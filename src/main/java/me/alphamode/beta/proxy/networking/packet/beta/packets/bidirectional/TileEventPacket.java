package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record TileEventPacket(Vec3i position, byte b0, byte b1) implements BetaPacket {
	public static final StreamCodec<ByteBuf, TileEventPacket> CODEC = StreamCodec.composite(
			Vec3i.SEMI_TINY_CODEC,
			TileEventPacket::position,
			BasicStreamCodecs.BYTE,
			TileEventPacket::b0,
			BasicStreamCodecs.BYTE,
			TileEventPacket::b1,
			TileEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TILE_EVENT;
	}
}
