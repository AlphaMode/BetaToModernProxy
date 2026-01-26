package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.BlockTranslator;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.data.modern.*;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;
import me.alphamode.beta.proxy.util.data.modern.registry.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class PalettedContainer<T> implements PaletteResize<T> {
    private volatile PalettedContainer.Data<T> data;
    private final Strategy<T> strategy;

    public static PalettedContainer<BlockState> blockStates() {
        final BlockStateRegistry registry = BrodernProxy.getBlockTranslator().getBlockStateRegistry();
        return new PalettedContainer<>(registry.byId(0), Strategy.createForSection(registry));
    }

    public static PalettedContainer<Object> biomes() {
        return new PalettedContainer<>(new Object(), Strategy.createForQuart(new IdMap<>() {
            @Override
            public int getId(Object thing) {
                return 0;
            }

            @Override
            public @Nullable Object byId(int id) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public @NotNull Iterator<Object> iterator() {
                return List.of().iterator();
            }
        }));
    }

    public PalettedContainer(final T initialValue, final Strategy<T> strategy) {
        this.strategy = strategy;
        this.data = createOrReuseData(null, 0);
        this.data.palette.idFor(initialValue, this);
    }

    private PalettedContainer.Data<T> createOrReuseData(@Nullable final PalettedContainer.Data<T> oldData, final int targetBits) {
        Configuration dataConfiguration = this.strategy.factory().getConfigurationForBitCount(targetBits);
        if (oldData != null && dataConfiguration.equals(oldData.configuration())) {
            return oldData;
        } else {
            BitStorage storage = dataConfiguration.bitsInMemory() == 0
                    ? new ZeroBitStorage(this.strategy.entryCount())
                    : new SimpleBitStorage(dataConfiguration.bitsInMemory(), this.strategy.entryCount());
            Palette<T> palette = dataConfiguration.createPalette(this.strategy, List.of());
            return new PalettedContainer.Data<>(dataConfiguration, storage, palette);
        }
    }

    @Override
    public int onResize(final int bits, final T lastAddedValue) {
        PalettedContainer.Data<T> oldData = this.data;
        PalettedContainer.Data<T> newData = this.createOrReuseData(oldData, bits);
        newData.copyFrom(oldData.palette, oldData.storage);
        this.data = newData;
        return newData.palette.idFor(lastAddedValue, PaletteResize.noResizeExpected());
    }

    public void write(final ByteBuf buffer) {
        this.data.write(buffer, this.strategy.globalMap());
    }

    public int getSerializedSize() {
        return this.data.getSerializedSize(this.strategy.globalMap());
    }

    public void set(final int x, final int y, final int z, final T value) {
        set(this.strategy.getIndex(x, y, z), value);
    }

    private void set(final int index, final T value) {
        int id = this.data.palette.idFor(value, this);
        this.data.storage.set(index, id);
    }

    public T get(final int x, final int y, final int z) {
        return this.get(this.strategy.getIndex(x, y, z));
    }

    protected T get(final int index) {
        PalettedContainer.Data<T> data = this.data;
        return data.palette.valueFor(data.storage.get(index));
    }

    private record Data<T>(Configuration configuration, BitStorage storage, Palette<T> palette) {
        public void copyFrom(final Palette<T> oldPalette, final BitStorage oldStorage) {
            PaletteResize<T> dummyResizer = PaletteResize.noResizeExpected();

            for (int i = 0; i < oldStorage.getSize(); i++) {
                T value = oldPalette.valueFor(oldStorage.get(i));
                this.storage.set(i, this.palette.idFor(value, dummyResizer));
            }
        }

        public int getSerializedSize(final IdMap<T> globalMap) {
            return 1 + this.palette.getSerializedSize(globalMap) + this.storage.getRaw().length * 8;
        }

        public void write(final ByteBuf buffer, final IdMap<T> globalMap) {
            buffer.writeByte(this.storage.getBits());
            this.palette.write(buffer, globalMap);
            long[] data = this.storage.getRaw();
            ModernStreamCodecs.fixedLongArray(data.length).encode(buffer, data);
        }

        public PalettedContainer.Data<T> copy() {
            return new PalettedContainer.Data<>(this.configuration, this.storage.copy(), this.palette.copy());
        }
    }
}
