package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.FilterMask;
import me.alphamode.beta.proxy.util.data.modern.MessageSignature;
import me.alphamode.beta.proxy.util.data.modern.chat.ChatType;
import me.alphamode.beta.proxy.util.data.modern.chat.SignedMessageBody;
import net.lenni0451.mcstructs.text.TextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record S2CPlayerChatPacket(
		int globalIndex,
		UUID sender,
		int index,
		@Nullable MessageSignature signature,
		SignedMessageBody.Packed body,
		@Nullable TextComponent unsignedContent,
		FilterMask filterMask,
		ChatType.Bound chatType
) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CPlayerChatPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CPlayerChatPacket::globalIndex,
			ModernStreamCodecs.UUID,
			S2CPlayerChatPacket::sender,
			ModernStreamCodecs.VAR_INT,
			S2CPlayerChatPacket::index,
			ModernStreamCodecs.nullable(MessageSignature.CODEC),
			S2CPlayerChatPacket::signature,
			SignedMessageBody.Packed.CODEC,
			S2CPlayerChatPacket::body,
			ModernStreamCodecs.nullable(ModernStreamCodecs.TEXT_COMPONENT),
			S2CPlayerChatPacket::unsignedContent,
			FilterMask.CODEC,
			S2CPlayerChatPacket::filterMask,
			ChatType.Bound.CODEC,
			S2CPlayerChatPacket::chatType,
			S2CPlayerChatPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.PLAYER_CHAT;
	}
}
