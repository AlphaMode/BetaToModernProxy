package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SLoginAcknowledgedPacket implements C2SLoginPacket {
	public static final C2SLoginAcknowledgedPacket INSTANCE = new C2SLoginAcknowledgedPacket();
	public static final StreamCodec<ByteBuf, C2SLoginAcknowledgedPacket> CODEC = StreamCodec.unit(INSTANCE);

	private C2SLoginAcknowledgedPacket() {
	}

	@Override
	public ServerboundLoginPackets getType() {
		return ServerboundLoginPackets.ACKNOWLEDGED;
	}
}
