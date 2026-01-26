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
        int y0 = y;
        int y1 = y + ys;
        if (y < 0) {
            y0 = 0;
        }

        if (y1 > 128) {
            y1 = 128;
        }

        List<ChunkRegion> regions = new ArrayList<>(1);

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

                BetaChunk chunk = BetaChunk.temp();

                size = setBlocksAndData(chunk, buffer, minBlockX, y0, minBlockZ, maxBlockX, y1, maxBlockZ, size);

                regions.add(new ChunkRegion(chunk, chunkX, chunkZ, minBlockX, y0, minBlockZ, maxBlockX, y1, maxBlockZ));
            }
        }
        return regions;
    }

    public record ChunkRegion(BetaChunk chunk, int x, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        public boolean inBounds(int x, int y, int z) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        }
    }

    public record BetaChunk(byte[] blocks, BetaNibbleArray data, BetaNibbleArray blockLight, BetaNibbleArray skyLight) {
        public static final int DATA_SIZE = 32768;

        public static BetaChunk temp() {
            byte[] blocks = new byte[DATA_SIZE];
            BetaNibbleArray data = new BetaNibbleArray(DATA_SIZE);
            BetaNibbleArray blockLight = new BetaNibbleArray(DATA_SIZE);
            BetaNibbleArray skyLight = new BetaNibbleArray(DATA_SIZE);
            return new BetaChunk(blocks, data, blockLight, skyLight);
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
                int packedPos = (x << 11 | z << 7 | y0) >> 1;
                int offset = (y1 - y0) / 2;
                System.arraycopy(chunkData, size, chunk.data.data(), packedPos, offset);
                size += offset;
            }
        }

        for (int x = x0; x < x1; x++) {
            for (int z = z0; z < z1; z++) {
                int packedPos = (x << 11 | z << 7 | y0) >> 1;
                int offset = (y1 - y0) / 2;
                System.arraycopy(chunkData, size, chunk.blockLight.data(), packedPos, offset);
                size += offset;
            }
        }

        for (int x = x0; x < x1; x++) {
            for (int z = z0; z < z1; z++) {
                int packedPos = (x << 11 | z << 7 | y0) >> 1;
                int offset = (y1 - y0) / 2;
                System.arraycopy(chunkData, size, chunk.skyLight.data(), packedPos, offset);
                size += offset;
            }
        }

        return size;
    }
}
