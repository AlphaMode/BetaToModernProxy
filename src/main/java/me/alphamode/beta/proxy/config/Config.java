package me.alphamode.beta.proxy.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Config {
	private static final Gson GSON = TextComponentSerializer.LATEST.getGson().newBuilder().setPrettyPrinting().create();
	public static final String CONFIG_PATH = "config.json";

	private String brand = "BetaToModernProxy";
	private TextComponent message = TextComponent.of("A modern to b1.7.3 proxy server.", "Test");
	private int maxPlayers = 20;
	private String bindAddress = "0.0.0.0";
	private int bindPort = 25566;
	private String serverAddress = "0.0.0.0";

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

	public void load() {
		final Path path = Paths.get(CONFIG_PATH);
		if (!Files.exists(path)) {
			save();
		}

		try (final Reader reader = new FileReader(path.toFile())) {
			final JsonObject config = GSON.fromJson(reader, JsonObject.class);
			if (config.has("brand")) {
				this.brand = config.get("brand").getAsString();
			}

			if (config.has("message")) {
				this.message = GSON.fromJson(config.get("message"), TextComponent.class);
			}

			if (config.has("max_players")) {
				this.maxPlayers = config.get("max_players").getAsInt();
			}

			if (config.has("bind_address")) {
				this.bindAddress = config.get("bind_address").getAsString();
			}

			if (config.has("bind_port")) {
				this.bindPort = config.get("bind_port").getAsInt();
			}

			if (config.has("server_address")) {
				this.serverAddress = config.get("server_address").getAsString();
			}
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void save() {
		try (final Writer writer = new FileWriter(Paths.get(CONFIG_PATH).toFile())) {
			GSON.toJson(this, writer);
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
