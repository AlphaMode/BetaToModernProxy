package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CPlayDisconnectPacket(
		TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteBuf, S2CPlayDisconnectPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.TEXT_COMPONENT,
			S2CPlayDisconnectPacket::reason,
			S2CPlayDisconnectPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.DISCONNECT;
	}

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public TextComponent getReason() {
		return this.reason;
	}
}
