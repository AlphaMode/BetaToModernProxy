package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.data.Item;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

// TODO: item components
public record ModernItemStack(Item item, CompoundTag data) {
}
