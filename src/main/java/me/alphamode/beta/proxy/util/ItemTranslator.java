package me.alphamode.beta.proxy.util;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponents;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class ItemTranslator {
	private static final Logger LOGGER = LogManager.getLogger(ItemTranslator.class);

	public static ModernItemStack toModernStack(final BetaItemStack stack) {
		if (stack == null) {
			return ModernItemStack.EMPTY;
		} else {
			final DataComponentPatch.Builder builder = DataComponentPatch.builder();

			int modernId = 0;
			final Either<ItemTranslation, ItemTranslations> either = getTranslation(stack);
			if (either.isLeft()) {
				final ItemTranslation translation = either.getLeft();
				modernId = translation.modernId();
				translation.apply(builder);
			} else {
				final ItemTranslations translations = either.getRight();
				final ItemTranslation translation = translations.auxMapping.getOrDefault(stack.aux(), null);
				if (translation != null) {
					modernId = translation.modernId();
					translation.apply(builder);
				}
			}

			if (isDamagable(stack.item().id())) {
				builder.set(DataComponents.DAMAGE, stack.aux());
			}

			return new ModernItemStack(modernId, stack.count(), builder.build());
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

	private static Either<ItemTranslation, ItemTranslations> getTranslation(final BetaItemStack stack) {
		final NbtTag tag = BrodernProxy.getBetaToModernItems().get(String.valueOf(stack.item().id()));
		if (tag == null) {
			throw new UnsupportedOperationException();
		}

		if (tag instanceof CompoundTag translationTag) {
			if (translationTag.contains("id")) {
				return Either.left(ItemTranslation.read(translationTag));
			} else {
				final Map<Integer, ItemTranslation> auxMapping = new HashMap<>();
				for (final var entry : translationTag) {
					auxMapping.put(Integer.parseInt(entry.getKey()), ItemTranslation.read(entry.getValue().asCompoundTag()));
				}

				return Either.right(new ItemTranslations(auxMapping));
			}
		}

		throw new UnsupportedOperationException();
	}

	private record ItemTranslation(int modernId, DataComponentPatch patch) {
		public static ItemTranslation read(final CompoundTag tag) {
			final int id = tag.getInt("id");
			return new ItemTranslation(id, DataComponentPatch.CODEC.decode(tag.getCompound("patch")));
		}

		public void apply(final DataComponentPatch.Builder builder) {
			builder.apply(this.patch);
		}
	}

	private record ItemTranslations(Map<Integer, ItemTranslation> auxMapping) {
	}
}
