package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TileUpdatePacket(int x, byte y, int z, byte block, byte data) implements BetaPacket {
	public static final StreamCodec<ByteStream, TileUpdatePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			TileUpdatePacket::x,
			CommonStreamCodecs.BYTE,
			TileUpdatePacket::y,
			CommonStreamCodecs.INT,
			TileUpdatePacket::z,
			CommonStreamCodecs.BYTE,
			TileUpdatePacket::block,
			CommonStreamCodecs.BYTE,
			TileUpdatePacket::data,
			TileUpdatePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.TILE_UPDATE;
	}
}
