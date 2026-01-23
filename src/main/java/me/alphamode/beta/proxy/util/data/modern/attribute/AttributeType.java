package me.alphamode.beta.proxy.util.data.modern.attribute;

import net.lenni0451.mcstructs.converter.codec.Codec;

import java.util.Map;

public record AttributeType<Value>(
        Codec<Value> valueCodec,
        Map<AttributeModifier.OperationId, Codec<?>> modifierLibrary,
        Codec<AttributeModifier<Value, ?>> modifierCodec
) {
}
