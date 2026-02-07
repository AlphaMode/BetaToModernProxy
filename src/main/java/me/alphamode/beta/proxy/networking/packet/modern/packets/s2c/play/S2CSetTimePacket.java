package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CSetTimePacket(long gameTime, long dayTime, boolean tickDayTime) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CSetTimePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			S2CSetTimePacket::gameTime,
			CommonStreamCodecs.LONG,
			S2CSetTimePacket::dayTime,
			CommonStreamCodecs.BOOL,
			S2CSetTimePacket::tickDayTime,
			S2CSetTimePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_TIME;
	}
}
