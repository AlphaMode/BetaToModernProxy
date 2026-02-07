package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.data.modern.IdMap;

import java.util.function.Predicate;

public record GlobalPalette<T>(IdMap<T> registry) implements Palette<T> {

	@Override
	public int idFor(final T value, final PaletteResize<T> resizeHandler) {
		int id = this.registry.getId(value);
		return id == -1 ? 0 : id;
	}

	@Override
	public boolean maybeHas(final Predicate<T> predicate) {
		return true;
	}

	@Override
	public T valueFor(final int index) {
		T value = this.registry.byId(index);
		if (value == null) {
			throw new IndexOutOfBoundsException(index);
		} else {
			return value;
		}
	}

	@Override
	public void read(final ByteStream buffer, final IdMap<T> globalMap) {
	}

	@Override
	public void write(final ByteStream buffer, final IdMap<T> globalMap) {
	}

	@Override
	public int getSerializedSize(final IdMap<T> globalMap) {
		return 0;
	}

	@Override
	public int getSize() {
		return this.registry.size();
	}

	@Override
	public Palette<T> copy() {
		return this;
	}
}

