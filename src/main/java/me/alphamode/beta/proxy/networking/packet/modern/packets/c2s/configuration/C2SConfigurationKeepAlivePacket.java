package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SConfigurationKeepAlivePacket(long id) implements C2SCommonKeepAlivePacket<ServerboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, C2SConfigurationKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			C2SConfigurationKeepAlivePacket::id,
			C2SConfigurationKeepAlivePacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public ServerboundConfigurationPackets getType() {
		return ServerboundConfigurationPackets.KEEP_ALIVE;
	}
}
