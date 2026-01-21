package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockRegionUpdatePacket(int x, short y, int z, byte xs, byte ys, byte zs,
									  byte[] buffer) implements RecordPacket {
	public static final StreamCodec<ByteBuf, BlockRegionUpdatePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			BlockRegionUpdatePacket::x,
			ByteBufCodecs.SHORT,
			BlockRegionUpdatePacket::y,
			ByteBufCodecs.INT,
			BlockRegionUpdatePacket::z,
			ByteBufCodecs.BYTE,
			BlockRegionUpdatePacket::xs,
			ByteBufCodecs.BYTE,
			BlockRegionUpdatePacket::ys,
			ByteBufCodecs.BYTE,
			BlockRegionUpdatePacket::zs,
			ByteBufCodecs.BYTE_ARRAY,
			BlockRegionUpdatePacket::buffer,
			BlockRegionUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.BLOCK_REGION_UPDATE;
	}
}
