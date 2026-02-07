package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CPlayKeepAlivePacket(long id) implements S2CCommonKeepAlivePacket<ClientboundPlayPackets> {
	public static final StreamCodec<ByteStream, S2CPlayKeepAlivePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			S2CPlayKeepAlivePacket::id,
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
	public long getId() {
		return this.id;
	}
}
