package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPingRequestPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPlayPingRequestPacket(long time) implements C2SCommonPingRequestPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteStream, C2SPlayPingRequestPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
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
