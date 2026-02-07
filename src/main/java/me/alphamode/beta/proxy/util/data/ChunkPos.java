package me.alphamode.beta.proxy.util.data;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkPos(int x, int z) {
	public static final StreamCodec<ByteStream, ChunkPos> CODEC = StreamCodec.ofMember(ChunkPos::write, ChunkPos::new);

	public static final StreamCodec<ByteStream, ChunkPos> BASIC_CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			ChunkPos::x,
			CommonStreamCodecs.INT,
			ChunkPos::z,
			ChunkPos::new
	);

	public ChunkPos(final ByteStream buf) {
		final long value = buf.readLong();
		this((int) value, (int) (value >> 32));
	}

	public void write(final ByteStream buf) {
		buf.writeLong(this.pack());
	}

	public static ChunkPos unpack(final long value) {
		return new ChunkPos((int) value, (int) (value >> 32));
	}

	public long pack() {
		return this.x & 4294967295L | (this.z & 4294967295L) << 32;
	}
}
