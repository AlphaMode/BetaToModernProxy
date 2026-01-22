package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.api.ProxyAPI;
import me.alphamode.beta.proxy.config.Config;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetServer;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class Proxy implements ProxyAPI {
    private static Proxy instance;

    public static Proxy getProxy() {
        if (instance == null)
            throw new IllegalStateException("Proxy has not been initialized yet");
        return instance;
    }

    private static final CompoundTag DEFAULT_TAGS;
    private static final CompoundTag DEFAULT_REGISTRIES;

    static {
        CompoundTag tag;
        try {
            tag = NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/vanilla_tags.nbt")))), new NbtReadTracker()).getTag().asCompoundTag();
        } catch (final Exception exception) {
            tag = new CompoundTag();
        }

        DEFAULT_TAGS = tag;

        try {
            tag = NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/vanilla_registries.nbt")))), new NbtReadTracker()).getTag().asCompoundTag();
        } catch (final Exception exception) {
            tag = new CompoundTag();
        }

        DEFAULT_REGISTRIES = tag;
    }

    private final Config config;
    private String brand;
    private TextComponent motd;
    private final String bindAddress;
    private final int bindPort;
    private final String serverAddress;

    public Proxy(final Config config) {
        instance = this;
        this.config = config;
        this.brand = config.getBrand();
        this.motd = config.getMotd();
        this.bindAddress = config.getBindAddress();
        this.bindPort = config.getBindPort();
        this.serverAddress = config.getServerAddress();
    }

    public void listen() {
        IO.println(String.format("Listening on %s:%d -> %s", this.bindAddress, this.bindPort, this.serverAddress));
        new NetServer(new ProxyChannel(this.serverAddress, DEFAULT_TAGS, DEFAULT_REGISTRIES))
                .bind(new InetSocketAddress(this.bindAddress, this.bindPort));
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public void setMotd(TextComponent description) {
        this.motd = description;
    }

    @Override
    public TextComponent getMotd() {
        return this.motd;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }
}
