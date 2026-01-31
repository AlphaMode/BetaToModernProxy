package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record AnimatePacket(int entityId, Action action) implements BetaPacket {
	public static final StreamCodec<ByteBuf, AnimatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AnimatePacket::entityId,
			BetaStreamCodecs.javaEnum(Action.class),
			AnimatePacket::action,
			AnimatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ANIMATE;
	}

	public enum Action {
		SWING_ARM,
		DAMAGE_ANIMATION,
		LEAVE_BED
	}
}
