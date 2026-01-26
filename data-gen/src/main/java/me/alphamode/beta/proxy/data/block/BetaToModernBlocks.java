package me.alphamode.beta.proxy.data.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.io.IOException;
import java.nio.file.Path;

public class BetaToModernBlocks {
    public void translate() {
        registerTranslation(0, single(Blocks.AIR));
        registerTranslation(BetaTiles.STONE, single(Blocks.STONE));
        registerTranslation(BetaTiles.DIRT, single(Blocks.DIRT));
        registerTranslation(BetaTiles.COBBLESTONE, single(Blocks.COBBLESTONE));
        registerTranslation(BetaTiles.WOOD, single(Blocks.OAK_PLANKS));
        registerTranslation(BetaTiles.SAPLING, sapling());
        registerTranslation(BetaTiles.BEDROCK, single(Blocks.BEDROCK));
        registerTranslation(BetaTiles.FLOWING_WATER, null);
        registerTranslation(BetaTiles.WATER, null);
        registerTranslation(BetaTiles.FLOWING_LAVA, null);
        registerTranslation(BetaTiles.LAVA, null);
        registerTranslation(BetaTiles.SAND, single(Blocks.SAND));
        registerTranslation(BetaTiles.GRAVEL, single(Blocks.GRAVEL));
        registerTranslation(BetaTiles.GOLD_ORE, single(Blocks.GOLD_ORE));
        registerTranslation(BetaTiles.IRON_ORE, single(Blocks.IRON_ORE));
        registerTranslation(BetaTiles.COAL_ORE, single(Blocks.COAL_ORE));
        registerTranslation(BetaTiles.LOG, log());
        registerTranslation(BetaTiles.SPONGE, null);
        registerTranslation(BetaTiles.GLASS, null);
        registerTranslation(BetaTiles.LAPIS_ORE, single(Blocks.LAPIS_ORE));
        registerTranslation(BetaTiles.LAPIS_BLOCK, single(Blocks.LAPIS_BLOCK));
        registerTranslation(BetaTiles.DISPENSER, null);
        registerTranslation(BetaTiles.SANDSTONE, null);
        registerTranslation(BetaTiles.MUSIC_BLOCK, null);
        registerTranslation(BetaTiles.BED, null);
        registerTranslation(BetaTiles.POWERED_RAIL, null);
        registerTranslation(BetaTiles.DETECTOR_RAIL, null);
        registerTranslation(BetaTiles.STICKY_PISTON, null);
        registerTranslation(BetaTiles.WEB, single(Blocks.COBWEB));
        registerTranslation(BetaTiles.PISTON, null);
        registerTranslation(BetaTiles.CLOTH, null);
        registerTranslation(BetaTiles.GOLD_BLOCK, single(Blocks.GOLD_BLOCK));
        registerTranslation(BetaTiles.IRON_BLOCK, single(Blocks.IRON_BLOCK));
        registerTranslation(BetaTiles.DOUBLE_SLAB, null);
        registerTranslation(BetaTiles.SLAB, null);
        registerTranslation(BetaTiles.BRICK, null);
        registerTranslation(BetaTiles.TNT, null);
        registerTranslation(BetaTiles.BOOKSHELF, single(Blocks.BOOKSHELF));
        registerTranslation(BetaTiles.MOSS_STONE, null);
        registerTranslation(BetaTiles.OBSIDIAN, single(Blocks.OBSIDIAN));
        registerTranslation(BetaTiles.TORCH, null);
        registerTranslation(BetaTiles.SPAWNER, null);
        registerTranslation(BetaTiles.WOOD_STAIRS, null);
        registerTranslation(BetaTiles.CHEST, null);
        registerTranslation(BetaTiles.REDSTONE, null);
        registerTranslation(BetaTiles.DIAMOND_ORE, single(Blocks.DIAMOND_ORE));
        registerTranslation(BetaTiles.DIAMOND_BLOCK, single(Blocks.DIAMOND_BLOCK));
        registerTranslation(BetaTiles.WORKBENCH, null);
        registerTranslation(BetaTiles.WHEAT, null);
        registerTranslation(BetaTiles.FARMLAND, null);
        registerTranslation(BetaTiles.FURNACE, null);
        registerTranslation(BetaTiles.LIT_FURNACE, null);
        registerTranslation(BetaTiles.SIGN, null);
        registerTranslation(BetaTiles.DOOR, null);
        registerTranslation(BetaTiles.LADDER, null);
        registerTranslation(BetaTiles.RAIL, null);
        registerTranslation(BetaTiles.COBBLESTONE_STAIRS, null);
        registerTranslation(BetaTiles.WALL_SIGN, null);
        registerTranslation(BetaTiles.LEVER, null);
        registerTranslation(BetaTiles.STONE_PRESSURE_PLATE, null);
        registerTranslation(BetaTiles.IRON_DOOR, null);
        registerTranslation(BetaTiles.WOOD_PRESSURE_PLATE, null);
        registerTranslation(BetaTiles.REDSTONE_ORE, null);
        registerTranslation(BetaTiles.LIT_REDSTONE_ORE, null);
        registerTranslation(BetaTiles.UNLIT_REDSTONE_TORCH, null);
        registerTranslation(BetaTiles.REDSTONE_TORCH, null);
        registerTranslation(BetaTiles.STONE_BUTTON, null);
        registerTranslation(BetaTiles.SNOW_LAYER, null);
        registerTranslation(BetaTiles.ICE, null);
        registerTranslation(BetaTiles.SNOW, null);
        registerTranslation(BetaTiles.CACTUS, null);
        registerTranslation(BetaTiles.CLAY, single(Blocks.CLAY));
        registerTranslation(BetaTiles.REEDS, null);
        registerTranslation(BetaTiles.JUKEBOX, null);
        registerTranslation(BetaTiles.OAK_FENCE, null);
        registerTranslation(BetaTiles.PUMPKIN, null);
        registerTranslation(BetaTiles.NETHERRACK, null);
        registerTranslation(BetaTiles.SOUL_SAND, null);
        registerTranslation(BetaTiles.GLOWSTONE, null);
        registerTranslation(BetaTiles.LIT_PUMPKIN, null);
        registerTranslation(BetaTiles.CAKE, null);
        registerTranslation(BetaTiles.REPEATER_OFF, null);
        registerTranslation(BetaTiles.REPEATER_ON, null);
        registerTranslation(BetaTiles.LOCKED_CHEST, null);
        registerTranslation(BetaTiles.TRAPDOOR, null);
    }

