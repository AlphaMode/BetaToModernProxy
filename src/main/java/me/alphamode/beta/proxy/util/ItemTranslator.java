package me.alphamode.beta.proxy.util;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;

public final class ItemTranslator {
	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return ModernItemStack.EMPTY;
		} else {
			final int modernId = BrodernProxy.getBetaToModernItems().getInt(String.valueOf(stack.item().id()));
			return new ModernItemStack(modernId, stack.count(), DataComponentPatch.EMPTY);
		}
	}
}
