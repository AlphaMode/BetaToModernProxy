package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record PlayerActionPacket(Action action, Vec3i pos, byte face) implements BetaPacket {
	public static final StreamCodec<ByteStream, PlayerActionPacket> CODEC = StreamCodec.composite(
			BetaStreamCodecs.javaEnum(Action.class),
			PlayerActionPacket::action,
			Vec3i.TINY_CODEC,
			PlayerActionPacket::pos,
			CommonStreamCodecs.BYTE,
			PlayerActionPacket::face,
			PlayerActionPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.PLAYER_ACTION;
	}

	public enum Action {
		STARTED_DIGGING,
		UNUSED_1,
		FINISHED_DIGGING,
		UNUSED_2,
		DROP_ITEM
	}
}
