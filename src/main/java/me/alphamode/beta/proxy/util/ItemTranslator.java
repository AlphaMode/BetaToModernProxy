package me.alphamode.beta.proxy.util;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.BlockItem;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.beta.BetaItems;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.ModernItems;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;

// TODO
public final class ItemTranslator {
	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return ModernItemStack.EMPTY;
		} else {
			BrodernProxy.LOGGER.error("{}", stack.item());
			if (stack.item() instanceof BlockItem blockItem) {
				return new ModernItemStack(ModernItems.STONE, stack.count(), DataComponentPatch.EMPTY);
			} else {
				return new ModernItemStack(ModernItems.DIORITE, stack.count(), DataComponentPatch.EMPTY);
			}
		}
	}

	public static BetaItemStack toBetaStack(final ModernItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		} else {
			return new BetaItemStack(BetaItems.byId(ModernItems.getRawId(stack.item())), stack.count(), 0);
		}
	}
}
