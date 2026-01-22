package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record InteractionPacket(int id, byte type, Vec3i position) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, InteractionPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			InteractionPacket::id,
			BasicCodecs.BYTE,
			InteractionPacket::type,
			Vec3i.TINY_CODEC,
			InteractionPacket::position,
			InteractionPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.INTERACTION;
	}
}
