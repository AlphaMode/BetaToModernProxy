package me.alphamode.beta.proxy.wisp;

import me.alphamode.wisp.loader.api.plugin.LoaderPlugin;
import me.alphamode.wisp.loader.api.plugin.PluginContext;

public class BootstrapLoaderPlugin implements LoaderPlugin {

    @Override
    public void preInit(PluginContext context) {
        context.registerGameLocator(new ProxyProvider());
    }

    @Override
    public void init(PluginContext context) {

    }
}
