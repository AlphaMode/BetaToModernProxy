package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record PlayerActionPacket(Action action, Vec3i pos, byte face) implements BetaPacket {
	public static final StreamCodec<ByteBuf, PlayerActionPacket> CODEC = StreamCodec.composite(
			BetaStreamCodecs.javaEnum(Action.class),
			PlayerActionPacket::action,
			Vec3i.TINY_CODEC,
			PlayerActionPacket::pos,
			BasicStreamCodecs.BYTE,
			PlayerActionPacket::face,
			PlayerActionPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_ACTION;
	}

	public enum Action {
		STARTED_DIGGING,
		UNUSED_1,
		FINISHED_DIGGING,
		UNUSED_2,
		DROP_ITEM
	}
}
