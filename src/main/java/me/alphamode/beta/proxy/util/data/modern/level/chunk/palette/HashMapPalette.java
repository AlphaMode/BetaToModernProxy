package me.alphamode.beta.proxy.util.data.modern.level.chunk.palette;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.data.modern.CrudeIncrementalIntIdentityHashBiMap;
import me.alphamode.beta.proxy.util.data.modern.IdMap;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HashMapPalette<T> implements Palette<T> {
	private final CrudeIncrementalIntIdentityHashBiMap<T> values;
	private final int bits;

	public HashMapPalette(final int bits, final List<T> values) {
		this(bits);
		values.forEach(this.values::add);
	}

	public HashMapPalette(final int bits) {
		this(bits, CrudeIncrementalIntIdentityHashBiMap.create(1 << bits));
	}

	private HashMapPalette(final int bits, final CrudeIncrementalIntIdentityHashBiMap<T> values) {
		this.bits = bits;
		this.values = values;
	}

	@Override
	public int idFor(final T value, final PaletteResize<T> resizeHandler) {
		int id = this.values.getId(value);
		if (id == -1) {
			id = this.values.add(value);
			if (id >= 1 << this.bits) {
				id = resizeHandler.onResize(this.bits + 1, value);
			}
		}

		return id;
	}

	@Override
	public boolean maybeHas(final Predicate<T> predicate) {
		for (int i = 0; i < this.getSize(); i++) {
			if (predicate.test(this.values.byId(i))) {
				return true;
			}
		}

		return false;
	}

	@Override
	public T valueFor(final int index) {
		T value = this.values.byId(index);
		if (value == null) {
			throw new RuntimeException("Missing Palette entry for index " + index + ".");
		} else {
			return value;
		}
	}

	@Override
	public void read(final ByteBuf buffer, final IdMap<T> globalMap) {
		this.values.clear();
		int size = ModernStreamCodecs.VAR_INT.decode(buffer);

		for (int i = 0; i < size; i++) {
			this.values.add(globalMap.byIdOrThrow(ModernStreamCodecs.VAR_INT.decode(buffer)));
		}
	}

	@Override
	public void write(final ByteBuf buffer, final IdMap<T> globalMap) {
		int size = this.getSize();
		ModernStreamCodecs.VAR_INT.encode(buffer, size);

		for (int i = 0; i < size; i++) {
			ModernStreamCodecs.VAR_INT.encode(buffer, globalMap.getId(this.values.byId(i)));
		}
	}

	@Override
	public int getSerializedSize(final IdMap<T> globalMap) {
		int size = PacketTypes.getVarIntLength(this.getSize());

		for (int i = 0; i < this.getSize(); i++) {
			size += PacketTypes.getVarIntLength(globalMap.getId(this.values.byId(i)));
		}

		return size;
	}

	public List<T> getEntries() {
		ArrayList<T> list = new ArrayList<>();
		this.values.iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public int getSize() {
		return this.values.size();
	}

	@Override
	public Palette<T> copy() {
		return new HashMapPalette<>(this.bits, this.values.copy());
	}
}
