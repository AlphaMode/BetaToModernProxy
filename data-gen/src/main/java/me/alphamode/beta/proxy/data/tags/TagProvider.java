package me.alphamode.beta.proxy.data.tags;

import com.google.common.collect.Maps;
import me.alphamode.beta.proxy.data.NbtDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/// Vanilla copy of {@link net.minecraft.data.tags.TagsProvider} to work for all registries and export a single nbt file.
public class TagProvider implements NbtDataProvider {
    private final HolderLookup.Provider lookup;
    private final Map<KeyWithRegistry, TagBuilder> builders = Maps.newLinkedHashMap();
    private final Path output;

    public TagProvider(final Path output, final HolderLookup.Provider lookup) {
        this.output = output;
        this.lookup = lookup;
    }

    protected void addTags(HolderLookup.Provider registries) {
        blockTag(BlockTags.INFINIBURN_OVERWORLD).add(Blocks.NETHERRACK);
    }

    protected TagAppender<Block, Block> blockTag(final TagKey<Block> tag) {
        TagBuilder builder = this.getOrCreateRawBuilder(tag);
        return TagAppender.<Block>forBuilder(builder).map(block -> block.builtInRegistryHolder().key());
    }

    protected <T> TagAppender<ResourceKey<T>, T> keyTag(final TagKey<T> tag) {
        TagBuilder builder = this.getOrCreateRawBuilder(tag);
        return TagAppender.forBuilder(builder);
    }

    protected <T> TagBuilder getOrCreateRawBuilder(final TagKey<T> tag) {
        return this.builders.computeIfAbsent(new KeyWithRegistry(tag.registry().identifier(), tag.location()), k -> TagBuilder.create());
    }

    public record KeyWithRegistry(Identifier registry, Identifier key) {}

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        addTags(lookup);
        CompoundTag tag = new CompoundTag();
        Map<Identifier, Map<Identifier, List<TagEntry>>> tags = Maps.newLinkedHashMap();
        this.builders.entrySet().stream().forEach(entry -> {
            KeyWithRegistry key = entry.getKey();
            Map<Identifier, List<TagEntry>> registryTags = tags.computeIfAbsent(key.registry(), identifier -> new HashMap<>());
            registryTags.put(key.key, entry.getValue().build());
        });
        tags.forEach((registry, registryTags) -> {
            CompoundTag registryTag = new CompoundTag();
            registryTags.forEach((tagKey, tagEntries) -> {
                var realRegistry = BuiltInRegistries.REGISTRY.getValue(registry);
                registryTag.put(tagKey.toString(), TagEntry.CODEC.listOf().encodeStart(NbtOps.INSTANCE, tagEntries).getOrThrow());
            });
            tag.put(registry.toString(), registryTag);
        });

        return NbtDataProvider.saveStable(cache, tag, this.output);
    }

    @Override
    public String getName() {
        return "Beta Tag Provider";
    }
}
