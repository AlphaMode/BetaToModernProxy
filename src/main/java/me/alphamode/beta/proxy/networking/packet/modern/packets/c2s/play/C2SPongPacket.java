package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPongPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPongPacket(int id) implements C2SCommonPongPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteStream, C2SPongPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			C2SPongPacket::id,
			C2SPongPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PONG;
	}

	@Override
	public int getId() {
		return this.id;
	}
}
