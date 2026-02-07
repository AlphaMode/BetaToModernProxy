package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SClientTickEndPacket implements C2SPlayPacket {
	public static C2SClientTickEndPacket INSTANCE = new C2SClientTickEndPacket();
	public static StreamCodec<ByteStream, C2SClientTickEndPacket> CODEC = StreamCodec.unit(INSTANCE);

	private C2SClientTickEndPacket() {
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CLIENT_TICK_END;
	}
}
