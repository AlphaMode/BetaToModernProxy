package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.data.modern.IdMap;

public record Strategy<T>(IdMap<T> globalMap, GlobalPalette<T> globalPalette, int bitsPerAxis, ConfigurationFactory factory) {
    private static final Palette.Factory SINGLE_VALUE_PALETTE_FACTORY = SingleValuePalette::create;
    private static final Palette.Factory LINEAR_PALETTE_FACTORY = LinearPalette::new;
    private static final Palette.Factory HASHMAP_PALETTE_FACTORY = HashMapPalette::new;
    private static final Configuration ZERO_BITS = new Configuration.Simple(SINGLE_VALUE_PALETTE_FACTORY, 0);
    private static final Configuration ONE_BIT_LINEAR = new Configuration.Simple(LINEAR_PALETTE_FACTORY, 1);
    private static final Configuration TWO_BITS_LINEAR = new Configuration.Simple(LINEAR_PALETTE_FACTORY, 2);
    private static final Configuration THREE_BITS_LINEAR = new Configuration.Simple(LINEAR_PALETTE_FACTORY, 3);
    private static final Configuration FOUR_BITS_LINEAR = new Configuration.Simple(LINEAR_PALETTE_FACTORY, 4);
    private static final Configuration FIVE_BITS_HASHMAP = new Configuration.Simple(HASHMAP_PALETTE_FACTORY, 5);
    private static final Configuration SIX_BITS_HASHMAP = new Configuration.Simple(HASHMAP_PALETTE_FACTORY, 6);
    private static final Configuration SEVEN_BITS_HASHMAP = new Configuration.Simple(HASHMAP_PALETTE_FACTORY, 7);
    private static final Configuration EIGHT_BITS_HASHMAP = new Configuration.Simple(HASHMAP_PALETTE_FACTORY, 8);

    public Strategy(final IdMap<T> globalMap, final int bitsPerAxis, final ConfigurationFactory factory) {
        this(globalMap, new GlobalPalette<>(globalMap), bitsPerAxis, factory);
    }

    /// Section is a 16x16x16 area
    public static <T> Strategy<T> createForSection(final IdMap<T> registry) {
        return new Strategy<>(registry, 4, entryBits -> switch (entryBits) {
            case 0 -> Strategy.ZERO_BITS;
            case 1, 2, 3, 4 -> Strategy.FOUR_BITS_LINEAR;
            case 5 -> Strategy.FIVE_BITS_HASHMAP;
            case 6 -> Strategy.SIX_BITS_HASHMAP;
            case 7 -> Strategy.SEVEN_BITS_HASHMAP;
            case 8 -> Strategy.EIGHT_BITS_HASHMAP;
            default -> new Configuration.Global(minimumBitsRequiredForDistinctValues(registry.size()), entryBits);
        });
    }

    /// Quart is 4x4x4 area
    public static <T> Strategy<T> createForQuart(final IdMap<T> registry) {
        return new Strategy<>(registry, 2, entryBits -> switch (entryBits) {
            case 0 -> Strategy.ZERO_BITS;
            case 1 -> Strategy.ONE_BIT_LINEAR;
            case 2 -> Strategy.TWO_BITS_LINEAR;
            case 3 -> Strategy.THREE_BITS_LINEAR;
            default -> new Configuration.Global(minimumBitsRequiredForDistinctValues(registry.size()), entryBits);
        });
    }

    public int entryCount() {
        return 1 << this.bitsPerAxis * 3;
    }

    public int getIndex(final int x, final int y, final int z) {
        return (y << this.bitsPerAxis | z) << this.bitsPerAxis | x;
    }

    private static int minimumBitsRequiredForDistinctValues(final int count) {
        return Mth.ceillog2(count);
    }

    public interface ConfigurationFactory {
        Configuration getConfigurationForBitCount(final int entryBits);
    }
}
