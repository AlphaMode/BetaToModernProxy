package me.alphamode.beta.proxy.util.data.modern.level;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ClientboundLevelChunkPacketData {
	private static final StreamCodec<ByteBuf, Map<Heightmap.Types, long[]>> HEIGHTMAPS_STREAM_CODEC = ModernStreamCodecs.map(
			_ -> new EnumMap<>(Heightmap.Types.class),
			Heightmap.Types.STREAM_CODEC,
			ModernStreamCodecs.LONG_ARRAY
	);

	private final Map<Heightmap.Types, long[]> heightmaps;
	private final byte[] buffer;
	private final List<BlockEntityInfo> blockEntitiesData;

	public ClientboundLevelChunkPacketData(final ByteBuf buf) {
		this.heightmaps = HEIGHTMAPS_STREAM_CODEC.decode(buf);

		final int size = ModernStreamCodecs.VAR_INT.decode(buf);
		if (size > 2097152) {
			throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
		} else {
			this.buffer = new byte[size];
			buf.readBytes(this.buffer);
			this.blockEntitiesData = BlockEntityInfo.LIST_STREAM_CODEC.decode(buf);
		}
	}

	public void write(final ByteBuf buf) {
		HEIGHTMAPS_STREAM_CODEC.encode(buf, this.heightmaps);
		ModernStreamCodecs.VAR_INT.encode(buf, this.buffer.length);
		buf.writeBytes(this.buffer);
		BlockEntityInfo.LIST_STREAM_CODEC.encode(buf, this.blockEntitiesData);
	}

	public ByteBuf getReadBuffer() {
		return Unpooled.wrappedBuffer(this.buffer);
	}

	public Map<Heightmap.Types, long[]> getHeightmaps() {
		return this.heightmaps;
	}

	private static class BlockEntityInfo {
		public static final StreamCodec<ByteBuf, ClientboundLevelChunkPacketData.BlockEntityInfo> STREAM_CODEC = StreamCodec.ofMember(ClientboundLevelChunkPacketData.BlockEntityInfo::write, ClientboundLevelChunkPacketData.BlockEntityInfo::new);
		public static final StreamCodec<ByteBuf, List<ClientboundLevelChunkPacketData.BlockEntityInfo>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ModernStreamCodecs.list());
		private final int packedXZ;
		private final int y;
		// TODO: private final BlockEntityType<?> type;
		private final @Nullable CompoundTag tag;

		private BlockEntityInfo(int packedXZ, int y, /*BlockEntityType<?> type,*/ @Nullable CompoundTag tag) {
			this.packedXZ = packedXZ;
			this.y = y;
			//this.type = type;
			this.tag = tag;
		}

		private BlockEntityInfo(final ByteBuf buf) {
			this.packedXZ = buf.readByte();
			this.y = buf.readShort();
			// TODO: this.type = ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE).decode(buf);
			this.tag = ModernStreamCodecs.TAG.decode(buf).asCompoundTag();
		}

		private void write(final ByteBuf buf) {
			buf.writeByte(this.packedXZ);
			buf.writeShort(this.y);
			// ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE).encode(buf, this.type);
			ModernStreamCodecs.TAG.encode(buf, this.tag);
		}
	}
}
