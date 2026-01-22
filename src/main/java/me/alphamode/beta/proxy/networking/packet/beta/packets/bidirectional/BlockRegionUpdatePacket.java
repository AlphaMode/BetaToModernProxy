package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockRegionUpdatePacket(int x, short y, int z, byte xs, byte ys, byte zs,
									  byte[] data) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, BlockRegionUpdatePacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			BlockRegionUpdatePacket::x,
			BasicCodecs.SHORT,
			BlockRegionUpdatePacket::y,
			BasicCodecs.INT,
			BlockRegionUpdatePacket::z,
			BasicCodecs.BYTE,
			BlockRegionUpdatePacket::xs,
			BasicCodecs.BYTE,
			BlockRegionUpdatePacket::ys,
			BasicCodecs.BYTE,
			BlockRegionUpdatePacket::zs,
			BetaCodecs.BYTE_ARRAY,
			BlockRegionUpdatePacket::data,
			BlockRegionUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.BLOCK_REGION_UPDATE;
	}
}
