package me.alphamode.beta.proxy.data.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.io.IOException;
import java.nio.file.Path;

public class BetaToModernBlocks {
	public void translate() {
		registerTranslation(0, single(Blocks.AIR));
		registerTranslation(BetaTiles.STONE, single(Blocks.STONE));
		registerTranslation(BetaTiles.GRASS, single(Blocks.GRASS_BLOCK));
		registerTranslation(BetaTiles.DIRT, single(Blocks.DIRT));
		registerTranslation(BetaTiles.COBBLESTONE, single(Blocks.COBBLESTONE));
		registerTranslation(BetaTiles.PLANKS, single(Blocks.OAK_PLANKS));
		registerTranslation(BetaTiles.LEAVES, single(Blocks.OAK_LEAVES));
		registerTranslation(BetaTiles.SAPLING, sapling());
		registerTranslation(BetaTiles.BEDROCK, single(Blocks.BEDROCK));
		registerTranslation(BetaTiles.FLOWING_WATER, fluid(Fluids.WATER.defaultFluidState())); // TODO
		registerTranslation(BetaTiles.STILL_WATER, fluid(Fluids.WATER.defaultFluidState()));
		registerTranslation(BetaTiles.FLOWING_LAVA, fluid(Fluids.LAVA.defaultFluidState())); // TODO
		registerTranslation(BetaTiles.STILL_LAVA, fluid(Fluids.LAVA.defaultFluidState()));
		registerTranslation(BetaTiles.SAND, single(Blocks.SAND));
		registerTranslation(BetaTiles.GRAVEL, single(Blocks.GRAVEL));
		registerTranslation(BetaTiles.GOLDEN_ORE, single(Blocks.GOLD_ORE));
		registerTranslation(BetaTiles.IRON_ORE, single(Blocks.IRON_ORE));
		registerTranslation(BetaTiles.COAL_ORE, single(Blocks.COAL_ORE));
		registerTranslation(BetaTiles.LOG, log());
		registerTranslation(BetaTiles.SPONGE, single(Blocks.SPONGE));
		registerTranslation(BetaTiles.GLASS, single(Blocks.GLASS));
		registerTranslation(BetaTiles.LAPIS_ORE, single(Blocks.LAPIS_ORE));
		registerTranslation(BetaTiles.LAPIS_BLOCK, single(Blocks.LAPIS_BLOCK));
		registerTranslation(BetaTiles.DISPENSER, single(Blocks.DISPENSER));
		registerTranslation(BetaTiles.SANDSTONE, single(Blocks.SANDSTONE));
		registerTranslation(BetaTiles.NOTEBLOCK, single(Blocks.NOTE_BLOCK));
		registerTranslation(BetaTiles.BED, single(Blocks.RED_BED));
		registerTranslation(BetaTiles.POWERED_RAIL, single(Blocks.POWERED_RAIL));
		registerTranslation(BetaTiles.DETECTOR_RAIL, single(Blocks.DETECTOR_RAIL));
		registerTranslation(BetaTiles.STICKY_PISTON, single(Blocks.STICKY_PISTON));
		registerTranslation(BetaTiles.WEB, single(Blocks.COBWEB));
		registerTranslation(BetaTiles.TALL_GRASS, single(Blocks.TALL_GRASS));
		registerTranslation(BetaTiles.DEAD_BUSH, single(Blocks.DEAD_BUSH));
		registerTranslation(BetaTiles.PISTON, single(Blocks.PISTON));
		registerTranslation(BetaTiles.PISTON_EXTENDED, single(Blocks.PISTON_HEAD));
		registerTranslation(BetaTiles.CLOTH, single(Blocks.WHITE_WOOL));
		registerTranslation(BetaTiles.MOVING_PISTON, single(Blocks.MOVING_PISTON));
		registerTranslation(BetaTiles.YELLOW_PLANT, single(Blocks.DANDELION));
		registerTranslation(BetaTiles.POPPY, single(Blocks.POPPY));
		registerTranslation(BetaTiles.BROWN_MUSHROOM, single(Blocks.BROWN_MUSHROOM));
		registerTranslation(BetaTiles.RED_MUSHROOM, single(Blocks.RED_MUSHROOM));
		registerTranslation(BetaTiles.GOLD_BLOCK, single(Blocks.GOLD_BLOCK));
		registerTranslation(BetaTiles.IRON_BLOCK, single(Blocks.IRON_BLOCK));
		registerTranslation(BetaTiles.DOUBLE_SLAB, single(Blocks.OAK_PLANKS));
		registerTranslation(BetaTiles.HALF_SLAB, single(Blocks.OAK_SLAB));
		registerTranslation(BetaTiles.BRICK, single(Blocks.BRICKS));
		registerTranslation(BetaTiles.TNT, single(Blocks.TNT));
		registerTranslation(BetaTiles.BOOKSHELF, single(Blocks.BOOKSHELF));
		registerTranslation(BetaTiles.MOSSY_COBBLESTONE, single(Blocks.MOSSY_COBBLESTONE));
		registerTranslation(BetaTiles.OBSIDIAN, single(Blocks.OBSIDIAN));
		registerTranslation(BetaTiles.TORCH, single(Blocks.TORCH));
		registerTranslation(BetaTiles.FIRE, single(Blocks.FIRE));
		registerTranslation(BetaTiles.MOB_SPAWNER, single(Blocks.SPAWNER));
		registerTranslation(BetaTiles.OAK_STAIRS, single(Blocks.OAK_STAIRS));
		registerTranslation(BetaTiles.CHEST, single(Blocks.CHEST));
		registerTranslation(BetaTiles.REDSTONE_DUST, single(Blocks.REDSTONE_WIRE));
		registerTranslation(BetaTiles.DIAMOND_ORE, single(Blocks.DIAMOND_ORE));
		registerTranslation(BetaTiles.DIAMOND_BLOCK, single(Blocks.DIAMOND_BLOCK));
		registerTranslation(BetaTiles.CRAFTING_TABLE, single(Blocks.CRAFTING_TABLE));
		registerTranslation(BetaTiles.CROPS, single(Blocks.WHEAT));
		registerTranslation(BetaTiles.TILLED_FARMLAND, single(Blocks.FARMLAND));
		registerTranslation(BetaTiles.FURNACE, single(Blocks.FURNACE));
		registerTranslation(BetaTiles.FURNACE_ACTIVE, single(Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.LIT, true)));
		registerTranslation(BetaTiles.SIGN, single(Blocks.OAK_SIGN));
		registerTranslation(BetaTiles.OAK_DOOR, single(Blocks.OAK_DOOR));
		registerTranslation(BetaTiles.LADDER, single(Blocks.LADDER));
		registerTranslation(BetaTiles.RAIL, single(Blocks.RAIL));
		registerTranslation(BetaTiles.COBBLESTONE_STAIRS, single(Blocks.COBBLESTONE_STAIRS));
		registerTranslation(BetaTiles.WALL_SIGN, single(Blocks.OAK_WALL_SIGN));
		registerTranslation(BetaTiles.LEVER, single(Blocks.LEVER));
		registerTranslation(BetaTiles.STONE_PRESSURE_PLATE, single(Blocks.STONE_PRESSURE_PLATE));
		registerTranslation(BetaTiles.IRON_DOOR, single(Blocks.IRON_DOOR));
		registerTranslation(BetaTiles.OAK_PRESSURE_PLATE, single(Blocks.OAK_PRESSURE_PLATE));
		registerTranslation(BetaTiles.REDSTONE_ORE, single(Blocks.REDSTONE_ORE));
		registerTranslation(BetaTiles.REDSTONE_ORE_ACTIVE, single(Blocks.REDSTONE_ORE));
		registerTranslation(BetaTiles.REDSTONE_TORCH, single(Blocks.REDSTONE_TORCH));
		registerTranslation(BetaTiles.REDSTONE_TORCH_ACTIVE, single(Blocks.REDSTONE_TORCH));
		registerTranslation(BetaTiles.BUTTON, single(Blocks.STONE_BUTTON));
		registerTranslation(BetaTiles.SNOW, single(Blocks.SNOW));
		registerTranslation(BetaTiles.ICE, single(Blocks.ICE));
		registerTranslation(BetaTiles.SNOW_BLOCK, single(Blocks.SNOW_BLOCK));
		registerTranslation(BetaTiles.CACTUS, single(Blocks.CACTUS));
		registerTranslation(BetaTiles.CLAY, single(Blocks.CLAY));
		registerTranslation(BetaTiles.REED, single(Blocks.SUGAR_CANE));
		registerTranslation(BetaTiles.JUKEBOX, single(Blocks.JUKEBOX));
		registerTranslation(BetaTiles.FENCE, single(Blocks.OAK_FENCE));
		registerTranslation(BetaTiles.PUMPKIN, single(Blocks.PUMPKIN));
		registerTranslation(BetaTiles.NETHERRACK, single(Blocks.NETHERRACK));
		registerTranslation(BetaTiles.SOUL_SAND, single(Blocks.SOUL_SAND));
		registerTranslation(BetaTiles.GLOWSTONE, single(Blocks.GLOWSTONE));
		registerTranslation(BetaTiles.PORTAL, single(Blocks.NETHER_PORTAL));
		registerTranslation(BetaTiles.LANTERN, single(Blocks.JACK_O_LANTERN));
		registerTranslation(BetaTiles.CAKE, single(Blocks.CAKE));
		registerTranslation(BetaTiles.REDSTONE_REPEATER, single(Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.POWERED, false)));
		registerTranslation(BetaTiles.REDSTONE_REPEATER_ACTIVE, single(Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.POWERED, true)));
		registerTranslation(BetaTiles.LOCKED_CHEST, single(Blocks.CHEST));
		registerTranslation(BetaTiles.TRAPDOOR, single(Blocks.OAK_TRAPDOOR));
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
		return data -> Blocks.OAK_LOG.defaultBlockState();
	}

	public DataToBlockFactory single(final Block block) {
		final BlockState defaultState = block.defaultBlockState();
		return _ -> defaultState;
	}

	public DataToBlockFactory fluid(final FluidState state) {
		return data -> {
			if (data == 0) {
				return state.createLegacyBlock();
			}

			return null;
		};
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
