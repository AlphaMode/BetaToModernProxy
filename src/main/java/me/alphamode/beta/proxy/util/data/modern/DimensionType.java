package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class DimensionType {
	// TODO
	public static final StreamCodec<ByteStream, Holder<DimensionType>> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final Holder<DimensionType> value) {
			ModernStreamCodecs.VAR_INT.encode(buf, 0);
		}

		@Override
		public Holder<DimensionType> decode(final ByteStream buf) {
			ModernStreamCodecs.VAR_INT.decode(buf);
			return null;
		}
	};
}
