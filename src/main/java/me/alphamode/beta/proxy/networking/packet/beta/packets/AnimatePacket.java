package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record AnimatePacket(int entityId, byte action) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, AnimatePacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			AnimatePacket::entityId,
			BasicCodecs.BYTE,
			AnimatePacket::action,
			AnimatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ANIMATE;
	}
}
