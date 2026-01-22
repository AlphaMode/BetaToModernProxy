package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SKeepAlivePacket(long id) implements C2SCommonKeepAlivePacket<ServerboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, C2SKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicCodecs.LONG,
			C2SKeepAlivePacket::id,
			C2SKeepAlivePacket::new
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
