package me.alphamode.beta.proxy.util.translators;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.util.data.EntityDataValue;
import me.alphamode.beta.proxy.util.data.modern.ModernEntityTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityDataTranslator<FROM extends EntityDataValue<?>, TO extends EntityDataValue<?>> {
	private final Int2ObjectMap<DataTranslator<?, FROM, TO>> translators = new Int2ObjectOpenHashMap<>();
	private final Object2ObjectMap<EntityDataAccessor<?, ?>, DataTranslator<?, FROM, TO>> entityTranslators = new Object2ObjectOpenHashMap<>();

	public List<TO> translate(final ClientConnection connection, final int entityId, final ModernEntityTypes type, final List<FROM> packedItem) {
		List<TO> newValues = new ArrayList<>();
		for (final FROM item : packedItem) {
			final int id = item.id();
			translators.get(id).translate(connection, item, newValues::add);
		}

        return newValues;
	}

	public <V, F extends EntityDataValue<V>> void registerTranslation(DataAccessor<V, F> id, DataTranslator<V, F, TO> translator) {
		translators.put(id.id(), (DataTranslator) translator);
	}

	public <V, F extends EntityDataValue<V>> void registerEntityTranslation(final ModernEntityTypes type, DataAccessor<V, F> id, DataTranslator<V, F, TO> translator) {
		entityTranslators.put(new EntityDataAccessor<>(type, id), (DataTranslator) translator);
	}

	public interface DataTranslator<T, FROM extends EntityDataValue<T>, TO extends EntityDataValue<?>> {
		void translate(final ClientConnection connection, final FROM item, final Consumer<TO> output);
	}

	public record EntityDataAccessor<T, F extends EntityDataValue<T>>(ModernEntityTypes type, DataAccessor<T, F> accessor) {
	}

	public record DataAccessor<T, V extends EntityDataValue<T>>(int id) {
	}
}
