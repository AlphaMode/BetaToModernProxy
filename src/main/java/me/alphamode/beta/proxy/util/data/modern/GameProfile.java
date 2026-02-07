package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record GameProfile(UUID uuid, String name, Map<String, Property> properties) {
	public static final StreamCodec<ByteStream, GameProfile> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final GameProfile value) {
			ModernStreamCodecs.UUID.encode(buf, value.uuid);
			ModernStreamCodecs.STRING_UTF8.encode(buf, value.name);

			ModernStreamCodecs.VAR_INT.encode(buf, value.properties.size());
			for (final Property property : value.properties.values()) {
				Property.CODEC.encode(buf, property);
			}
		}

		@Override
		public GameProfile decode(final ByteStream buf) {
			final UUID uuid = ModernStreamCodecs.UUID.decode(buf);
			final String name = ModernStreamCodecs.STRING_UTF8.decode(buf);

			final Map<String, Property> properties = new HashMap<>();
			final int propertyCount = ModernStreamCodecs.readCount(buf, 16);
			for (int i = 0; i < propertyCount; i++) {
				final Property property = Property.CODEC.decode(buf);
				properties.put(property.name(), property);
			}

			return new GameProfile(uuid, name, properties);
		}
	};

	public record Property(String name, String value, String signature) {
		public static final StreamCodec<ByteStream, String> NAME_CODEC = ModernStreamCodecs.stringUtf8(64);
		public static final StreamCodec<ByteStream, String> VALUE_CODEC = ModernStreamCodecs.STRING_UTF8;
		public static final StreamCodec<ByteStream, String> SIGNATURE_CODEC = ModernStreamCodecs.nullable(ModernStreamCodecs.stringUtf8(1024));

		public static final StreamCodec<ByteStream, Property> CODEC = StreamCodec.composite(
				NAME_CODEC,
				Property::name,
				VALUE_CODEC,
				Property::value,
				SIGNATURE_CODEC,
				Property::signature,
				Property::new
		);
	}
}
