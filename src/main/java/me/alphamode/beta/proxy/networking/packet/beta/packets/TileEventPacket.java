package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record TileEventPacket(Vec3i position, byte b0, byte b1) implements RecordPacket {
	public static final StreamCodec<ByteBuf, TileEventPacket> CODEC = StreamCodec.composite(
			Vec3i.SEMI_TINY_CODEC,
			TileEventPacket::position,
			ByteBufCodecs.BYTE,
			TileEventPacket::b0,
			ByteBufCodecs.BYTE,
			TileEventPacket::b0,
			TileEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TILE_EVENT;
	}
}
