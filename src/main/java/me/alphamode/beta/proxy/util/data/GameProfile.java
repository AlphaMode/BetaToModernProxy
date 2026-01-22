package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record GameProfile(UUID uuid, String name, Map<String, Property> properties) {
	public static final StreamCodec<ByteBuf, GameProfile> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final GameProfile value) {
			ModernCodecs.UUID.encode(buf, value.uuid);
			ModernCodecs.stringUtf8().encode(buf, value.name);

			ModernCodecs.VAR_INT.encode(buf, value.properties.size());
			for (final Property property : value.properties.values()) {
				Property.CODEC.encode(buf, property);
			}
		}

		@Override
		public GameProfile decode(final ByteBuf buf) {
			final UUID uuid = ModernCodecs.UUID.decode(buf);
			final String name = ModernCodecs.stringUtf8().decode(buf);

			final Map<String, Property> properties = new HashMap<>();
			final int propertyCount = ModernCodecs.count(buf, 16);
			for (int i = 0; i < propertyCount; i++) {
				final Property property = Property.CODEC.decode(buf);
				properties.put(property.name(), property);
			}

			return new GameProfile(uuid, name, properties);
		}
	};

	public record Property(String name, String value, String signature) {
		public static final StreamCodec<ByteBuf, String> NAME_CODEC = ModernCodecs.stringUtf8(64);
		public static final StreamCodec<ByteBuf, String> VALUE_CODEC = ModernCodecs.stringUtf8();
		public static final StreamCodec<ByteBuf, String> SIGNATURE_CODEC = ModernCodecs.nullable(ModernCodecs.stringUtf8(1024));

		public static final StreamCodec<ByteBuf, Property> CODEC = StreamCodec.composite(
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
