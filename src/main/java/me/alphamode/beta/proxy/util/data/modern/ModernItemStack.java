package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Item;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

// TODO: item components
public record ModernItemStack(Item item, CompoundTag data) {
	public static final StreamCodec<ByteBuf, ModernItemStack> OPTIONAL_CODEC = null;
}