    public DataToBlockFactory sapling() {
        return data -> {
            int type = data & 3;
            int stage = data & 8;
            BlockState state = Blocks.OAK_SAPLING.defaultBlockState();
            if (type == 1) {
                state = Blocks.SPRUCE_SAPLING.defaultBlockState();
            } else if (type == 2) {
                state = Blocks.BIRCH_SAPLING.defaultBlockState();
            }

            if (stage == 0) {
                return state.setValue(SaplingBlock.STAGE, stage);
            } else {
                return state.setValue(SaplingBlock.STAGE, 1);
            }
        };
    }

    public DataToBlockFactory log() {
        return data -> {
            return Blocks.OAK_LOG.defaultBlockState();
        };
    }

    public DataToBlockFactory single(final Block block) {
        final BlockState defaultState = block.defaultBlockState();
        return _ -> defaultState;
    }

    public DataToBlockFactory single(final BlockState state) {
        return data -> {
            if (data == 0)
                return state;
            return null;
        };
    }

    private Int2ObjectMap<DataToBlockFactory> dataMap = new Int2ObjectOpenHashMap<>();

    public void registerTranslation(int tile, DataToBlockFactory factory) {
        if (factory == null)
            return;
        dataMap.put(tile, factory);
    }

    public void save(Path output) throws IOException {
        final IdMapper<BlockState> idMapper = Block.BLOCK_STATE_REGISTRY;
        final CompoundTag root = new CompoundTag();
        dataMap.forEach((key, factory) -> {
            CompoundTag tag = new CompoundTag();
            for (int data = 0; data < 16; data++) {
                final BlockState state = factory.toBlockState(data);
                if (state != null)
                    tag.putInt(Integer.toString(data), idMapper.getId(state));
            }
            root.put(Integer.toString(key), tag);
        });
        NbtIo.writeCompressed(root, output);
    }

    @FunctionalInterface
    public interface DataToBlockFactory {
        BlockState toBlockState(int data);
    }
}
