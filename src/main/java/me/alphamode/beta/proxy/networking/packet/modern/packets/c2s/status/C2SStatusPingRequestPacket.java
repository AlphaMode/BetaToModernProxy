package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPingRequestPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SStatusPingRequestPacket(long time) implements C2SCommonPingRequestPacket<ServerboundStatusPackets> {
	public static final StreamCodec<ByteStream, C2SStatusPingRequestPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			C2SStatusPingRequestPacket::time,
			C2SStatusPingRequestPacket::new
	);

	@Override
	public ServerboundStatusPackets getType() {
		return ServerboundStatusPackets.PING_REQUEST;
	}

	@Override
	public PacketState getState() {
		return PacketState.STATUS;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
