package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record TileEventPacket(Vec3i position, byte b0, byte b1) implements BetaPacket {
	public static final StreamCodec<ByteStream, TileEventPacket> CODEC = StreamCodec.composite(
			Vec3i.SEMI_TINY_CODEC,
			TileEventPacket::position,
			CommonStreamCodecs.BYTE,
			TileEventPacket::b0,
			CommonStreamCodecs.BYTE,
			TileEventPacket::b1,
			TileEventPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.TILE_EVENT;
	}
}
