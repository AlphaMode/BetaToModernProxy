package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record RemoveEntityPacket(int entityId) implements BetaPacket {
	public static final StreamCodec<ByteBuf, RemoveEntityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			RemoveEntityPacket::entityId,
			RemoveEntityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.REMOVE_ENTITY;
	}
}
