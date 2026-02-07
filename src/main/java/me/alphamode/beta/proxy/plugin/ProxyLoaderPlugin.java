package me.alphamode.beta.proxy.plugin;

import me.alphamode.wisp.loader.api.mod.LoadingMod;
import me.alphamode.wisp.loader.api.plugin.LoaderPlugin;
import me.alphamode.wisp.loader.api.plugin.PluginContext;

import java.io.IOException;

public class ProxyLoaderPlugin implements LoaderPlugin {
	@Override
	public void init(PluginContext context) {
		try {
			context.addMods(PluginLoader.INSTANCE.locatePlugins().toArray(LoadingMod[]::new));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
