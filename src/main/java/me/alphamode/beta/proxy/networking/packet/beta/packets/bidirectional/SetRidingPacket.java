package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetRidingPacket(int riderId, int riddenId) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, SetRidingPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			SetRidingPacket::riderId,
			BasicCodecs.INT,
			SetRidingPacket::riddenId,
			SetRidingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_RIDING;
	}
}
