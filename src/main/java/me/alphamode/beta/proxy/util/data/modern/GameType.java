package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.function.IntFunction;

public enum GameType implements StringRepresentable {
	SURVIVAL(0, "survival"),
	CREATIVE(1, "creative"),
	ADVENTURE(2, "adventure"),
	SPECTATOR(3, "spectator");

	public static final StringRepresentable.EnumCodec<GameType> CODEC = StringRepresentable.fromEnum(GameType::values);
	private static final IntFunction<GameType> BY_ID = ByIdMap.continuous(GameType::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteBuf, GameType> STREAM_CODEC = ModernStreamCodecs.idMapper(BY_ID, GameType::getId);

	public static final StreamCodec<ByteBuf, GameType> STREAM_CODEC_BYTE = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final GameType value) {
			buf.writeByte(value.id);
		}

		@Override
		public GameType decode(final ByteBuf buf) {
			return GameType.byId(buf.readByte());
		}
	};

	private final int id;
	private final String name;

	GameType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public static GameType byId(int id) {
		return BY_ID.apply(id);
	}
}
