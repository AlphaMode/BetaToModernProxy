package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TileUpdatePacket(int x, byte y, int z, byte block, byte data) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, TileUpdatePacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			TileUpdatePacket::x,
			BasicCodecs.BYTE,
			TileUpdatePacket::y,
			BasicCodecs.INT,
			TileUpdatePacket::z,
			BasicCodecs.BYTE,
			TileUpdatePacket::block,
			BasicCodecs.BYTE,
			TileUpdatePacket::data,
			TileUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TILE_UPDATE;
	}
}
