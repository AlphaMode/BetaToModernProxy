package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.util.data.Block;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class BetaBlocks {
	private static final Map<Integer, Block> REGISTRY = new HashMap<>();

	public static final Block STONE = registerBlock(new Block(1));
	public static final Block GRASS = registerBlock(new Block(2));
	public static final Block DIRT = registerBlock(new Block(3));
	public static final Block COBBLESTONE = registerBlock(new Block(4));
	public static final Block PLANKS = registerBlock(new Block(5));
	public static final Block SAPLING = registerBlock(new Block(6));
	public static final Block BEDROCK = registerBlock(new Block(7));
	public static final Block FLOWING_WATER = registerBlock(new Block(8));
	public static final Block STILL_WATER = registerBlock(new Block(9));
	public static final Block FLOWING_LAVA = registerBlock(new Block(10));
	public static final Block STILL_LAVA = registerBlock(new Block(11));
	public static final Block SAND = registerBlock(new Block(12));
	public static final Block GRAVEL = registerBlock(new Block(13));
	public static final Block GOLDEN_ORE = registerBlock(new Block(14));
	public static final Block IRON_ORE = registerBlock(new Block(15));
	public static final Block COAL_ORE = registerBlock(new Block(16));
	public static final Block WOOD = registerBlock(new Block(17));
	public static final Block LEAVES = registerBlock(new Block(18));
	public static final Block SPONGE = registerBlock(new Block(19));
	public static final Block GLASS = registerBlock(new Block(20));
	public static final Block LAPIS_ORE = registerBlock(new Block(21));
	public static final Block LAPIS_BLOCk = registerBlock(new Block(22));
	public static final Block DISPENSER = registerBlock(new Block(23));
	public static final Block SANDSTONE = registerBlock(new Block(24));
	public static final Block NOTEBLOCk = registerBlock(new Block(25));
	public static final Block BED = registerBlock(new Block(26));
	public static final Block POWERED_RAIL = registerBlock(new Block(27));
	public static final Block DETECTOR_RAIL = registerBlock(new Block(28));
	public static final Block STICKY_PISTON = registerBlock(new Block(29));
	public static final Block WEB = registerBlock(new Block(30));
	public static final Block TALL_GRASS = registerBlock(new Block(31));
	public static final Block DEAD_BUSH = registerBlock(new Block(32));
	public static final Block PISTON = registerBlock(new Block(33));
	public static final Block PISTON_EXTENDED = registerBlock(new Block(34));
	public static final Block CLOTH = registerBlock(new Block(35));
	public static final Block MOVING_PISTON = registerBlock(new Block(36));
	public static final Block YELLOW_PLANT = registerBlock(new Block(37));
	public static final Block POPPY = registerBlock(new Block(38));
	public static final Block BROWN_MUSHROOM = registerBlock(new Block(39));
	public static final Block RED_MUSHROOM = registerBlock(new Block(40));
	public static final Block GOLDEN_BLOCK = registerBlock(new Block(41));
	public static final Block IRON_BLOCk = registerBlock(new Block(42));
	public static final Block DOUBLE_STAIR = registerBlock(new Block(43));
	public static final Block SINGLE_STAIR = registerBlock(new Block(44));
	public static final Block BRICK = registerBlock(new Block(45));
	public static final Block TNT = registerBlock(new Block(46));
	public static final Block BOOKSHELF = registerBlock(new Block(47));
	public static final Block MOSSY_COBBLESTONE = registerBlock(new Block(48));
	public static final Block OBSIDIAN = registerBlock(new Block(49));
	public static final Block TORCH = registerBlock(new Block(50));
	public static final Block FIRE = registerBlock(new Block(51));
	public static final Block MOB_SPAWNER = registerBlock(new Block(52));
	public static final Block OAK_STAIRS = registerBlock(new Block(53));
	public static final Block CHEST = registerBlock(new Block(54));
	public static final Block REDSTONE_DUST = registerBlock(new Block(55));
	public static final Block DIAMOND_ORE = registerBlock(new Block(56));
	public static final Block DIAMOND_BLOCK = registerBlock(new Block(57));
	public static final Block CRAFTING_TABLE = registerBlock(new Block(58));
	public static final Block CROPS = registerBlock(new Block(59));
	public static final Block TILLED_FARMLAND = registerBlock(new Block(60));
	public static final Block FURNACE = registerBlock(new Block(61));
	public static final Block FURNACE_ACTIVE = registerBlock(new Block(62));
	public static final Block SIGN = registerBlock(new Block(63));
	public static final Block OAK_DOOR = registerBlock(new Block(64));
	public static final Block LADDER = registerBlock(new Block(65));
	public static final Block RAIL = registerBlock(new Block(66));
	public static final Block COBBLESTONE_STAIRS = registerBlock(new Block(67));
	public static final Block WALL_SIGN = registerBlock(new Block(68));
	public static final Block LEVER = registerBlock(new Block(69));
	public static final Block STONE_PRESSURE_PLATE = registerBlock(new Block(70));
	public static final Block IRON_DOOR = registerBlock(new Block(71));
	public static final Block OAK_PRESSURE_PLATE = registerBlock(new Block(72));
	public static final Block REDSTONE_ORE = registerBlock(new Block(73));
	public static final Block REDSTONE_ORE_ACTIVE = registerBlock(new Block(74));
	public static final Block REDSTONE_TORCH = registerBlock(new Block(75));
	public static final Block REDSTONE_TORCH_ACTIVE = registerBlock(new Block(76));
	public static final Block BUTTON = registerBlock(new Block(77));
	public static final Block SNOW = registerBlock(new Block(78));
	public static final Block ICE = registerBlock(new Block(79));
	public static final Block SNOW_BLOCK = registerBlock(new Block(80));
	public static final Block CACTUS = registerBlock(new Block(81));
	public static final Block CLAY = registerBlock(new Block(82));
	public static final Block REED = registerBlock(new Block(83));
	public static final Block JUKEBOX = registerBlock(new Block(84));
	public static final Block FENCE = registerBlock(new Block(85));
	public static final Block PUMPKIN = registerBlock(new Block(86));
	public static final Block NETHERRACK = registerBlock(new Block(87));
	public static final Block SOUL_SAND = registerBlock(new Block(88));
	public static final Block GLOWSTONE = registerBlock(new Block(89));
	public static final Block PORTAL = registerBlock(new Block(90));
	public static final Block LANTERN = registerBlock(new Block(91));
	public static final Block CAKE = registerBlock(new Block(92));
	public static final Block REDSTONE_REPEATER = registerBlock(new Block(93));
	public static final Block REDSTONE_REPEATER_ACTIVE = registerBlock(new Block(94));
	public static final Block LOCKED_CHEST = registerBlock(new Block(95));
	public static final Block TRAPDOOR = registerBlock(new Block(96));

	public static Block registerBlock(final Block block) {
		REGISTRY.put(block.id(), block);
		return block;
	}

	public static @Nullable Block byId(final int id) {
		if (id > 256) {
			throw new IllegalArgumentException("Expected id for blocks, not items");
		} else {
			BrodernProxy.LOGGER.warn("Got block with id {}", id);
			return REGISTRY.getOrDefault(id, null);
		}
	}
}
