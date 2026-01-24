package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CLoginDisconnectPacket(
		TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundLoginPackets> {
	public static final StreamCodec<ByteBuf, S2CLoginDisconnectPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.TEXT_COMPONENT,
			S2CLoginDisconnectPacket::reason,
			S2CLoginDisconnectPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.DISCONNECT;
	}

	@Override
	public PacketState getState() {
		return PacketState.LOGIN;
	}

	@Override
	public TextComponent getReason() {
		return this.reason;
	}
}
