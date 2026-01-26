package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TileUpdatePacket(int x, byte y, int z, byte block, byte data) implements BetaPacket {
	public static final StreamCodec<ByteBuf, TileUpdatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			TileUpdatePacket::x,
			BasicStreamCodecs.BYTE,
			TileUpdatePacket::y,
			BasicStreamCodecs.INT,
			TileUpdatePacket::z,
			BasicStreamCodecs.BYTE,
			TileUpdatePacket::block,
			BasicStreamCodecs.BYTE,
			TileUpdatePacket::data,
			TileUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TILE_UPDATE;
	}
}
