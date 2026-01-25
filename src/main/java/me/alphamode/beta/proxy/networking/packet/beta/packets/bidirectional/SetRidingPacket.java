package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetRidingPacket(int riderId, int riddenId) implements BetaPacket {
	public static final StreamCodec<ByteBuf, SetRidingPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			SetRidingPacket::riderId,
			BasicStreamCodecs.INT,
			SetRidingPacket::riddenId,
			SetRidingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_RIDING;
	}
}
