package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPingRequestPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPlayPingRequestPacket(long time) implements C2SCommonPingRequestPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteBuf, C2SPlayPingRequestPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			C2SPlayPingRequestPacket::time,
			C2SPlayPingRequestPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PING_REQUEST;
	}

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
