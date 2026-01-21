package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetRidingPacket(int riderId, int riddenId) implements RecordPacket {
	public static final StreamCodec<ByteBuf, SetRidingPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			SetRidingPacket::riderId,
			ByteBufCodecs.INT,
			SetRidingPacket::riddenId,
			SetRidingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_RIDING;
	}
}
