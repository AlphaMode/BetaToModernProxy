package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.RemoteChatSession;

public record C2SChatSessionUpdatePacket(RemoteChatSession.Data session) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SChatSessionUpdatePacket> CODEC = StreamCodec.composite(
			RemoteChatSession.Data.CODEC,
			C2SChatSessionUpdatePacket::session,
			C2SChatSessionUpdatePacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHAT_SESSION_UPDATE;
	}
}
