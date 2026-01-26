package me.alphamode.beta.proxy.util;

import me.alphamode.beta.proxy.util.data.beta.BetaNibbleArray;

import java.util.ArrayList;
import java.util.List;

public class ChunkTranslator {
	public static List<ChunkRegion> readBetaRegionData(int x, int y, int z, int xs, int ys, int zs, byte[] buffer) {
		int x0 = x >> 4;
		int z0 = z >> 4;
		int x1 = x + xs - 1 >> 4;
		int z1 = z + zs - 1 >> 4;
		int size = 0;
		int y0 = Math.max(0, y);
		int y1 = Math.min(128, y + ys);

		final List<ChunkRegion> regions = new ArrayList<>(1);
		for (int chunkX = x0; chunkX <= x1; chunkX++) {
			final int minBlockX = Math.max(0, x - chunkX * 16);
			final int maxBlockX = Math.min(16, x + xs - chunkX * 16);
			for (int chunkZ = z0; chunkZ <= z1; chunkZ++) {
				final int minBlockZ = Math.max(0, z - chunkZ * 16);
				final int maxBlockZ = Math.min(16, z + zs - chunkZ * 16);

				final BetaChunk chunk = BetaChunk.temp();
				size = setBlocksAndData(chunk, buffer, minBlockX, y0, minBlockZ, maxBlockX, y1, maxBlockZ, size);
				regions.add(new ChunkRegion(chunk, chunkX, chunkZ, minBlockX, y0, minBlockZ, maxBlockX, y1, maxBlockZ));
			}
		}

		return regions;
	}

	public record ChunkRegion(BetaChunk chunk, int x, int z, int minX, int minY, int minZ, int maxX, int maxY,
							  int maxZ) {
		public boolean inBounds(int x, int y, int z) {
			return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
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
				final int offset = y1 - y0;
				System.arraycopy(chunkData, size, chunk.blocks, x << 11 | z << 7 | y0, offset);
				size += offset;
			}
		}

//        this.recalcHeightmapOnly();

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.data.data(), (x << 11 | z << 7 | y0) >> 1, offset);
				size += offset;
			}
		}

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.blockLight.data(), (x << 11 | z << 7 | y0) >> 1, offset);
				size += offset;
			}
		}

		for (int x = x0; x < x1; x++) {
			for (int z = z0; z < z1; z++) {
				final int offset = (y1 - y0) / 2;
				System.arraycopy(chunkData, size, chunk.skyLight.data(), (x << 11 | z << 7 | y0) >> 1, offset);
				size += offset;
			}
		}

		return size;
	}
}
