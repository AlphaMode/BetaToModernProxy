package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record DimensionType(int networkId) {
	public static final StreamCodec<ByteStream, DimensionType> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final DimensionType value) {
			ModernStreamCodecs.VAR_INT.encode(stream, value.networkId);
		}

		@Override
		public DimensionType decode(final ByteStream stream) {
			return new DimensionType(ModernStreamCodecs.VAR_INT.decode(stream));
		}
	};
}
