package me.alphamode.beta.proxy.plugin;

import me.alphamode.beta.proxy.api.PluginMetadata;
import me.alphamode.beta.proxy.api.ProxyPlugin;
import me.alphamode.wisp.loader.api.mod.Mod;

public record PluginContainer(Mod mod, ProxyPlugin plugin, PluginMetadata metadata) {


	@Override
	public int hashCode() {
		return this.metadata.id().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PluginContainer other) {
			return this.metadata.id().equals(other.metadata.id());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "PluginContainer[id=" + this.metadata.id() + "]";
	}
}
