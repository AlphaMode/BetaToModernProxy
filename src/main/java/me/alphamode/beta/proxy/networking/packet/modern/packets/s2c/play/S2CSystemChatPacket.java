package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CSystemChatPacket(TextComponent content, boolean overlay) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CSystemChatPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.TEXT_COMPONENT,
			S2CSystemChatPacket::content,
			BasicStreamCodecs.BOOL,
			S2CSystemChatPacket::overlay,
			S2CSystemChatPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SYSTEM_CHAT;
	}
}
