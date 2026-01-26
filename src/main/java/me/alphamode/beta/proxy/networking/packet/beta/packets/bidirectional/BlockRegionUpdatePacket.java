package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockRegionUpdatePacket(int x, short y, int z, byte xs, byte ys, byte zs,
									  byte[] data) implements BetaPacket {
	public static final StreamCodec<ByteBuf, BlockRegionUpdatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			BlockRegionUpdatePacket::x,
			BasicStreamCodecs.SHORT,
			BlockRegionUpdatePacket::y,
			BasicStreamCodecs.INT,
			BlockRegionUpdatePacket::z,
			BasicStreamCodecs.BYTE,
			BlockRegionUpdatePacket::xs,
			BasicStreamCodecs.BYTE,
			BlockRegionUpdatePacket::ys,
			BasicStreamCodecs.BYTE,
			BlockRegionUpdatePacket::zs,
			BetaStreamCodecs.BYTE_ARRAY,
			BlockRegionUpdatePacket::data,
			BlockRegionUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.BLOCK_REGION_UPDATE;
	}
}
