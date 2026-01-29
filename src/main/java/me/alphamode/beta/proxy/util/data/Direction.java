package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.Arrays;
import java.util.Comparator;

public enum Direction {
	DOWN(0),
	UP(1),
	NORTH(2),
	SOUTH(3),
	WEST(4),
	EAST(5);

	private static final Direction[] VALUES = values();
	private static final Direction[] BY_3D_DATA = Arrays.stream(VALUES).sorted(Comparator.comparingInt(d -> d.data3d)).toArray(Direction[]::new);

	public static final StreamCodec<ByteBuf, Direction> CODEC_3D = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Direction value) {
			buf.writeByte(value.data3d);
		}

		@Override
		public Direction decode(final ByteBuf buf) {
			return Direction.from3DDataValue(buf.readUnsignedByte());
		}
	};

	private final int data3d;

	Direction(int data3d) {
		this.data3d = data3d;
	}

	public static Direction from3DDataValue(int data) {
		return BY_3D_DATA[Math.abs(data % BY_3D_DATA.length)];
	}
}
