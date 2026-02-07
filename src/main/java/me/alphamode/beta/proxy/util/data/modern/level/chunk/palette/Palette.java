package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.data.modern.IdMap;

import java.util.List;
import java.util.function.Predicate;

public interface Palette<T> {
	int idFor(T value, PaletteResize<T> resizeHandler);

	boolean maybeHas(Predicate<T> predicate);

	T valueFor(int index);

	void read(ByteStream buffer, IdMap<T> globalMap);

	void write(ByteStream buffer, IdMap<T> globalMap);

	int getSerializedSize(IdMap<T> globalMap);

	int getSize();

	Palette<T> copy();

	interface Factory {
		<A> Palette<A> create(int bits, List<A> paletteEntries);
	}
}
