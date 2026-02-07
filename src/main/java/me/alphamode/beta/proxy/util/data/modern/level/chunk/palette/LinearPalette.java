package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.data.modern.IdMap;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.List;
import java.util.function.Predicate;

public final class LinearPalette<T> implements Palette<T> {
	private final T[] values;
	private final int bits;
	private int size;

	public LinearPalette(final int bits, final List<T> paletteEntries) {
		this.values = (T[]) new Object[1 << bits];
		this.bits = bits;
		if (paletteEntries.size() >= this.values.length)
			throw new IllegalArgumentException("Can't initialize LinearPalette of size " + this.values.length + " with " + paletteEntries.size() + " entries");

		for (int i = 0; i < paletteEntries.size(); i++) {
			this.values[i] = paletteEntries.get(i);
		}

		this.size = paletteEntries.size();
	}

	private LinearPalette(final T[] values, final int bits, final int size) {
		this.values = values;
		this.bits = bits;
		this.size = size;
	}

	@Override
	public int idFor(final T value, final PaletteResize<T> resizeHandler) {
		for (int i = 0; i < this.size; i++) {
			if (this.values[i] == value) {
				return i;
			}
		}

		int index = this.size;
		if (index < this.values.length) {
			this.values[index] = value;
			this.size++;
			return index;
		} else {
			return resizeHandler.onResize(this.bits + 1, value);
		}
	}

	@Override
	public boolean maybeHas(final Predicate<T> predicate) {
		for (int i = 0; i < this.size; i++) {
			if (predicate.test(this.values[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public T valueFor(final int index) {
		if (index >= 0 && index < this.size) {
			return this.values[index];
		} else {
			throw new IndexOutOfBoundsException(index);
		}
	}

	@Override
	public void read(final ByteStream buffer, final IdMap<T> globalMap) {
		this.size = ModernStreamCodecs.VAR_INT.decode(buffer);

		for (int i = 0; i < this.size; i++) {
			this.values[i] = globalMap.byIdOrThrow(ModernStreamCodecs.VAR_INT.decode(buffer));
		}
	}

	@Override
	public void write(final ByteStream buffer, final IdMap<T> globalMap) {
		ModernStreamCodecs.VAR_INT.encode(buffer, this.size);

		for (int i = 0; i < this.size; i++) {
			ModernStreamCodecs.VAR_INT.encode(buffer, globalMap.getId(this.values[i]));
		}
	}

	@Override
	public int getSerializedSize(final IdMap<T> globalMap) {
		int result = PacketTypes.getVarIntLength(this.getSize());

		for (int i = 0; i < this.getSize(); i++) {
			result += PacketTypes.getVarIntLength(globalMap.getId(this.values[i]));
		}

		return result;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public Palette<T> copy() {
		return new LinearPalette<>(this.values.clone(), this.bits, this.size);
	}
}
