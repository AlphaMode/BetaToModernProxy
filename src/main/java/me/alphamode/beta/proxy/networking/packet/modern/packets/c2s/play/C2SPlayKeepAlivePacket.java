package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPlayKeepAlivePacket(long id) implements C2SCommonKeepAlivePacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteStream, C2SPlayKeepAlivePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			C2SPlayKeepAlivePacket::id,
			C2SPlayKeepAlivePacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.KEEP_ALIVE;
	}

	@Override
	public long getId() {
		return this.id;
	}
}
