package me.alphamode.beta.proxy.util.translators;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CLevelChunkWithLightPacket;
import me.alphamode.beta.proxy.util.data.ChunkPos;
import me.alphamode.beta.proxy.util.data.beta.BetaNibbleArray;
import me.alphamode.beta.proxy.util.data.modern.ModernNibbleArray;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLevelChunkPacketData;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLightUpdatePacketData;
import me.alphamode.beta.proxy.util.data.modern.level.Heightmap;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;
import me.alphamode.beta.proxy.util.data.modern.level.chunk.palette.PalettedContainer;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public class ChunkTranslator {
	public static final int MODERN_MIN_Y = 0;
	public static final int MODERN_MAX_Y = 128;

	public static int getMinSectionY() {
		return MODERN_MIN_Y >> 4;
	}

	public static int getMaxSectionY() {
		return MODERN_MAX_Y >> 4;
	}

	public static int getSectionsCount() {
		return getMaxSectionY() - getMinSectionY() + 1;
	}

	public static int getSectionIndex(final int blockY) {
		return getSectionIndexFromSectionY(blockY >> 4);
	}

	public static int getSectionIndexFromSectionY(final int sectionY) {
		return sectionY - getMinSectionY();
	}

	public static int getLightSectionCount() {
		return getSectionsCount() + 2;
	}

	public static int getMinLightSection() {
		return getMinSectionY() - 1;
	}

	public static final int SECTION_SIZE = 16;
	public static final int BETA_CHUNK_Y_SIZE = 128;
	public static final int BETA_CHUNK_SECTION_SIZE = BETA_CHUNK_Y_SIZE / SECTION_SIZE;

	public static List<ChunkRegion> readBetaRegionData(ClientConnection connection, int x, int y, int z, int xs, int ys, int zs, byte[] buffer) {
		int x0 = x >> 4;
		int z0 = z >> 4;
		int x1 = x + xs - 1 >> 4;
		int z1 = z + zs - 1 >> 4;
		int size = 0;
		int y0 = y;
		int y1 = y + ys;
		if (xs != 16 && ys != 128 && zs != 16) {
			return List.of();
		}

		if (y < 0) {
			y0 = 0;
		}

		if (y1 > 128) {
			y1 = 128;
		}

		final List<ChunkRegion> regions = new ArrayList<>(1);
		for (int chunkX = x0; chunkX <= x1; chunkX++) {
			int minBlockX = x - chunkX * 16;
			int maxBlockX = x + xs - chunkX * 16;
			if (minBlockX < 0) {
				minBlockX = 0;
			}

			if (maxBlockX > 16) {
				maxBlockX = 16;
			}

			for (int chunkZ = z0; chunkZ <= z1; chunkZ++) {
				int minBlockZ = z - chunkZ * 16;
				int maxBlockZ = z + zs - chunkZ * 16;
				if (minBlockZ < 0) {
					minBlockZ = 0;
				}

				if (maxBlockZ > 16) {
					maxBlockZ = 16;
				}

				final BetaChunk chunk = BetaChunk.temp();
				size = setBlocksAndData(chunk, buffer, minBlockX, y0, minBlockZ, maxBlockX, y1, maxBlockZ, size);
				translateAndSend(connection, chunkX, chunkZ, chunk);
			}
		}

		return regions;
	}

	public static void translateAndSend(final ClientConnection connection, final int x, final int z, final BetaChunk betaChunk) {
		final ModernChunk chunk = translate(betaChunk);

		final BitSet skyYMask = new BitSet();
		final BitSet blockYMask = new BitSet();
		final BitSet emptySkyYMask = new BitSet();
		final BitSet emptyBlockYMask = new BitSet();
		final List<byte[]> skyUpdates = Lists.newArrayList();
		final List<byte[]> blockUpdates = Lists.newArrayList();

		final ModernNibbleArray[] skyLight = fromBetaToModern(betaChunk.skyLight());
		final ModernNibbleArray[] blockLight = fromBetaToModern(betaChunk.blockLight());
		for (int sectionIndex = 0; sectionIndex < getLightSectionCount(); sectionIndex++) {
			// Sky Light
			prepareSectionData(skyLight[sectionIndex], sectionIndex, skyYMask, emptySkyYMask, skyUpdates);

			// Block Light
			prepareSectionData(blockLight[sectionIndex], sectionIndex, blockYMask, emptyBlockYMask, blockUpdates);
		}

		connection.send(new S2CLevelChunkWithLightPacket(
				new ChunkPos(x, z),
				new ClientboundLevelChunkPacketData(chunk),
				new ClientboundLightUpdatePacketData(
						skyYMask, blockYMask, emptySkyYMask, emptyBlockYMask,
						skyUpdates, blockUpdates
				)));
	}

	private static ModernNibbleArray[] fromBetaToModern(final BetaNibbleArray nibbleArray) {
		final ModernNibbleArray[] modernArray = new ModernNibbleArray[getLightSectionCount()];
		for (int i = 0; i < modernArray.length; i++) {
			modernArray[i] = new ModernNibbleArray(new byte[ModernNibbleArray.SIZE]);
		}

		for (int y = 0; y < BETA_CHUNK_Y_SIZE; y++) {
			final int sectionY = /*getMinLightSection() + */getSectionIndex(y);
			// TODO/FIX
			final ModernNibbleArray modernNibbleArray = modernArray[sectionY];
			for (int x = 0; x < SECTION_SIZE; x++) {
				for (int z = 0; z < SECTION_SIZE; z++) {
					modernNibbleArray.set(x, y & 15, z, nibbleArray.get(x, y, z));
				}
			}
		}

		return modernArray;
	}

	private static void prepareSectionData(
			final ModernNibbleArray data,
			final int sectionIndex,
			final BitSet mask,
			final BitSet emptyMask,
			final List<byte[]> updates
	) {
		if (data.isEmpty()) {
			emptyMask.set(sectionIndex);
		} else {
			mask.set(sectionIndex);
			updates.add(data.data().clone());
		}
	}

	public static ModernChunk translate(BetaChunk chunk) {
		final BlockTranslator translator = BrodernProxy.getBlockTranslator();
		final ModernChunkSection[] sections = new ModernChunkSection[BETA_CHUNK_SECTION_SIZE];
		for (int sectionY = 0; sectionY < BETA_CHUNK_SECTION_SIZE; sectionY++) {
			PalettedContainer<BlockState> states = PalettedContainer.blockStates();
			int nonEmptyBlockCount = 0;
			for (int x = 0; x < SECTION_SIZE; x++) {
				for (int y = 0; y < SECTION_SIZE; y++) {
					for (int z = 0; z < SECTION_SIZE; z++) {
						final int blockY = sectionY * 16 + y;
						final BlockState state = translator.translate(chunk.getTile(x, blockY, z), chunk.getData(x, blockY, z));
						states.set(x, y, z, state);
						if (!state.isAir()) {
							nonEmptyBlockCount++;
						}
					}
				}
			}

			sections[sectionY] = new ModernChunkSection(nonEmptyBlockCount, states, PalettedContainer.biomes());
		}

		// TODO: heightmaps
		return new ModernChunk(sections, Map.of());
	}

	public record ChunkRegion(BetaChunk chunk, int x, int z, int minX, int minY, int minZ, int maxX, int maxY,
							  int maxZ) {
		public boolean inBounds(int x, int y, int z) {
			return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
		}
	}

	public record ModernChunk(ModernChunkSection[] sections, Map<Heightmap.Types, long[]> heightmaps) {
	}

	public record ModernChunkSection(int nonEmptyBlockCount, PalettedContainer<BlockState> blockStates,
									 PalettedContainer<Object> biomes) {
		public void setBlockState(int x, int y, int z, BlockState state) {
			blockStates.set(x, y, z, state);
		}

		public void write(final ByteBuf buffer) {
			buffer.writeShort(this.nonEmptyBlockCount);
			this.blockStates.write(buffer);
			this.biomes.write(buffer);
		}

		public int getSerializedSize() {
			return 2 + this.blockStates.getSerializedSize() + this.biomes.getSerializedSize();
		}
	}

	public record BetaChunk(byte[] blocks, BetaNibbleArray data, BetaNibbleArray blockLight, BetaNibbleArray skyLight) {
		public static final int DATA_SIZE = 32768;

		public static BetaChunk temp() {
			return new BetaChunk(
					new byte[DATA_SIZE],
					new BetaNibbleArray(DATA_SIZE),
					new BetaNibbleArray(DATA_SIZE),
					new BetaNibbleArray(DATA_SIZE)
			);
		}

		public int getTile(int x, int y, int z) {
			return this.blocks[x << 11 | z << 7 | y] & 0xFF;
		}

		public int getData(int x, int y, int z) {
			return this.data.get(x, y, z);
		}

		public int getBlockBrightness(int x, int y, int z) {
			return this.blockLight.get(x, y, z);
		}

		public int getSkyBrightness(int x, int y, int z) {
			return this.skyLight.get(x, y, z);
		}
	}

	public static int setBlocksAndData(BetaChunk chunk, byte[] chunkData, int x0, int y0, int z0, int x1, int y1, int z1, int size) {
		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				int packedPos = x << 11 | z << 7 | y0;
				int offset = y1 - y0;
				System.arraycopy(chunkData, size, chunk.blocks, packedPos, offset);
				size += offset;
			}
		}

//        this.recalcHeightmapOnly();

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int packedPos = (x << 11 | z << 7 | y0) >> 1;
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.data.data(), packedPos, offset);
				size += offset;
			}
		}

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int packedPos = (x << 11 | z << 7 | y0) >> 1;
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.blockLight.data(), packedPos, offset);
				size += offset;
			}
		}

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int packedPos = (x << 11 | z << 7 | y0) >> 1;
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.skyLight.data(), packedPos, offset);
				size += offset;
			}
		}

		return size;
	}
}
