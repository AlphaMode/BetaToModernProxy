package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ServerListPingPacket() implements BetaPacket {
	public static final ServerListPingPacket INSTANCE = new ServerListPingPacket();
	public static final StreamCodec<ByteBuf, ServerListPingPacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SERVER_LIST_PING;
	}
}
