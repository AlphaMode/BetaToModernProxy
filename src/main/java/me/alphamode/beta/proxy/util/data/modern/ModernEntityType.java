package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public enum ModernEntityType {
	CHICKEN(26),
	COW(30),
	GIANT(59),
	ITEM(71),
	PIGLIN(101),
	SKELETON(115),
	SPIDER(124),
	SQUID(127),
	ZOMBIE(150),
	PLAYER(155);

	public static final StreamCodec<ByteBuf, ModernEntityType> CODEC = ModernStreamCodecs.VAR_INT.map(ModernEntityType::fromId, ModernEntityType::id);
	private final int id;

	ModernEntityType(final int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

	public static ModernEntityType fromId(final int id) {
		for (final ModernEntityType type : values()) {
			if (type.id == id) {
				return type;
			}
		}

		throw new IndexOutOfBoundsException();
	}
}
