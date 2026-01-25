package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkTilesUpdatePacket(int x,
									 int z,
									 int changes,
									 short[] positions,
									 byte[] blocks,
									 byte[] data) implements BetaPacket {
	public static final StreamCodec<ByteBuf, ChunkTilesUpdatePacket> CODEC = AbstractPacket.codec(ChunkTilesUpdatePacket::write, ChunkTilesUpdatePacket::new);

	public ChunkTilesUpdatePacket(final ByteBuf buf) {
		final int x = buf.readInt();
		final int z = buf.readInt();
		final int changes = buf.readShort() & 255;
		this(x, z, changes, new short[changes], new byte[changes], new byte[changes]);
		for (int i = 0; i < changes; i++) {
			this.positions[i] = buf.readShort();
		}

		buf.readBytes(this.blocks);
		buf.readBytes(this.data);
	}

	public void write(final ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.z);
		buf.writeShort((short) this.changes);
		for (int i = 0; i < this.changes; i++) {
			buf.writeShort(this.positions[i]);
		}

		buf.writeBytes(this.blocks);
		buf.writeBytes(this.data);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.CHUNK_TILES_UPDATE;
	}
}
