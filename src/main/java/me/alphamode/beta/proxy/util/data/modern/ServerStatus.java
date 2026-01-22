package me.alphamode.beta.proxy.util.data.modern;

import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_20_5.JsonConverter_v1_20_5;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ServerStatus(TextComponent description,
						   Optional<Players> players,
						   Optional<ServerStatus.Version> version,
						   Optional<ServerStatus.Favicon> favicon,
						   boolean enforcesSecureChat) {
	public static final Codec<ServerStatus> CODEC = MapCodecMerger.codec(
			TextComponentCodec.LATEST.getTextCodec().mapCodec("description").required(), ServerStatus::description,
			Players.CODEC.mapCodec("players").optional().defaulted(null), serverStatus -> serverStatus.players.orElse(null),
			Version.CODEC.mapCodec("version").optional().defaulted(null), serverStatus -> serverStatus.version.orElse(null),
			Favicon.CODEC.mapCodec("favicon").optional().defaulted(null), serverStatus -> serverStatus.favicon.orElse(null),
			Codec.BOOLEAN.mapCodec("enforcesSecureChat").optional().defaulted(false), ServerStatus::enforcesSecureChat,
			ServerStatus::new
	);

	public static final StreamCodec<ByteBuf, ServerStatus> STREAM_CODEC = new StreamCodec<>() {
		private static final StreamCodec<ByteBuf, JsonElement> JSON = ModernCodecs.lenientJson(ModernCodecs.MAX_STRING_LENGTH);

		@Override
		public void encode(final ByteBuf buf, final ServerStatus value) {
			JSON.encode(buf, CODEC.serialize(JsonConverter_v1_20_5.INSTANCE, value).getOrThrow(RuntimeException::new));
		}

		@Override
		public ServerStatus decode(final ByteBuf buf) {
			return CODEC.deserialize(JsonConverter_v1_20_5.INSTANCE, JSON.decode(buf)).getOrThrow(RuntimeException::new);
		}
	};

	public ServerStatus(TextComponent description,
						@Nullable Players players,
						@Nullable ServerStatus.Version version,
						@Nullable ServerStatus.Favicon favicon,
						boolean enforcesSecureChat) {
		this(description, Optional.ofNullable(players), Optional.ofNullable(version), Optional.ofNullable(favicon), enforcesSecureChat);
	}

	public ServerStatus(TextComponent description, ServerStatus.Version version) {
		this(description, Optional.of(new ServerStatus.Players(20, 0, List.of())), Optional.of(version), Optional.empty(), false);
	}

	public record Favicon(byte[] iconBytes) {
		public static final Codec<Favicon> CODEC = Codec.STRING.flatMap(favicon -> Result.success("data:image/png;base64," + new String(Base64.getEncoder().encode(favicon.iconBytes), StandardCharsets.UTF_8)), data -> {
			if (!data.startsWith("data:image/png;base64,")) {
				return Result.error("Unknown format");
			} else {
				try {
					final String base64 = data.substring("data:image/png;base64,".length()).replaceAll("\n", "");
					final byte[] iconBytes = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
					return Result.success(new ServerStatus.Favicon(iconBytes));
				} catch (IllegalArgumentException var3) {
					return Result.error("Malformed base64 server icon");
				}
			}
		});
	}

	public record Players(int max, int online, List<NameAndId> sample) {
		public static final Codec<Players> CODEC = MapCodecMerger.codec(
				Codec.INTEGER.mapCodec("max").required(), Players::max,
				Codec.INTEGER.mapCodec("online").required(), Players::online,
				NameAndId.CODEC.optionalListOf().mapCodec("sample").optional().defaulted(List.of()), Players::sample,
				Players::new
		);
	}

	public record NameAndId(UUID id, String name) {
		public static final Codec<NameAndId> CODEC = MapCodecMerger.codec(
				Codec.STRICT_STRING_UUID.mapCodec("id").required(), NameAndId::id,
				Codec.STRING.mapCodec("name").required(), NameAndId::name,
				NameAndId::new
		);
	}

	public record Version(String name, int protocol) {
		public static final Codec<Version> CODEC = MapCodecMerger.codec(
				Codec.STRING.mapCodec("name").required(), Version::name,
				Codec.INTEGER.mapCodec("protocol").required(), Version::protocol,
				Version::new
		);
	}
}
