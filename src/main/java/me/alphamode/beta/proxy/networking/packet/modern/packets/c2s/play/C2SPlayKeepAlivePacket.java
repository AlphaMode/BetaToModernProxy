package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPlayKeepAlivePacket(long id) implements C2SCommonKeepAlivePacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteBuf, C2SPlayKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
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
	public long getTime() {
		return this.id;
	}
}
