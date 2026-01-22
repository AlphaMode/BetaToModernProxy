package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CDisconnectPacket(TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteBuf, S2CDisconnectPacket> CODEC = StreamCodec.composite(
			ModernCodecs.COMPONENT,
			S2CDisconnectPacket::reason,
			S2CDisconnectPacket::new
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
