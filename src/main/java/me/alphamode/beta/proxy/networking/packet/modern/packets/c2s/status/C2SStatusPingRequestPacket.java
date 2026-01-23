package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonPingRequestPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SStatusPingRequestPacket(long time) implements C2SCommonPingRequestPacket<ServerboundStatusPackets> {
	public static final StreamCodec<ByteBuf, C2SStatusPingRequestPacket> CODEC = StreamCodec.composite(
			BasicCodecs.LONG,
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
