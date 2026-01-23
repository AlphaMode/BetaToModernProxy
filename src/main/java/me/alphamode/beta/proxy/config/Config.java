package me.alphamode.beta.proxy.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public final class Config {
	private static final Gson GSON = TextComponentSerializer.LATEST.getGson().newBuilder().setPrettyPrinting().create();
	public static final Path path = Paths.get("config.json");

	public static final StreamCodec<JsonObject, Config> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final JsonObject object, final Config value) {
			object.addProperty("brand", value.brand);
			object.addProperty("message", value.message.asLegacyFormatString());
			object.addProperty("max_players", value.maxPlayers);
			object.addProperty("bind_address", value.bindAddress);
			object.addProperty("bind_port", value.bindPort);
			object.addProperty("server_address", value.serverAddress);
			object.addProperty("server_port", value.serverPort);
		}

		@Override
		public Config decode(final JsonObject object) {
			final Config config = new Config();
			final Map<String, JsonElement> elementMap = object.asMap();
			config.brand = elementMap.getOrDefault("brand", new JsonPrimitive("BetaToModernProxy")).getAsString();
			config.message = TextComponent.of(elementMap.getOrDefault("message", new JsonPrimitive("A modern to b1.7.3 proxy server.")).getAsString());
			config.maxPlayers = elementMap.getOrDefault("max_players", new JsonPrimitive(20)).getAsInt();
			config.bindAddress = elementMap.getOrDefault("bind_address", new JsonPrimitive("0.0.0.0")).getAsString();
			config.bindPort = elementMap.getOrDefault("bind_port", new JsonPrimitive(25566)).getAsInt();
			config.serverAddress = elementMap.getOrDefault("server_address", new JsonPrimitive("0.0.0.0")).getAsString();
			config.serverPort = elementMap.getOrDefault("server_port", new JsonPrimitive(25565)).getAsInt();
			return config;
		}
	};

	private String brand = "BetaToModernProxy";
	private TextComponent message = TextComponent.of("A modern to b1.7.3 proxy server.");
	private int maxPlayers = 20;
	private String bindAddress = "0.0.0.0";
	private int bindPort = 25566;
	private String serverAddress = "0.0.0.0";
	private int serverPort = 25565;

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	public TextComponent getMessage() {
		return this.message;
	}

	public void setMessage(final TextComponent message) {
		this.message = message;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public void setMaxPlayers(final int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public String getBindAddress() {
		return this.bindAddress;
	}

	public int getBindPort() {
		return this.bindPort;
	}

	public String getServerAddress() {
		return this.serverAddress;
	}

	public int getServerPort() {
		return this.serverPort;
	}

	public void load() {
		if (!Files.exists(path)) {
			this.save();
			return;
		}

		try (final Reader reader = new FileReader(path.toFile())) {
			final Config config = CODEC.decode(GSON.fromJson(reader, JsonObject.class));
			this.brand = config.brand;
			this.message = config.message;
			this.maxPlayers = config.maxPlayers;
			this.bindAddress = config.bindAddress;
			this.bindPort = config.bindPort;
			this.serverAddress = config.serverAddress;
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void save() {
		try (final Writer writer = new FileWriter(path.toFile())) {
			final JsonObject object = new JsonObject();
			CODEC.encode(object, this);
			GSON.toJson(object, writer);
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
