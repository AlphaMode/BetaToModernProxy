package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkPos(int x, int z) {
	public static final StreamCodec<ByteBuf, ChunkPos> CODEC = StreamCodec.ofMember(ChunkPos::write, ChunkPos::new);

	public ChunkPos(final ByteBuf buf) {
		final long value = buf.readLong();
		this((int) value, (int) (value >> 32));
	}

	public void write(final ByteBuf buf) {
		buf.writeLong(this.pack());
	}

	public static ChunkPos unpack(final long value) {
		return new ChunkPos((int) value, (int) (value >> 32));
	}

	public long pack() {
		return this.x & 4294967295L | (this.z & 4294967295L) << 32;
	}
}
