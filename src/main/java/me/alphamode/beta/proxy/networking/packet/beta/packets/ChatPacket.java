package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public class ChatPacket implements Packet {
	public static final int MAX_CHAT_STRING_LENGTH = 119;
	public static final StreamCodec<ByteBuf, String> CHAT_STRING_CODEC = ByteBufCodecs.stringUtf8(MAX_CHAT_STRING_LENGTH);

	public String message;

	public ChatPacket() {
	}

	public ChatPacket(final String message) {
		this.message = message.substring(0, MAX_CHAT_STRING_LENGTH);
	}

	@Override
	public void read(final ByteBuf data, final int protocolVersion) {
		this.message = CHAT_STRING_CODEC.decode(data);
	}

	@Override
	public void write(final ByteBuf data, final int protocolVersion) {
		CHAT_STRING_CODEC.encode(data, this.message);
	}
}
