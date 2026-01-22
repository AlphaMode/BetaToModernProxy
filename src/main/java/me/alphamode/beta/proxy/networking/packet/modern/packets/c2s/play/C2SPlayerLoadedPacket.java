package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SPlayerLoadedPacket implements C2SPlayPacket {
	public static final C2SPlayerLoadedPacket INSTANCE = new C2SPlayerLoadedPacket();
	public static final StreamCodec<ByteBuf, C2SPlayerLoadedPacket> CODEC = StreamCodec.unit(INSTANCE);

	private C2SPlayerLoadedPacket() {
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PLAYER_LOADED;
	}
}
