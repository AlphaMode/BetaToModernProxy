package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CSetTimePacket(long gameTime, long dayTime, boolean tickDayTime) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CSetTimePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			S2CSetTimePacket::gameTime,
			BasicStreamCodecs.LONG,
			S2CSetTimePacket::dayTime,
			BasicStreamCodecs.BOOL,
			S2CSetTimePacket::tickDayTime,
			S2CSetTimePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_TIME;
	}
}
