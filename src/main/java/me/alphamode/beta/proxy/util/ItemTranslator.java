package me.alphamode.beta.proxy.util;

import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.beta.BetaItems;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.ModernItems;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;

// TODO
public final class ItemTranslator {
	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return new ModernItemStack(ModernItems.DIORITE, 1, DataComponentPatch.EMPTY);
		} else {
			return new ModernItemStack(ModernItems.DIORITE, stack.count(), DataComponentPatch.EMPTY);
		}
	}

	public static BetaItemStack toBetaStack(final ModernItemStack stack) {
		return new BetaItemStack(BetaItems.byId(ModernItems.getRawId(stack.item())), stack.count(), 0);
	}
}
