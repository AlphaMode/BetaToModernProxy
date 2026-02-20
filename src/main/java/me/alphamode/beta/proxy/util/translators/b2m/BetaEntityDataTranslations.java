package me.alphamode.beta.proxy.util.translators.b2m;

import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.util.data.beta.entity.BetaSynchedEntityData;
import me.alphamode.beta.proxy.util.data.modern.ModernEntityTypes;
import me.alphamode.beta.proxy.util.data.modern.entity.ModernEntityData;
import me.alphamode.beta.proxy.util.data.modern.entity.ModernSynchedEntityData;
import me.alphamode.beta.proxy.util.translators.EntityDataTranslator;

import java.util.function.Consumer;

public final class BetaEntityDataTranslations {
	public static final EntityDataTranslator.DataAccessor<Byte, BetaSynchedEntityData.DataValue<Byte>> SHARED_FLAG = new EntityDataTranslator.DataAccessor<>(0);

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

	public static final int ON_FIRE_FLAG = 0;
	public static final int SNEAKING_FLAG = 1;
	public static final int RIDING_FLAG = 2;

	private static void translateSharedFlags(final boolean isPlayer, final ClientConnection connection, final BetaSynchedEntityData.DataValue<Byte> item, Consumer<ModernSynchedEntityData.DataValue<?>> output) {
		final byte flags = item.data();
		boolean onFire = getSharedFlag(ON_FIRE_FLAG, flags);
		boolean sneaking = getSharedFlag(SNEAKING_FLAG, flags);
		// Riding isn't a shared flag anymore
		boolean riding = getSharedFlag(RIDING_FLAG, flags);

		byte modernFlag = 0;
		modernFlag = setSharedFlag(ON_FIRE_FLAG, onFire, modernFlag);
		modernFlag = setSharedFlag(SNEAKING_FLAG, sneaking, modernFlag);

		output.accept(new ModernSynchedEntityData.DataValue<>((byte) SHARED_FLAG.id(), ModernEntityData.BYTE, modernFlag));

		if (isPlayer) {

		}
	}

	public static void register(EntityDataTranslator<BetaSynchedEntityData.DataValue<?>, ModernSynchedEntityData.DataValue<?>> translator) {
		translator.registerTranslation(SHARED_FLAG, (connection, item, output) -> {
			translateSharedFlags(false, connection, item, output);
		});
		translator.registerEntityTranslation(ModernEntityTypes.PLAYER, SHARED_FLAG, (connection, item, output) -> {
			translateSharedFlags(true, connection, item, output);
		});
	}
}
