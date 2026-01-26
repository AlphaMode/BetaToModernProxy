package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record InteractionPacket(int id, byte type, Vec3i position) implements BetaPacket {
	public static final StreamCodec<ByteBuf, InteractionPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			InteractionPacket::id,
			BasicStreamCodecs.BYTE,
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
