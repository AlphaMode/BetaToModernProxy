package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CStatusPongResponsePacket(long time) implements S2CStatusPacket {
	public static final StreamCodec<ByteBuf, S2CStatusPongResponsePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			S2CStatusPongResponsePacket::time,
			S2CStatusPongResponsePacket::new
	);

	@Override
	public ClientboundStatusPackets getType() {
		return ClientboundStatusPackets.PONG_RESPONSE;
	}
}
