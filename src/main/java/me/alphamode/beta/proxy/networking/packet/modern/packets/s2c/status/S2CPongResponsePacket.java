package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CPongResponsePacket(long time) implements S2CStatusPacket {
	public static final StreamCodec<ByteBuf, S2CPongResponsePacket> CODEC = StreamCodec.composite(
			BasicCodecs.LONG,
			S2CPongResponsePacket::time,
			S2CPongResponsePacket::new
	);

	@Override
	public ClientboundStatusPackets getType() {
		return ClientboundStatusPackets.PONG_RESPONSE;
	}
}
