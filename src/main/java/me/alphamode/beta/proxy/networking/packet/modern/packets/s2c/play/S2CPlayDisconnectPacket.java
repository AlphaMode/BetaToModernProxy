package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CPlayDisconnectPacket(TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteBuf, S2CPlayDisconnectPacket> CODEC = StreamCodec.composite(
			ModernCodecs.COMPONENT,
			S2CPlayDisconnectPacket::reason,
			S2CPlayDisconnectPacket::new
	);

	@Override
	public TextComponent getReason() {
		return this.reason;
	}

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.DISCONNECT;
	}
}
