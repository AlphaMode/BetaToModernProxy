package me.alphamode.beta.proxy.util;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

public final class ItemTranslator {
	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return ModernItemStack.EMPTY;
		} else {
			final ItemTranslation translation = getTranslation(stack);

			int modernId;
			if (!translation.auxMapping.isEmpty()) {
				modernId = translation.auxMapping.getOrDefault(stack.aux(), 0);
			} else {
				modernId = translation.modernId;
			}

			return new ModernItemStack(modernId, stack.count(), DataComponentPatch.EMPTY);
		}
	}

	private static ItemTranslation getTranslation(final BetaItemStack stack) {
		final NbtTag tag = BrodernProxy.getBetaToModernItems().get(String.valueOf(stack.item().id()));
		if (tag != null && tag.isIntTag()) {
			return new ItemTranslation(tag.asIntTag().intValue(), new Int2IntArrayMap());
		} else if (tag != null && tag.isCompoundTag()) {
			final Int2IntArrayMap auxMapping = new Int2IntArrayMap();
			final CompoundTag mapping = tag.asCompoundTag();
			mapping.forEach(entry -> auxMapping.put(Integer.parseInt(entry.getKey()), entry.getValue().asIntTag().intValue()));
			return new ItemTranslation(-1, auxMapping);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private record ItemTranslation(int modernId, Int2IntArrayMap auxMapping) {
	}
}
