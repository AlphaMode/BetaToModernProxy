package me.alphamode.beta.proxy.util.data.modern.attribute;

import net.lenni0451.mcstructs.converter.codec.Codec;

import java.util.Map;

// This is a fucking mess
public class EnvironmentAttributeMap {
	public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(/*Map.of()*/);
	public static final Codec<EnvironmentAttributeMap> CODEC = null;

	private final Map<EnvironmentAttribute<?>, EnvironmentAttributeMap.Entry<?, ?>> entries = Map.of();

	public record Entry<Value, Argument>(Argument argument, AttributeModifier<Value, Argument> modifier) {
	}
}
