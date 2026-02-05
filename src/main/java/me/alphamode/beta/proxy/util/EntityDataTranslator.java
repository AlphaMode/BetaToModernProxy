package me.alphamode.beta.proxy.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CSetEntityDataPacket;
import me.alphamode.beta.proxy.util.data.beta.BetaSynchedEntityData;
import me.alphamode.beta.proxy.util.data.modern.ModernEntityTypes;
import me.alphamode.beta.proxy.util.data.modern.entity.EntityDataSerializers;
import me.alphamode.beta.proxy.util.data.modern.entity.ModernSynchedEntityData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityDataTranslator {
	private static final Int2ObjectMap<DataTranslator<?>> TRANSLATORS = new Int2ObjectOpenHashMap<>();
	private static final Object2ObjectMap<EntityDataAccessor<?>, DataTranslator<?>> ENTITY_TRANSLATORS = new Object2ObjectOpenHashMap<>();
	public static final DataAccessor<Byte> SHARED_FLAG = new DataAccessor<>(0, BetaSynchedEntityData.DataType.BYTE);
	public static final int ON_FIRE_FLAG = 0;
	public static final int SNEAKING_FLAG = 1;
	public static final int RIDING_FLAG = 2;

	public static boolean getSharedFlag(final int flag, final byte data) {
		return (data & 1 << flag) != 0;
	}

	public static byte setSharedFlag(final int flag, final boolean value, final byte currentValue) {
		if (value) {
			return (byte) (currentValue | 1 << flag);
		} else {
			return (byte) (currentValue & ~(1 << flag));
		}
	}

	public static void translate(final ClientConnection connection, final int entityId, final ModernEntityTypes type, final List<BetaSynchedEntityData.DataItem<?>> packedItem) {
		List<ModernSynchedEntityData.DataValue<?>> newValues = new ArrayList<>();
		for (final BetaSynchedEntityData.DataItem item : packedItem) {
			final int id = item.getId();
			TRANSLATORS.get(id).translate(connection, item, newValues::add);
		}
		connection.send(new S2CSetEntityDataPacket(entityId, newValues));
	}

	public static <T> void registerTranslation(DataAccessor<T> id, DataTranslator<T> translator) {
		TRANSLATORS.put(id.id(), translator);
	}

	public static <T> void registerEntityTranslation(final ModernEntityTypes type, DataAccessor<T> id, DataTranslator<T> translator) {
		ENTITY_TRANSLATORS.put(new EntityDataAccessor<>(type, id), translator);
	}

	public interface DataTranslator<T> {
		void translate(final ClientConnection connection, final BetaSynchedEntityData.DataItem<T> item, final Consumer<ModernSynchedEntityData.DataValue<?>> output);
	}

	public record EntityDataAccessor<T>(ModernEntityTypes type, DataAccessor<T> accessor) {
	}

	public record DataAccessor<T>(int id, BetaSynchedEntityData.DataType type) {
	}

	static {
		registerTranslation(SHARED_FLAG, (connection, item, output) -> {
			final byte flags = item.getData();
			boolean onFire = getSharedFlag(ON_FIRE_FLAG, flags);
			boolean sneaking = getSharedFlag(SNEAKING_FLAG, flags);
			// Riding isn't a shared flag anymore
			boolean riding = getSharedFlag(RIDING_FLAG, flags);

			byte modernFlag = 0;
			modernFlag = setSharedFlag(ON_FIRE_FLAG, onFire, modernFlag);
			modernFlag = setSharedFlag(SNEAKING_FLAG, sneaking, modernFlag);

			output.accept(new ModernSynchedEntityData.DataValue<>((byte) SHARED_FLAG.id(), EntityDataSerializers.BYTE, modernFlag));
		});
	}
}
