package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record InteractPacket(int attackerId, int targetId, boolean attack) implements BetaPacket {
	public static final StreamCodec<ByteBuf, InteractPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			InteractPacket::attackerId,
			BasicStreamCodecs.INT,
			InteractPacket::targetId,
			BasicStreamCodecs.BOOL,
			InteractPacket::attack,
			InteractPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.INTERACT;
	}
}
