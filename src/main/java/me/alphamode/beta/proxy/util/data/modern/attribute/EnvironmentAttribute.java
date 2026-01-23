package me.alphamode.beta.proxy.util.data.modern.attribute;

import net.lenni0451.mcstructs.core.Identifier;

public record EnvironmentAttribute<Value>(Identifier id, AttributeType<Value> type) {
}
