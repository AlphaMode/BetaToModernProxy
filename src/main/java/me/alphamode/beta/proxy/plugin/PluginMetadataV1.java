package me.alphamode.beta.proxy.plugin;

public record PluginMetadataV1(int schemaVersion, String id, String version, String plugin) implements InternalPluginMetadata {
}
