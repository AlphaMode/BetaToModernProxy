package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCustomPayloadPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

public record S2CPlayCustomPayloadPacket(Identifier identifier,
										 byte[] data) implements S2CCustomPayloadPacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteBuf, S2CPlayCustomPayloadPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.IDENTIFIER,
			S2CPlayCustomPayloadPacket::identifier,
			ModernStreamCodecs.BYTE_ARRAY,
			S2CPlayCustomPayloadPacket::data,
			S2CPlayCustomPayloadPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.CUSTOM_PAYLOAD;
	}
}
