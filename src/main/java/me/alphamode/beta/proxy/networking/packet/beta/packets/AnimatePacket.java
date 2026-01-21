package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record AnimatePacket(int entityId, byte action) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, AnimatePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AnimatePacket::entityId,
			ByteBufCodecs.BYTE,
			AnimatePacket::action,
			AnimatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ANIMATE;
	}
}
