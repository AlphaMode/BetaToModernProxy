package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TileUpdatePacket(int x, byte y, int z, byte block, byte data) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, TileUpdatePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			TileUpdatePacket::x,
			ByteBufCodecs.BYTE,
			TileUpdatePacket::y,
			ByteBufCodecs.INT,
			TileUpdatePacket::z,
			ByteBufCodecs.BYTE,
			TileUpdatePacket::block,
			ByteBufCodecs.BYTE,
			TileUpdatePacket::data,
			TileUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TILE_UPDATE;
	}
}
