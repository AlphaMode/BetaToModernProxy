package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.data.modern.IdMap;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.List;
import java.util.function.Predicate;

public final class SingleValuePalette<T> implements Palette<T> {
	private T value;

	public SingleValuePalette(T value) {
		this.value = value;
	}

	public static <A> Palette<A> create(final int bits, final List<A> paletteEntries) {
		if (paletteEntries.size() > 1)
			throw new IllegalArgumentException("Can't initialize SingleValuePalette with " + paletteEntries.size() + " values.");
		return new SingleValuePalette<>(paletteEntries.isEmpty() ? null : paletteEntries.getFirst());
	}

	@Override
	public int idFor(T value, PaletteResize<T> resizeHandler) {
		if (this.value != null && this.value != value) {
			return resizeHandler.onResize(1, value);
		} else {
			this.value = value;
			return 0;
		}
	}

	@Override
	public boolean maybeHas(Predicate<T> predicate) {
		if (this.value == null) {
			throw new IllegalStateException("Use of an uninitialized palette");
		} else {
			return predicate.test(this.value);
		}
	}

	@Override
	public T valueFor(int index) {
		if (this.value != null && index == 0) {
			return this.value;
		} else {
			throw new IllegalStateException("Missing Palette entry for id " + index + ".");
		}
	}

	@Override
	public void read(final ByteStream buffer, final IdMap<T> globalMap) {
		this.value = globalMap.byIdOrThrow(ModernStreamCodecs.VAR_INT.decode(buffer));
	}

	@Override
	public void write(final ByteStream buffer, final IdMap<T> globalMap) {
		if (this.value == null) {
			throw new IllegalStateException("Use of an uninitialized palette");
		} else {
			ModernStreamCodecs.VAR_INT.encode(buffer, globalMap.getId(this.value));
		}
	}

	@Override
	public int getSerializedSize(IdMap<T> globalMap) {
		if (this.value == null) {
			throw new IllegalStateException("Use of an uninitialized palette");
		} else {
			return PacketTypes.getVarIntLength(globalMap.getId(this.value));
		}
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public Palette<T> copy() {
		if (this.value == null) {
			throw new IllegalStateException("Use of an uninitialized palette");
		} else {
			return this;
		}
	}
}
