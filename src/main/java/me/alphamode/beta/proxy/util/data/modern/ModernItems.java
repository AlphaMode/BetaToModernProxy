package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.data.Item;
import net.lenni0451.mcstructs.core.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ModernItems {
	private static final Map<Identifier, Item> REGISTRY = new HashMap<>();

	public static final Item AIR = registerItem(Identifier.defaultNamespace("air"), new Item());
	public static final Item STONE = registerItem(Identifier.defaultNamespace("stone"), new Item());
	public static final Item GRANITE = registerItem(Identifier.defaultNamespace("granite"), new Item());
	public static final Item POLISHED_GRANITE = registerItem(Identifier.defaultNamespace("polished_granite"), new Item());
	public static final Item DIORITE = registerItem(Identifier.defaultNamespace("diorite"), new Item());
	// TODO: rest of items

	private static Item registerItem(final Identifier id, final Item item) {
		REGISTRY.put(id, item);
		return item;
	}

	public static int getRawId(final Item item) {
		int index = 0;
		for (final var entry : REGISTRY.entrySet()) {
			if (entry.getValue() == item) {
				return index;
			}

			index++;
		}

		return -1;
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
