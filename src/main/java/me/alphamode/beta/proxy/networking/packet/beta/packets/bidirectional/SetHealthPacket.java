package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetHealthPacket(short health) implements BetaPacket {
	public static final StreamCodec<ByteBuf, SetHealthPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.SHORT,
			SetHealthPacket::health,
			SetHealthPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_HEALTH;
	}
}
