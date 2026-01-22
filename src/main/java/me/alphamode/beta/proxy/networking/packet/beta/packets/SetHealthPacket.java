package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetHealthPacket(short health) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, SetHealthPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.SHORT,
			SetHealthPacket::health,
			SetHealthPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_HEALTH;
	}
}
