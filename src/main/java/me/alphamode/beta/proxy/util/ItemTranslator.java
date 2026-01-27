package me.alphamode.beta.proxy.util;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import me.alphamode.beta.proxy.util.data.modern.components.DataItemComponents;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Optional;

public final class ItemTranslator {
	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return ModernItemStack.EMPTY;
		} else {
			final ItemTranslation translation = getTranslation(stack);

			DataComponentPatch patch = DataComponentPatch.EMPTY;
			if (isDamagable(stack.item().id())) {
				patch = new DataComponentPatch(Reference2ObjectMaps.singleton(DataItemComponents.DAMAGE, Optional.of(stack.aux())));
			}

			int modernId;
			if (!translation.auxMapping.isEmpty()) {
				modernId = translation.auxMapping.getOrDefault(stack.aux(), 0);
			} else {
				modernId = translation.modernId;
			}

			return new ModernItemStack(modernId, stack.count(), patch);
		}
	}

	private static boolean isDamagable(final int id) {
		return (id >= 256 && id <= 258) // Iron Shovel, Pickaxe, and Axe
				|| (id == 259) // Flint And Steel
				|| (id == 261) // Bow
				|| (id == 346) // Fishing Rod
				|| (id == 359) // Shears
				|| (id >= 267 && id <= 279) // Iron Sword & Wooden/Stone Tools/Weapons
				|| (id >= 284 && id <= 286) // Golden Tools & Weapons
				|| (id >= 290 && id <= 294) // Hoes
				|| (id >= 298 && id <= 317); // Armor
	}

	private static ItemTranslation getTranslation(final BetaItemStack stack) {
		final NbtTag tag = BrodernProxy.getBetaToModernItems().get(String.valueOf(stack.item().id()));
		if (tag != null && tag.isIntTag()) {
			return new ItemTranslation(tag.asIntTag().intValue(), new Int2IntArrayMap());
		} else if (tag != null && tag.isCompoundTag()) {
			final Int2IntArrayMap auxMapping = new Int2IntArrayMap();
			final CompoundTag mapping = tag.asCompoundTag();
			mapping.forEach(entry -> auxMapping.put(Integer.parseInt(entry.getKey()), entry.getValue().asIntTag().intValue()));
			return new ItemTranslation(0, auxMapping);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private record ItemTranslation(int modernId, Int2IntArrayMap auxMapping) {
	}
}
