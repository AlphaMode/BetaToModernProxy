package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.data.Block;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class BetaBlocks {
	private static final Map<Integer, Block> REGISTRY = new HashMap<>();

	public static final Block STONE = registerBlock(1, new Block());
	public static final Block GRASS = registerBlock(2, new Block());
	public static final Block DIRT = registerBlock(3, new Block());
	public static final Block COBBLESTONE = registerBlock(4, new Block());
	public static final Block PLANKS = registerBlock(5, new Block());
	public static final Block SAPLING = registerBlock(6, new Block());
	public static final Block BEDROCK = registerBlock(7, new Block());
	public static final Block FLOWING_WATER = registerBlock(8, new Block());
	public static final Block STILL_WATER = registerBlock(9, new Block());
	public static final Block FLOWING_LAVA = registerBlock(10, new Block());
	public static final Block STILL_LAVA = registerBlock(11, new Block());
	public static final Block SAND = registerBlock(12, new Block());
	public static final Block GRAVEL = registerBlock(13, new Block());
	public static final Block GOLDEN_ORE = registerBlock(14, new Block());
	public static final Block IRON_ORE = registerBlock(15, new Block());
	public static final Block COAL_ORE = registerBlock(16, new Block());
	public static final Block WOOD = registerBlock(17, new Block());
	public static final Block LEAVES = registerBlock(18, new Block());
	public static final Block SPONGE = registerBlock(19, new Block());
	public static final Block GLASS = registerBlock(20, new Block());
	public static final Block LAPIS_ORE = registerBlock(21, new Block());
	public static final Block LAPIS_BLOCk = registerBlock(22, new Block());
	public static final Block DISPENSER = registerBlock(23, new Block());
	public static final Block SANDSTONE = registerBlock(24, new Block());
	public static final Block NOTEBLOCk = registerBlock(25, new Block());
	public static final Block BED = registerBlock(26, new Block());
	public static final Block POWERED_RAIL = registerBlock(27, new Block());
	public static final Block DETECTOR_RAIL = registerBlock(28, new Block());
	public static final Block STICKY_PISTON = registerBlock(29, new Block());
	public static final Block WEB = registerBlock(30, new Block());
	public static final Block TALL_GRASS = registerBlock(31, new Block());
	public static final Block DEAD_BUSH = registerBlock(32, new Block());
	public static final Block PISTON = registerBlock(33, new Block());
	public static final Block PISTON_EXTENDED = registerBlock(34, new Block());
	public static final Block CLOTH = registerBlock(35, new Block());
	public static final Block MOVING_PISTON = registerBlock(36, new Block());
	public static final Block YELLOW_PLANT = registerBlock(37, new Block());
	public static final Block POPPY = registerBlock(38, new Block());
	public static final Block BROWN_MUSHROOM = registerBlock(39, new Block());
	public static final Block RED_MUSHROOM = registerBlock(40, new Block());
	public static final Block GOLDEN_BLOCK = registerBlock(41, new Block());
	public static final Block IRON_BLOCk = registerBlock(42, new Block());
	public static final Block DOUBLE_STAIR = registerBlock(43, new Block());
	public static final Block SINGLE_STAIR = registerBlock(44, new Block());
	public static final Block BRICK = registerBlock(45, new Block());
	public static final Block TNT = registerBlock(46, new Block());
	public static final Block BOOKSHELF = registerBlock(47, new Block());
	public static final Block MOSSY_COBBLESTONE = registerBlock(48, new Block());
	public static final Block OBSIDIAN = registerBlock(49, new Block());
	public static final Block TORCH = registerBlock(50, new Block());
	public static final Block FIRE = registerBlock(51, new Block());
	public static final Block MOB_SPAWNER = registerBlock(52, new Block());
	public static final Block OAK_STAIRS = registerBlock(53, new Block());
	public static final Block CHEST = registerBlock(54, new Block());
	public static final Block REDSTONE_DUST = registerBlock(55, new Block());
	public static final Block DIAMOND_ORE = registerBlock(56, new Block());
	public static final Block DIAMOND_BLOCK = registerBlock(57, new Block());
	public static final Block CRAFTING_TABLE = registerBlock(58, new Block());
	public static final Block CROPS = registerBlock(59, new Block());
	public static final Block TILLED_FARMLAND = registerBlock(60, new Block());
	public static final Block FURNACE = registerBlock(61, new Block());
	public static final Block FURNACE_ACTIVE = registerBlock(62, new Block());
	public static final Block SIGN = registerBlock(63, new Block());
	public static final Block OAK_DOOR = registerBlock(64, new Block());
	public static final Block LADDER = registerBlock(65, new Block());
	public static final Block RAIL = registerBlock(66, new Block());
	public static final Block COBBLESTONE_STAIRS = registerBlock(67, new Block());
	public static final Block WALL_SIGN = registerBlock(68, new Block());
	public static final Block LEVER = registerBlock(69, new Block());
	public static final Block STONE_PRESSURE_PLATE = registerBlock(70, new Block());
	public static final Block IRON_DOOR = registerBlock(71, new Block());
	public static final Block OAK_PRESSURE_PLATE = registerBlock(72, new Block());
	public static final Block REDSTONE_ORE = registerBlock(73, new Block());
	public static final Block REDSTONE_ORE_ACTIVE = registerBlock(74, new Block());
	public static final Block REDSTONE_TORCH = registerBlock(75, new Block());
	public static final Block REDSTONE_TORCH_ACTIVE = registerBlock(76, new Block());
	public static final Block BUTTON = registerBlock(77, new Block());
	public static final Block SNOW = registerBlock(78, new Block());
	public static final Block ICE = registerBlock(79, new Block());
	public static final Block SNOW_BLOCK = registerBlock(80, new Block());
	public static final Block CACTUS = registerBlock(81, new Block());
	public static final Block CLAY = registerBlock(82, new Block());
	public static final Block REED = registerBlock(83, new Block());
	public static final Block JUKEBOX = registerBlock(84, new Block());
	public static final Block FENCE = registerBlock(85, new Block());
	public static final Block PUMPKIN = registerBlock(86, new Block());
	public static final Block NETHERRACK = registerBlock(87, new Block());
	public static final Block SOUL_SAND = registerBlock(88, new Block());
	public static final Block GLOWSTONE = registerBlock(89, new Block());
	public static final Block PORTAL = registerBlock(90, new Block());
	public static final Block LANTERN = registerBlock(91, new Block());
	public static final Block CAKE = registerBlock(92, new Block());
	public static final Block REDSTONE_REPEATER = registerBlock(93, new Block());
	public static final Block REDSTONE_REPEATER_ACTIVE = registerBlock(94, new Block());
	public static final Block LOCKED_CHEST = registerBlock(95, new Block());
	public static final Block TRAPDOOR = registerBlock(96, new Block());

	private static Block registerBlock(final int id, final Block block) {
		REGISTRY.put(id, block);
		return block;
	}

	public static int getId(final Block block) {
		for (final var entry : REGISTRY.entrySet()) {
			if (entry.getValue() == block) {
				return entry.getKey();
			}
		}

		return -1;
	}

	public static @Nullable Block byId(final int id) {
		if (id > 256) {
			throw new IllegalArgumentException("Expected id for blocks, not items");
		} else {
			return REGISTRY.getOrDefault(id, null);
		}
	}
}
