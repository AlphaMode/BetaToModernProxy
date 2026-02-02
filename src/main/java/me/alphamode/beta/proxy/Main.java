package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.api.PluginLoader;
import me.alphamode.beta.proxy.config.Config;

public final class Main {
	static void main(final String[] args) {
		final PluginLoader loader = new PluginLoader();
		loader.load();
		loader.onProxyStart();
		new BrodernProxy(new Config()).listen();
		loader.onProxyClose();
	}
}
