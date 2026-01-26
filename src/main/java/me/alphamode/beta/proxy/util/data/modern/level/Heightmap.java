package me.alphamode.beta.proxy.util.data.modern.level;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;
import net.lenni0451.mcstructs.converter.codec.Codec;

import java.util.function.IntFunction;

public class Heightmap {
	public enum Types implements StringRepresentable {
		WORLD_SURFACE_WG(0, "WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN),
		WORLD_SURFACE(1, "WORLD_SURFACE", Heightmap.Usage.CLIENT),
		OCEAN_FLOOR_WG(2, "OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN),
		OCEAN_FLOOR(3, "OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD),
		MOTION_BLOCKING(4, "MOTION_BLOCKING", Heightmap.Usage.CLIENT),
		MOTION_BLOCKING_NO_LEAVES(5, "MOTION_BLOCKING_NO_LEAVES", Heightmap.Usage.CLIENT);

		public static final Codec<Types> CODEC = StringRepresentable.fromEnum(Heightmap.Types::values);
		private static final IntFunction<Types> BY_ID = ByIdMap.continuous(t -> t.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final StreamCodec<ByteBuf, Types> STREAM_CODEC = ModernStreamCodecs.idMapper(BY_ID, t -> t.id);
		private final int id;
		private final String serializationKey;
		private final Heightmap.Usage usage;

		Types(int id, String serializationKey, Heightmap.Usage usage) {
			this.id = id;
			this.serializationKey = serializationKey;
			this.usage = usage;
		}

		public String getSerializationKey() {
			return this.serializationKey;
		}

		public boolean sendToClient() {
			return this.usage == Heightmap.Usage.CLIENT;
		}

		public boolean keepAfterWorldgen() {
			return this.usage != Heightmap.Usage.WORLDGEN;
		}

		@Override
		public String getSerializedName() {
			return this.serializationKey;
		}
	}

	public enum Usage {
		WORLDGEN,
		LIVE_WORLD,
		CLIENT
	}
}
