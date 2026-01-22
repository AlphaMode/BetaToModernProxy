package me.alphamode.beta.proxy.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final Gson GSON = TextComponentSerializer.LATEST.getGson().newBuilder().setPrettyPrinting().create();
    public static final String CONFIG_PATH = "config.json";

    private String brand = "BetaToModernProxy";
    private TextComponent motd = TextComponent.of("A modern to b1.7.3 proxy server.", "Test");
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

    public TextComponent getMotd() {
        return this.motd;
    }

    public void setMotd(final TextComponent motd) {
        this.motd = motd;
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
        Path path = Paths.get(CONFIG_PATH);
        if (!Files.exists(path))
            save();

        try (Reader reader = new FileReader(path.toFile())) {
            JsonObject config = GSON.fromJson(reader, JsonObject.class);
            if (config.has("brand"))
                this.brand = config.get("brand").getAsString();
            if (config.has("motd"))
                this.motd = GSON.fromJson(config.get("motd"), TextComponent.class);
            if (config.has("maxPlayers"))
                this.maxPlayers = config.get("maxPlayers").getAsInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        try (Writer writer = new FileWriter(Paths.get(CONFIG_PATH).toFile())) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
