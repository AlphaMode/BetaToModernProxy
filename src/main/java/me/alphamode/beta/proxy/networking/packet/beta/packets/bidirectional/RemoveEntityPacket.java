package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record RemoveEntityPacket(int id) implements BetaPacket {
	public static final StreamCodec<ByteBuf, RemoveEntityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			RemoveEntityPacket::id,
			RemoveEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.REMOVE_ENTITY;
	}
}
