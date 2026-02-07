package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public enum BetaEntityTypes {
	CREEPER(50),
	SKELETON(51),
	SPIDER(52),
	GIANT(53),
	ZOMBIE(54),
	SLIME(55),
	GHAST(56),
	ZOMBIE_PIGMEN(57),
	PIG(90),
	SHEEP(91),
	COW(92),
	CHICKEN(93),
	SQUID(94),
	WOLF(95);

	public static final StreamCodec<ByteStream, BetaEntityTypes> CODEC = CommonStreamCodecs.BYTE.map(BetaEntityTypes::fromId, (type) -> (byte) type.id);
	private final int id;

	BetaEntityTypes(final int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

	public static BetaEntityTypes fromId(final int id) {
		for (final BetaEntityTypes type : values()) {
			if (type.id == id) {
				return type;
			}
		}

		return null;
	}
}
