package me.alphamode.beta.proxy.util.data.modern.chat;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.Holder;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.Optional;

public class ChatType {
	public static final StreamCodec<ByteBuf, Holder<ChatType>> CODEC = null; // TODO

	public record Bound(Holder<ChatType> chatType, TextComponent name, Optional<TextComponent> targetName) {
		public static final StreamCodec<ByteBuf, Bound> CODEC = StreamCodec.composite(
				ChatType.CODEC,
				ChatType.Bound::chatType,
				ModernStreamCodecs.TEXT_COMPONENT,
				ChatType.Bound::name,
				ModernStreamCodecs.optional(ModernStreamCodecs.TEXT_COMPONENT),
				ChatType.Bound::targetName,
				ChatType.Bound::new
		);
	}
}
