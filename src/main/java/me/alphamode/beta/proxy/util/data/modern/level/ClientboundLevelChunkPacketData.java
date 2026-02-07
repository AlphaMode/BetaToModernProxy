package me.alphamode.beta.proxy.util.data.modern.level;

import io.netty.buffer.Unpooled;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.translators.ChunkTranslator;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ClientboundLevelChunkPacketData {
	private static final StreamCodec<ByteStream, Map<Heightmap.Types, long[]>> HEIGHTMAPS_STREAM_CODEC = ModernStreamCodecs.map(
			_ -> new EnumMap<>(Heightmap.Types.class),
			Heightmap.Types.STREAM_CODEC,
			ModernStreamCodecs.LONG_ARRAY
	);
	public static final StreamCodec<ByteStream, ClientboundLevelChunkPacketData> CODEC = StreamCodec.ofMember(ClientboundLevelChunkPacketData::write, ClientboundLevelChunkPacketData::new);

	private final Map<Heightmap.Types, long[]> heightmaps;
	private final byte[] buffer;
	private final List<BlockEntityInfo> blockEntitiesData;

	public ClientboundLevelChunkPacketData(ChunkTranslator.ModernChunk chunk) {
		this.heightmaps = chunk.heightmaps();
		this.buffer = new byte[calculateChunkSize(chunk)];
		extractChunkData(this.getWriteBuffer(), chunk);
		this.blockEntitiesData = List.of();
	}

	public ClientboundLevelChunkPacketData(
			final Map<Heightmap.Types, long[]> heightmaps,
			final byte[] buffer,
			final List<BlockEntityInfo> blockEntitiesData
	) {
		this.heightmaps = heightmaps;
		this.buffer = buffer;
		this.blockEntitiesData = blockEntitiesData;
	}

	public ClientboundLevelChunkPacketData(final ByteStream buf) {
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

	public void write(final ByteStream buf) {
		HEIGHTMAPS_STREAM_CODEC.encode(buf, this.heightmaps);
		ModernStreamCodecs.VAR_INT.encode(buf, this.buffer.length);
		buf.writeBytes(this.buffer);
		BlockEntityInfo.LIST_STREAM_CODEC.encode(buf, this.blockEntitiesData);
	}

	private static int calculateChunkSize(final ChunkTranslator.ModernChunk chunk) {
		int total = 0;

		for (ChunkTranslator.ModernChunkSection section : chunk.sections()) {
			total += section.getSerializedSize();
		}

		return total;
	}

	private ByteStream getWriteBuffer() {
		final ByteStream buffer = (ByteStream) Unpooled.wrappedBuffer(this.buffer);
		buffer.writerIndex(0);
		return buffer;
	}

	public static void extractChunkData(final ByteStream buffer, final ChunkTranslator.ModernChunk chunk) {
		for (final ChunkTranslator.ModernChunkSection section : chunk.sections()) {
			section.write(buffer);
		}

		if (buffer.writerIndex() != buffer.capacity()) {
			throw new IllegalStateException("Didn't fill chunk buffer: expected " + buffer.capacity() + " bytes, got " + buffer.writerIndex());
		}
	}

	public ByteStream getReadBuffer() {
		return (ByteStream) Unpooled.wrappedBuffer(this.buffer);
	}

	public Map<Heightmap.Types, long[]> getHeightmaps() {
		return this.heightmaps;
	}

	public static class BlockEntityInfo {
		public static final StreamCodec<ByteStream, ClientboundLevelChunkPacketData.BlockEntityInfo> STREAM_CODEC = StreamCodec.ofMember(ClientboundLevelChunkPacketData.BlockEntityInfo::write, ClientboundLevelChunkPacketData.BlockEntityInfo::new);
		public static final StreamCodec<ByteStream, List<ClientboundLevelChunkPacketData.BlockEntityInfo>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ModernStreamCodecs.list());
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

		private BlockEntityInfo(final ByteStream buf) {
			this.packedXZ = buf.readByte();
			this.y = buf.readShort();
			// TODO: this.type = ByteStreamCodecs.registry(Registries.BLOCK_ENTITY_TYPE).decode(buf);
			this.tag = ModernStreamCodecs.TAG.decode(buf).asCompoundTag();
		}

		private void write(final ByteStream buf) {
			buf.writeByte((byte) this.packedXZ);
			buf.writeShort((short) this.y);
			// ByteStreamCodecs.registry(Registries.BLOCK_ENTITY_TYPE).encode(buf, this.type);
			ModernStreamCodecs.TAG.encode(buf, this.tag);
		}
	}
}
