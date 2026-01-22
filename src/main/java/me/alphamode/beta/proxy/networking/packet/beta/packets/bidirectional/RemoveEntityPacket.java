package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record RemoveEntityPacket(int id) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, RemoveEntityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			RemoveEntityPacket::id,
			RemoveEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.REMOVE_ENTITY;
	}
}
