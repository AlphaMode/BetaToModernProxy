package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class DimensionType {
	// TODO
	public static final StreamCodec<ByteBuf, Holder<DimensionType>> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Holder<DimensionType> value) {
			ModernStreamCodecs.VAR_INT.encode(buf, 0);
		}

		@Override
		public Holder<DimensionType> decode(final ByteBuf buf) {
			ModernStreamCodecs.VAR_INT.decode(buf);
			return null;
		}
	};
}
