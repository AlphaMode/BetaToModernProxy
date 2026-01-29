package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonCustomPayloadPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

public record C2SPlayCustomPayloadPacket(Identifier identifier,
										 byte[] data) implements C2SCommonCustomPayloadPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteBuf, C2SPlayCustomPayloadPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.IDENTIFIER,
			C2SPlayCustomPayloadPacket::identifier,
			ModernStreamCodecs.BYTE_ARRAY,
			C2SPlayCustomPayloadPacket::data,
			C2SPlayCustomPayloadPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CUSTOM_PAYLOAD;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public byte[] getData() {
		return this.data;
	}
}
