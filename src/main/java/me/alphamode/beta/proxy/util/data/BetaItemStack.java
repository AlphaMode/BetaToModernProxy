package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BetaItemStack(int id, int count, int aux) {
	public static final StreamCodec<ByteBuf, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack value) {
			buf.writeShort(value.id);
			buf.writeByte(value.count);
			buf.writeShort(value.aux);
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
				buf.writeShort(value.aux);
			}
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			return id < 0 ? null : new BetaItemStack(id, buf.readByte(), buf.readShort());
		}
	};
}
