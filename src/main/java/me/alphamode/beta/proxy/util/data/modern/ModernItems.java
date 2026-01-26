package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.data.Item;
import net.lenni0451.mcstructs.core.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ModernItems {
	private static final Map<Identifier, Item> REGISTRY = new HashMap<>();
	private static int LAST_ITEM_ID = 0;

	public static final Item AIR = registerItem(Identifier.defaultNamespace("air"));
	public static final Item STONE = registerItem(Identifier.defaultNamespace("stone"));
	public static final Item GRANITE = registerItem(Identifier.defaultNamespace("granite"));
	public static final Item POLISHED_GRANITE = registerItem(Identifier.defaultNamespace("polished_granite"));
	public static final Item DIORITE = registerItem(Identifier.defaultNamespace("diorite"));
	// TODO: rest of items

	private static Item registerItem(final Identifier id) {
		final Item item = new Item(LAST_ITEM_ID++);
		REGISTRY.put(id, item);
		return item;
	}

	public static int getRawId(final Item item) {
		for (final var entry : REGISTRY.entrySet()) {
			if (entry.getValue() == item) {
				return item.id();
			}
		}

		return AIR.id();
	}

	public static @Nullable Identifier getId(final Item item) {
		for (final var entry : REGISTRY.entrySet()) {
			if (entry.getValue() == item) {
				return entry.getKey();
			}
		}

		return null;
	}

	public static @Nullable Item byRawId(final int id) {
		int index = 0;
		for (final var entry : REGISTRY.entrySet()) {
			if (index == id) {
				return entry.getValue();
			}

			index++;
		}

		return null;
	}

	public static @Nullable Item byId(final Identifier id) {
		return REGISTRY.getOrDefault(id, null);
	}
}
