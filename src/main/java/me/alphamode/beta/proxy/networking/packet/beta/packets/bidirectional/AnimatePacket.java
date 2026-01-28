package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record AnimatePacket(int entityId, byte action) implements BetaPacket {
	public static final byte SWING_ARM = 1;
	public static final byte DAMAGE_ANIMATION = 2;
	public static final byte LEAVE_BED = 3;

	public static final StreamCodec<ByteBuf, AnimatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AnimatePacket::entityId,
			BasicStreamCodecs.BYTE,
			AnimatePacket::action,
			AnimatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ANIMATE;
	}
}
