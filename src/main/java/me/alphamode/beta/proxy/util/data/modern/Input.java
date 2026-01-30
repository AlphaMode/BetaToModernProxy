package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Input(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift,
					boolean sprint) {
	public static final byte FLAG_FORWARD = 1;
	public static final byte FLAG_BACKWARD = 2;
	public static final byte FLAG_LEFT = 4;
	public static final byte FLAG_RIGHT = 8;
	public static final byte FLAG_JUMP = 16;
	public static final byte FLAG_SHIFT = 32;
	public static final byte FLAG_SPRINT = 64;

	public static final StreamCodec<ByteBuf, Input> CODEC = StreamCodec.ofMember((input, buf) -> buf.writeByte(input.pack()), (buf) -> Input.unpack(buf.readByte()));

	public static Input unpack(final int flags) {
		return new Input((flags & FLAG_FORWARD) != 0,
				(flags & FLAG_BACKWARD) != 0,
				(flags & FLAG_LEFT) != 0,
				(flags & FLAG_RIGHT) != 0,
				(flags & FLAG_JUMP) != 0,
				(flags & FLAG_SHIFT) != 0,
				(flags & FLAG_SPRINT) != 0);
	}

	public int pack() {
		return (this.forward() ? FLAG_FORWARD : 0)
				| (this.backward() ? FLAG_BACKWARD : 0)
				| (this.left() ? FLAG_LEFT : 0)
				| (this.right() ? FLAG_RIGHT : 0)
				| (this.jump() ? FLAG_JUMP : 0)
				| (this.shift() ? FLAG_SHIFT : 0)
				| (this.sprint() ? FLAG_SPRINT : 0);
	}
}
