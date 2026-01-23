package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record AnimatePacket(int entityId, byte action) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, AnimatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AnimatePacket::entityId,
			BasicStreamCodecs.BYTE,
			AnimatePacket::action,
			AnimatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ANIMATE;
	}
}
