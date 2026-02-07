package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockRegionUpdatePacket(int x, short y, int z, byte xs, byte ys, byte zs,
									  byte[] data) implements BetaPacket {
	public static final StreamCodec<ByteStream, BlockRegionUpdatePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			BlockRegionUpdatePacket::x,
			CommonStreamCodecs.SHORT,
			BlockRegionUpdatePacket::y,
			CommonStreamCodecs.INT,
			BlockRegionUpdatePacket::z,
			CommonStreamCodecs.BYTE,
			BlockRegionUpdatePacket::xs,
			CommonStreamCodecs.BYTE,
			BlockRegionUpdatePacket::ys,
			CommonStreamCodecs.BYTE,
			BlockRegionUpdatePacket::zs,
			BetaStreamCodecs.BYTE_ARRAY,
			BlockRegionUpdatePacket::data,
			BlockRegionUpdatePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.BLOCK_REGION_UPDATE;
	}
}
