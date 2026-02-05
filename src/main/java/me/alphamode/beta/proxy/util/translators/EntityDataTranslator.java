package me.alphamode.beta.proxy.util.translators;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CSetEntityDataPacket;
import me.alphamode.beta.proxy.util.data.EntityDataValue;
import me.alphamode.beta.proxy.util.data.beta.BetaSynchedEntityData;
import me.alphamode.beta.proxy.util.data.modern.ModernEntityTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityDataTranslator<FROM extends EntityDataValue<?>, TO extends EntityDataValue<?>> {
	private final Int2ObjectMap<DataTranslator<?, FROM, TO>> translators = new Int2ObjectOpenHashMap<>();
	private final Object2ObjectMap<EntityDataAccessor<?>, DataTranslator<?, FROM, TO>> entityTranslators = new Object2ObjectOpenHashMap<>();

	public void translate(final ClientConnection connection, final int entityId, final ModernEntityTypes type, final List<FROM> packedItem) {
		List<EntityDataValue<?>> newValues = new ArrayList<>();
		for (final FROM item : packedItem) {
			final int id = item.id();
			translators.get(id).translate(connection, item, newValues::add);
		}
		connection.send(new S2CSetEntityDataPacket(entityId, newValues));
	}

	public <T, FROM extends EntityDataValue<T>, TO extends EntityDataValue<?>> void registerTranslation(DataAccessor<T> id, DataTranslator<T, FROM, TO> translator) {
		translators.put(id.id(), (DataTranslator) translator);
	}

	public <T, FROM extends EntityDataValue<T>, TO extends EntityDataValue<?>> void registerEntityTranslation(final ModernEntityTypes type, DataAccessor<T> id, DataTranslator<T, FROM, TO> translator) {
		entityTranslators.put(new EntityDataAccessor<>(type, id), translator);
	}

	public interface DataTranslator<T, FROM extends EntityDataValue<T>, TO extends EntityDataValue<?>> {
		void translate(final ClientConnection connection, final FROM item, final Consumer<TO> output);
	}

	public record EntityDataAccessor<T>(ModernEntityTypes type, DataAccessor<T> accessor) {
	}

	public record DataAccessor<T>(int id, BetaSynchedEntityData.DataType type) {
	}
}
