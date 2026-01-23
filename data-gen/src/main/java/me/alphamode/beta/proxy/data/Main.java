package me.alphamode.beta.proxy.data;

import com.mojang.serialization.DynamicOps;
import me.alphamode.beta.proxy.data.registries.BetaRegistries;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) throws IOException {
        Path outputDir = Path.of(System.getProperty("datagen.output-dir"));
        Path registryDataPath = outputDir.resolve("beta_registries.nbt");

        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var lookup = BetaRegistries.createLookup();
        var ops = lookup.createSerializationContext(NbtOps.INSTANCE);
        CompoundTag registryTag = new CompoundTag();
        RegistryDataLoader.SYNCHRONIZED_REGISTRIES.forEach(registryData -> {
            packRegistry(ops, registryData, lookup, (resourceKey, entries) -> {
                CompoundTag registry = new CompoundTag();
                entries.forEach(entry -> {
                    entry.data().ifPresent(tag -> {
                        registry.put(entry.id().toString(), tag);
                    });
                });
                registryTag.put(resourceKey.identifier().toString(), registry);
            });
        });
        NbtIo.writeCompressed(registryTag, registryDataPath);
    }

    private static <T> void packRegistry(
            final DynamicOps<Tag> ops,
            final RegistryDataLoader.RegistryData<T> registryData, HolderLookup.Provider lookup,
            final BiConsumer<ResourceKey<? extends Registry<?>>, List<RegistrySynchronization.PackedRegistryEntry>> output
    ) {
        lookup.lookup(registryData.key()).ifPresent(registry -> {
            List<RegistrySynchronization.PackedRegistryEntry> packedElements = new ArrayList<>();
            registry.listElements().forEach(element -> {
                Tag encodedElement = registryData.elementCodec()
                        .encodeStart(ops, element.value())
                        .getOrThrow(s -> new IllegalArgumentException("Failed to serialize " + element.key() + ": " + s));
                packedElements.add(new RegistrySynchronization.PackedRegistryEntry(element.key().identifier(), Optional.of(encodedElement)));
            });
            output.accept(registry.key(), packedElements);
        });
    }
}
