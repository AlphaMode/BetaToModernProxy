package me.alphamode.beta.proxy;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import me.alphamode.beta.proxy.config.Config;
import me.alphamode.beta.proxy.networking.ProxyChannelInitializer;
import me.alphamode.beta.proxy.util.data.beta.BetaSynchedEntityData;
import me.alphamode.beta.proxy.util.data.modern.entity.ModernSynchedEntityData;
import me.alphamode.beta.proxy.util.translators.BlockTranslator;
import me.alphamode.beta.proxy.util.NbtUtil;
import me.alphamode.beta.proxy.util.data.beta.BetaBlocks;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItems;
import me.alphamode.beta.proxy.util.translators.EntityDataTranslator;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.raphimc.netminecraft.netty.connection.NetServer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public record BrodernProxy(Config config, KeyPair keyPair, MinecraftSessionService sessionService,
						   GameProfileRepository profileRepository) {
	public static final Logger LOGGER = LogManager.getLogger(BrodernProxy.class);

	private static BrodernProxy INSTANCE;
	private static final CompoundTag DEFAULT_TAGS;
	private static final CompoundTag DEFAULT_REGISTRIES;
	private static final CompoundTag BETA_TO_MODERN_ITEMS;
	private static final BlockTranslator BLOCK_TRANSLATOR;

	static {
		CompoundTag tag;
		try {
			tag = NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/vanilla_tags.nbt")))), new NbtReadTracker()).getTag().asCompoundTag();
		} catch (final Exception exception) {
			tag = new CompoundTag();
		}

		DEFAULT_TAGS = tag;

		try {
			tag = NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/beta_registries.nbt")))), new NbtReadTracker()).getTag().asCompoundTag();
		} catch (final Exception exception) {
			tag = new CompoundTag();
		}

		DEFAULT_REGISTRIES = tag;

		try {
			tag = NbtIO.LATEST.read(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/beta_to_modern_items.nbt")))), new NbtReadTracker()).asCompoundTag();
		} catch (final Exception exception) {
			tag = new CompoundTag();
		}

		BETA_TO_MODERN_ITEMS = tag;

		try {
			BLOCK_TRANSLATOR = new BlockTranslator(NbtUtil.readCompressed(Main.class.getResourceAsStream("/beta_to_modern_blocks.nbt")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public BrodernProxy {
		INSTANCE = this;
		config.load();
	}

	public void listen() {
		BetaItems.bootstrap();
		BetaBlocks.bootstrap();

		final String bindAddress = this.config.getBindAddress();
		final int bindPort = this.config.getBindPort();
		final String serverAddress = this.config.getServerAddress();
		final int serverPort = this.config.getServerPort();

		LOGGER.info("Listening on {}:{} -> {}:{}", bindAddress, bindPort, serverAddress, serverPort);
		new NetServer(new ProxyChannelInitializer(MinecraftServerAddress.ofResolved(serverAddress, serverPort)))
				.bind(new InetSocketAddress(bindAddress, bindPort));
	}

	public boolean isDebug() {
		return this.config.isDebug();
	}

	public static CompoundTag getDefaultTags() {
		return DEFAULT_TAGS;
	}

	public static CompoundTag getDefaultRegistries() {
		return DEFAULT_REGISTRIES;
	}

	public static BlockTranslator getBlockTranslator() {
		return BLOCK_TRANSLATOR;
	}

	public static CompoundTag getBetaToModernItems() {
		return BETA_TO_MODERN_ITEMS;
	}

	public static BrodernProxy getProxy() {
		if (INSTANCE == null) {
			throw new IllegalStateException("Proxy has not been initialized yet");
		}

		return INSTANCE;
	}
}
