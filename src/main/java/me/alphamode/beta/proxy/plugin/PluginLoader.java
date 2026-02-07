package me.alphamode.beta.proxy.plugin;

import com.google.gson.Gson;
import me.alphamode.beta.proxy.api.ProxyPlugin;
import me.alphamode.wisp.loader.api.WispLoader;
import me.alphamode.wisp.loader.api.components.ClasspathComponent;
import me.alphamode.wisp.loader.api.mod.LoadingMod;
import me.alphamode.wisp.loader.api.mod.Mod;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PluginLoader {
	public static final PluginLoader INSTANCE = new PluginLoader();
	private static final Logger LOGGER = LogManager.getLogger(PluginLoader.class);
	private static final Gson GSON = TextComponentSerializer.LATEST.getGson().newBuilder().setPrettyPrinting().create();
	public static final Path PLUGINS_DIR = WispLoader.get().getRunDir().resolve("plugins");
	public static final String PLUGIN_FILE = "asterial-plugin.json";

	private boolean loaded = false;
	private final Map<String, PluginContainer> plugins = new HashMap<>();

	public Map<String, PluginContainer> getPlugins() {
		if (!loaded) {
			throw new IllegalStateException("Plugins have not been loaded yet!");
		}
		return plugins;
	}

	public void loadPlugins(@Nullable Map<String, Mod> mods) {
		if (mods != null) {
			mods.forEach((name, mod) -> {
				if (mod.hasComponent(PluginMetadataV1.class)) {
					PluginMetadataV1 data = mod.getComponent(PluginMetadataV1.class);
					ProxyPlugin plugin = constructPlugin(data);
					if (plugin != null) {
						this.plugins.put(data.id(), new PluginContainer(mod, plugin, data));
					}
				}
			});
		}
		loaded = true;
	}

	@Nullable
	private ProxyPlugin constructPlugin(InternalPluginMetadata metadata) {
		try {
			return (ProxyPlugin) Class.forName(metadata.plugin()).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
				 ClassNotFoundException e) {
			LOGGER.error("Could not instantiate plugin {}", metadata.id(), e);
			return null;
		}
	}

	protected List<LoadingMod> locatePlugins() throws IOException {
		if (!Files.exists(PLUGINS_DIR)) {
			PLUGINS_DIR.toFile().mkdir();
		}

		List<LoadingMod> plugins = new ArrayList<>();

		try (Stream<Path> files = Files.list(PLUGINS_DIR)) {
			files.filter(path -> path.toString().endsWith(".jar")).forEach(path -> {
				try (FileSystem jar = FileSystems.newFileSystem(path)) {
					Path pluginFile = jar.getPath(PLUGIN_FILE);
					if (!Files.exists(pluginFile)) {
						LOGGER.warn("{} is not a valid plugin", path);
						return;
					}

					PluginMetadataV1 data = readV1Metadata(pluginFile);
					plugins.add(LoadingMod.create(data.id(), data.version())
							.addComponent(new ClasspathComponent(path))
							.addComponent(data)
					);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		}

		return plugins;
	}

	private PluginMetadataV1 readV1Metadata(Path file) throws IOException {
		return GSON.fromJson(Files.newBufferedReader(file), PluginMetadataV1.class);
	}
}
