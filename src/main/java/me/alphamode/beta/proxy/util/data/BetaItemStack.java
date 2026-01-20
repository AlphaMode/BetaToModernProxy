package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.StreamCodec;

public record BetaItemStack(int id, int count, int auxValue) {
	public static final StreamCodec<ByteBuf, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack value) {
			buf.writeShort(value.id);
			buf.writeByte(value.count);
			buf.writeShort(value.auxValue);
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			return new BetaItemStack(buf.readShort(), buf.readByte(), buf.readShort());
		}
	};

	public static final StreamCodec<ByteBuf, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack value) {
			if (value == null) {
				buf.writeShort(-1);
			} else {
				buf.writeShort(value.id);
				buf.writeByte(value.count);
				buf.writeShort(value.auxValue);
			}
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			if (id >= 0) {
				return new BetaItemStack(id, buf.readByte(), buf.readShort());
			} else {
				return null;
			}
		}
	};
}
