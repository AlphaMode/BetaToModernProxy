package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ServerListPingPacket() implements BetaRecordPacket {
	public static final ServerListPingPacket INSTANCE = new ServerListPingPacket();
	public static final StreamCodec<ByteBuf, ServerListPingPacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SERVER_LIST_PING;
	}
}
