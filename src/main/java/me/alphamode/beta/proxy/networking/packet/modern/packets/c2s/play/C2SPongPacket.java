package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPongPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPongPacket(int id) implements C2SCommonPongPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteBuf, C2SPongPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
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
