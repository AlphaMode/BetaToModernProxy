package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CConfigurationDisconnectPacket(
		TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, S2CConfigurationDisconnectPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.TEXT_COMPONENT,
			S2CConfigurationDisconnectPacket::reason,
			S2CConfigurationDisconnectPacket::new
	);

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.DISCONNECT;
	}

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public TextComponent getReason() {
		return this.reason;
	}
}
