package me.alphamode.beta.proxy.plugin;

import me.alphamode.beta.proxy.api.PluginMetadata;
import me.alphamode.beta.proxy.api.ProxyPlugin;
import me.alphamode.wisp.loader.api.mod.Mod;

public class PluginContainer {

    private final Mod mod;
    private final ProxyPlugin plugin;
    private final PluginMetadata metadata;

    public PluginContainer(final Mod mod, final ProxyPlugin plugin, final PluginMetadata metadata) {
        this.mod = mod;
        this.plugin = plugin;
        this.metadata = metadata;
    }

    public ProxyPlugin getPlugin() {
        return this.plugin;
    }

    public PluginMetadata getMetadata() {
        return this.metadata;
    }

    public Mod getMod() {
        return this.mod;
    }

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
