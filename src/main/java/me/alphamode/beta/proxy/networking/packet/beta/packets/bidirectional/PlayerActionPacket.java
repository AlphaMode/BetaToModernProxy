package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record PlayerActionPacket(byte action, Vec3i pos, byte face) implements BetaPacket {
	public static final StreamCodec<ByteBuf, PlayerActionPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			PlayerActionPacket::action,
			Vec3i.TINY_CODEC,
			PlayerActionPacket::pos,
			BasicStreamCodecs.BYTE,
			PlayerActionPacket::face,
			PlayerActionPacket::new
	);

	public PlayerActionPacket(int action, int x, int y, int z, int face) {
		this((byte) action, new Vec3i(x, y, z), (byte) face);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_ACTION;
	}
}
