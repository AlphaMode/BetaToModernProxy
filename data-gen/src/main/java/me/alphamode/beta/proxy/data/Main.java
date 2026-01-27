package me.alphamode.beta.proxy.data;

import com.mojang.serialization.DynamicOps;
import me.alphamode.beta.proxy.data.block.BetaToModernBlocks;
import me.alphamode.beta.proxy.data.item.ItemMapper;
import me.alphamode.beta.proxy.data.registries.BetaRegistries;
import me.alphamode.beta.proxy.data.tags.TagProvider;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class Main {
	public static void main(final String[] args) {
		bootstrap(() -> {
			final Path outputDir = Path.of(System.getProperty("datagen.output-dir"));

			SharedConstants.tryDetectVersion();
			Bootstrap.bootStrap();

			var lookup = BetaRegistries.createLookup();

			final DataGenerator generator = new DataGenerator(outputDir, SharedConstants.getCurrentVersion(), true);
			generator.getVanillaPack(true).addProvider(o -> new TagProvider(outputDir.resolve("beta_tags.nbt"), lookup));
			try {
				generator.run();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				outputRegistries(lookup, outputDir);
			} catch (IOException e) {
				e.printStackTrace();
			}

			ItemMapper.writeItems(outputDir.resolve("beta_to_modern_items.nbt"));
		});
	}

	private static void bootstrap(final Runnable runnable) {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		runnable.run();
		Util.shutdownExecutors();
	}

	private static void outputRegistries(final HolderLookup.Provider lookup, final Path outputDir) throws IOException {
		var ops = lookup.createSerializationContext(NbtOps.INSTANCE);
		CompoundTag registryTag = new CompoundTag();
		RegistryDataLoader.SYNCHRONIZED_REGISTRIES.forEach(registryData -> packRegistry(ops, registryData, lookup, (resourceKey, entries) -> {
			CompoundTag registry = new CompoundTag();
			entries.forEach(entry -> entry.data().ifPresent(tag -> registry.put(entry.id().toString(), tag)));
			registryTag.put(resourceKey.identifier().toString(), registry);
		}));
		NbtIo.writeCompressed(registryTag, outputDir.resolve("beta_registries.nbt"));

		final BetaToModernBlocks translator = new BetaToModernBlocks();
		translator.translate();
		translator.save(outputDir.resolve("beta_to_modern_blocks.nbt"));

		Util.shutdownExecutors();
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
