package me.alphamode.beta.proxy.wisp;

import me.alphamode.wisp.loader.api.ArgumentList;
import me.alphamode.wisp.loader.api.plugin.PluginContext;
import me.alphamode.wisp.loader.api.plugin.Provider;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import java.util.List;

public class ProxyProvider implements Provider {
    @Override
    public Path provide(List<Path> classPaths, String[] args) {
        return null;
    }

    @Override
    public List<Path> getClassPaths(String[] args) {
        return List.of();
    }

    @Override
    public String getMainClass() {
        return "me.alphamode.beta.proxy.Main";
    }

    @Override
    public void launch(ArgumentList arguments) {
        try {
            MethodHandles.lookup().findStatic(PluginContext.CLASS_LOADER.loadClass(getMainClass()), "main", MethodType.methodType(Void.TYPE, String[].class)).asFixedArity().invokeExact(arguments.toArray());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
