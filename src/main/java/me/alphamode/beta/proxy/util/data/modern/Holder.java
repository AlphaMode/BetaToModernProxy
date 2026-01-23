package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;

public interface Holder<T> {
	T value();

	boolean is(Identifier identifier);

	boolean is(ResourceKey<T> resourceKey);
}
