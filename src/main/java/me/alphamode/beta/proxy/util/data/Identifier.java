package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Identifier(String namespace, String path) {
	public static final StreamCodec<ByteBuf, Identifier> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Identifier value) {
			ModernCodecs.stringUtf8().encode(buf, value.toString());
		}

		@Override
		public Identifier decode(final ByteBuf buf) {
			return Identifier.ofNullable(ModernCodecs.stringUtf8().decode(buf));
		}
	};

	public Identifier(String input) throws Exception {
		String namespace;
		String path;
		if (!input.contains(":")) {
			namespace = "minecraft";
			path = input;
		} else {
			final String[] parts = input.split(":", 1);
			if (parts.length < 2) {
				throw new Exception("Invalid identifier");
			} else {
				namespace = parts[0];
				path = parts[1];
			}
		}

		this(namespace, path);
	}

	public static Identifier vanilla(final String path) {
		return new Identifier("minecraft", path);
	}

	public static Identifier ofNullable(final String input) {
		try {
			return new Identifier(input);
		} catch (final Exception exception) {
			return null;
		}
	}

	@Override
	public String toString() {
		return this.namespace + ":" + this.path;
	}
}
