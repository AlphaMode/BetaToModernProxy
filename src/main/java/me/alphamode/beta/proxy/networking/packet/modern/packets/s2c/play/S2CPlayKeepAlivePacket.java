package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CPlayKeepAlivePacket(long time) implements S2CCommonKeepAlivePacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteBuf, S2CPlayKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			S2CPlayKeepAlivePacket::time,
			S2CPlayKeepAlivePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.KEEP_ALIVE;
	}

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
