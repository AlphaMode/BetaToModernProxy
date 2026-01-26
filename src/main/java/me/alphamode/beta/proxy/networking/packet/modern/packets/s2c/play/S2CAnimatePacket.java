package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CAnimatePacket(int entityId, short action) implements S2CPlayPacket {
	public static final int SWING_MAIN_HAND = 0;
	public static final int WAKE_UP = 2;
	public static final int SWING_OFF_HAND = 3;
	public static final int CRITICAL_HIT = 4;
	public static final int MAGIC_CRITICAL_HIT = 5;

	public static final StreamCodec<ByteBuf, S2CAnimatePacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CAnimatePacket::entityId,
			BasicStreamCodecs.UNSIGNED_BYTE,
			S2CAnimatePacket::action,
			S2CAnimatePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ANIMATE;
	}
}
