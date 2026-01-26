package me.alphamode.beta.proxy.data.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ItemMapper {
	private static ItemFactory STONE = map(1, item(Items.STONE));
	private static ItemFactory GRASS = map(2, item(Items.GRASS_BLOCK));
	private static ItemFactory DIRT = map(3, item(Items.DIRT));
	private static ItemFactory COBBLESTONE = map(4, item(Items.COBBLESTONE));
	private static ItemFactory PLANKS = map(5, item(Items.OAK_PLANKS));  // TODO
	private static ItemFactory SAPLING = map(6, item(Items.OAK_SAPLING));  // TODO
	private static ItemFactory BEDROCK = map(7, item(Items.BEDROCK));
	private static ItemFactory FLOWING_WATER = map(8, item(Items.WATER_BUCKET));
	private static ItemFactory STILL_WATER = map(9, item(Items.WATER_BUCKET));
	private static ItemFactory FLOWING_LAVA = map(10, item(Items.LAVA_BUCKET));
	private static ItemFactory STILL_LAVA = map(11, item(Items.LAVA_BUCKET));
	private static ItemFactory SAND = map(12, item(Items.SAND));
	private static ItemFactory GRAVEL = map(13, item(Items.GRAVEL));
	private static ItemFactory GOLDEN_ORE = map(14, item(Items.GOLD_ORE));
	private static ItemFactory IRON_ORE = map(15, item(Items.IRON_ORE));
	private static ItemFactory COAL_ORE = map(16, item(Items.COAL_ORE));
	private static ItemFactory WOOD = map(17, item(Items.OAK_LOG)); // TODO
	private static ItemFactory LEAVES = map(18, item(Items.OAK_LEAVES)); // TODO
	private static ItemFactory SPONGE = map(19, item(Items.SPONGE));
	private static ItemFactory GLASS = map(20, item(Items.GLASS));
	private static ItemFactory LAPIS_ORE = map(21, item(Items.LAPIS_ORE));
	private static ItemFactory LAPIS_BLOCK = map(22, item(Items.LAPIS_BLOCK));
	private static ItemFactory DISPENSER = map(23, item(Items.DISPENSER));
	private static ItemFactory SANDSTONE = map(24, item(Items.SANDSTONE));
	private static ItemFactory NOTEBLOCk = map(25, item(Items.NOTE_BLOCK));
	private static ItemFactory BED = map(26, item(Items.RED_BED));
	private static ItemFactory POWERED_RAIL = map(27, item(Items.POWERED_RAIL));
	private static ItemFactory DETECTOR_RAIL = map(28, item(Items.DETECTOR_RAIL));
	private static ItemFactory STICKY_PISTON = map(29, item(Items.STICKY_PISTON));
	private static ItemFactory WEB = map(30, item(Items.COBWEB));
	private static ItemFactory TALL_GRASS = map(31, item(Items.TALL_GRASS));
	private static ItemFactory DEAD_BUSH = map(32, item(Items.DEAD_BUSH));
	private static ItemFactory PISTON = map(33, item(Items.PISTON));
	private static ItemFactory PISTON_EXTENDED = map(34, item(Items.PISTON));
	private static ItemFactory CLOTH = map(35, item(Items.WHITE_WOOL)); // TODO
	private static ItemFactory MOVING_PISTON = map(36, item(Items.PISTON));
	private static ItemFactory YELLOW_PLANT = map(37, item(Items.DANDELION));
	private static ItemFactory POPPY = map(38, item(Items.POPPY));
	private static ItemFactory BROWN_MUSHROOM = map(39, item(Items.BROWN_MUSHROOM));
	private static ItemFactory RED_MUSHROOM = map(40, item(Items.RED_MUSHROOM));
	private static ItemFactory GOLD_BLOCK = map(41, item(Items.GOLD_BLOCK));
	private static ItemFactory IRON_BLOCK = map(42, item(Items.IRON_BLOCK));
	private static ItemFactory DOUBLE_STAIR = map(43, item(Items.OAK_STAIRS)); // TODO
	private static ItemFactory SINGLE_STAIR = map(44, item(Items.OAK_STAIRS)); // TODO
	private static ItemFactory BRICK = map(45, item(Items.BRICKS));
	private static ItemFactory TNT = map(46, item(Items.TNT));
	private static ItemFactory BOOKSHELF = map(47, item(Items.BOOKSHELF));
	private static ItemFactory MOSSY_COBBLESTONE = map(48, item(Items.MOSSY_COBBLESTONE));
	private static ItemFactory OBSIDIAN = map(49, item(Items.OBSIDIAN));
	private static ItemFactory TORCH = map(50, item(Items.TORCH));
	private static ItemFactory FIRE = map(51, item(Items.FLINT_AND_STEEL));
	private static ItemFactory MOB_SPAWNER = map(52, item(Items.SPAWNER));
	private static ItemFactory OAK_STAIRS = map(53, item(Items.OAK_STAIRS));
	private static ItemFactory CHEST = map(54, item(Items.CHEST));
	private static ItemFactory REDSTONE_DUST = map(55, item(Items.REDSTONE));
	private static ItemFactory DIAMOND_ORE = map(56, item(Items.DIAMOND_ORE));
	private static ItemFactory DIAMOND_BLOCK = map(57, item(Items.DIAMOND_BLOCK));
	private static ItemFactory CRAFTING_TABLE = map(58, item(Items.CRAFTING_TABLE));
	private static ItemFactory CROPS = map(59, item(Items.WHEAT_SEEDS));
	private static ItemFactory TILLED_FARMLAND = map(60, item(Items.FARMLAND)); // TODO (tilled)
	private static ItemFactory FURNACE = map(61, item(Items.FURNACE));
	private static ItemFactory FURNACE_ACTIVE = map(62, item(Items.FURNACE)); // todo (active)
	private static ItemFactory SIGN = map(63, item(Items.OAK_SIGN));
	private static ItemFactory OAK_DOOR = map(64, item(Items.OAK_DOOR));
	private static ItemFactory LADDER = map(65, item(Items.LADDER));
	private static ItemFactory RAIL = map(66, item(Items.RAIL));
	private static ItemFactory COBBLESTONE_STAIRS = map(67, item(Items.COBBLESTONE_STAIRS));
	private static ItemFactory WALL_SIGN = map(68, item(Items.OAK_SIGN));
	private static ItemFactory LEVER = map(69, item(Items.LEVER));
	private static ItemFactory STONE_PRESSURE_PLATE = map(70, item(Items.STONE_PRESSURE_PLATE));
	private static ItemFactory IRON_DOOR = map(71, item(Items.IRON_DOOR));
	private static ItemFactory OAK_PRESSURE_PLATE = map(72, item(Items.OAK_PRESSURE_PLATE));
	private static ItemFactory REDSTONE_ORE = map(73, item(Items.REDSTONE_ORE));
	private static ItemFactory REDSTONE_ORE_ACTIVE = map(74, item(Items.REDSTONE_ORE));
	private static ItemFactory REDSTONE_TORCH = map(75, item(Items.REDSTONE_TORCH));
	private static ItemFactory REDSTONE_TORCH_ACTIVE = map(76, item(Items.REDSTONE_TORCH));
	private static ItemFactory BUTTON = map(77, item(Items.OAK_BUTTON)); // todo?
	private static ItemFactory SNOW = map(78, item(Items.SNOW));
	private static ItemFactory ICE = map(79, item(Items.ICE));
	private static ItemFactory SNOW_BLOCK = map(80, item(Items.SNOW_BLOCK));
	private static ItemFactory CACTUS = map(81, item(Items.CACTUS));
	private static ItemFactory CLAY = map(82, item(Items.CLAY));
	private static ItemFactory REED = map(83, item(Items.SUGAR_CANE));
	private static ItemFactory JUKEBOX = map(84, item(Items.JUKEBOX));
	private static ItemFactory FENCE = map(85, item(Items.OAK_FENCE)); // todo
	private static ItemFactory PUMPKIN = map(86, item(Items.PUMPKIN));
	private static ItemFactory NETHERRACK = map(87, item(Items.NETHERRACK));
	private static ItemFactory SOUL_SAND = map(88, item(Items.SOUL_SAND));
	private static ItemFactory GLOWSTONE = map(89, item(Items.GLOWSTONE));
	private static ItemFactory PORTAL = map(90, item(Items.PURPLE_WOOL)); // todo
	private static ItemFactory LANTERN = map(91, item(Items.LANTERN));
	private static ItemFactory CAKE = map(92, item(Items.CAKE));
	private static ItemFactory REDSTONE_REPEATER = map(93, item(Items.REPEATER));
	private static ItemFactory REDSTONE_REPEATER_ACTIVE = map(94, item(Items.REPEATER)); // TODO: active
	private static ItemFactory LOCKED_CHEST = map(95, item(Items.CHEST));
	private static ItemFactory TRAPDOOR = map(96, item(Items.OAK_TRAPDOOR)); // todo: was there iron?
	private static ItemFactory IRON_SHOVEL = map(256, item(Items.IRON_SHOVEL));
	private static ItemFactory IRON_PICKAXE = map(256 + 1, item(Items.IRON_PICKAXE));
	private static ItemFactory IRON_AXE = map(256 + 2, item(Items.IRON_AXE));
	private static ItemFactory FLINT_AND_STEEL = map(256 + 3, item(Items.FLINT_AND_STEEL));
	private static ItemFactory APPLE = map(256 + 4, item(Items.APPLE));
	private static ItemFactory BOW = map(256 + 5, item(Items.BOW));
	private static ItemFactory ARROW = map(256 + 6, item(Items.ARROW));
	private static ItemFactory COAL = map(256 + 7, item(Items.COAL));
	private static ItemFactory DIAMOND = map(256 + 8, item(Items.DIAMOND));
	private static ItemFactory IRON_INGOT = map(256 + 9, item(Items.IRON_INGOT));
	private static ItemFactory GOLD_INGOT = map(256 + 10, item(Items.GOLD_INGOT));
	private static ItemFactory IRON_SWORD = map(256 + 11, item(Items.IRON_SWORD));
	private static ItemFactory WOODEN_SWORD = map(256 + 12, item(Items.WOODEN_SWORD));
	private static ItemFactory WOODEN_SHOVEL = map(256 + 13, item(Items.WOODEN_SHOVEL));
	private static ItemFactory WOODEN_PICKAXE = map(256 + 14, item(Items.WOODEN_PICKAXE));
	private static ItemFactory WOODEN_AXE = map(256 + 15, item(Items.WOODEN_AXE));
	private static ItemFactory STONE_SWORD = map(256 + 16, item(Items.STONE_SWORD));
	private static ItemFactory STONE_SHOVEL = map(256 + 17, item(Items.STONE_SHOVEL));
	private static ItemFactory STONE_PICKAXE = map(256 + 18, item(Items.STONE_PICKAXE));
	private static ItemFactory STONE_AXE = map(256 + 19, item(Items.STONE_AXE));
	private static ItemFactory DIAMOND_SWORD = map(256 + 20, item(Items.DIAMOND_SWORD));
	private static ItemFactory DIAMOND_SHOVEL = map(256 + 21, item(Items.DIAMOND_SHOVEL));
	private static ItemFactory DIAMOND_PICKAXE = map(256 + 22, item(Items.DIAMOND_PICKAXE));
	private static ItemFactory DIAMOND_AXE = map(256 + 23, item(Items.DIAMOND_AXE));
	private static ItemFactory STICK = map(256 + 24, item(Items.STICK));
	private static ItemFactory BOWL = map(256 + 25, item(Items.BOWL));
	private static ItemFactory STEW = map(256 + 26, item(Items.MUSHROOM_STEW));
	private static ItemFactory GOLDEN_SWORD = map(256 + 27, item(Items.GOLDEN_SWORD));
	private static ItemFactory GOLDEN_SHOVEL = map(256 + 28, item(Items.GOLDEN_SHOVEL));
	private static ItemFactory GOLDEN_PICKAXE = map(256 + 29, item(Items.GOLDEN_PICKAXE));
	private static ItemFactory GOLDEN_AXE = map(256 + 30, item(Items.GOLDEN_AXE));
	private static ItemFactory STRING = map(256 + 31, item(Items.STRING));
	private static ItemFactory FEATHER = map(256 + 32, item(Items.FEATHER));
	private static ItemFactory GUN_POWDER = map(256 + 33, item(Items.GUNPOWDER));
	private static ItemFactory WOODEN_HOE = map(256 + 34, item(Items.WOODEN_HOE));
	private static ItemFactory STONE_HOE = map(256 + 35, item(Items.STONE_HOE));
	private static ItemFactory IRON_HOE = map(256 + 36, item(Items.IRON_HOE));
	private static ItemFactory DIAMOND_HOE = map(256 + 37, item(Items.DIAMOND_HOE));
	private static ItemFactory GOLDEN_HOE = map(256 + 38, item(Items.GOLDEN_HOE));
	private static ItemFactory SEEDS = map(256 + 39, item(Items.WHEAT_SEEDS));
	private static ItemFactory WHEAT = map(256 + 40, item(Items.WHEAT));
	private static ItemFactory BREAD = map(256 + 41, item(Items.BREAD));
	private static ItemFactory LEATHER_HELMET = map(256 + 42, item(Items.LEATHER_HELMET));
	private static ItemFactory LEATHER_CHESTPLATE = map(256 + 43, item(Items.LEATHER_CHESTPLATE));
	private static ItemFactory LEATHER_LEGGINGS = map(256 + 44, item(Items.LEATHER_LEGGINGS));
	private static ItemFactory LEATHER_BOOTS = map(256 + 45, item(Items.LEATHER_BOOTS));
	private static ItemFactory CHAIN_HELMET = map(256 + 46, item(Items.CHAINMAIL_HELMET));
	private static ItemFactory CHAIN_CHESTPLATE = map(256 + 47, item(Items.CHAINMAIL_CHESTPLATE));
	private static ItemFactory CHAIN_LEGGINGS = map(256 + 48, item(Items.CHAINMAIL_LEGGINGS));
	private static ItemFactory CHAIN_BOOTS = map(256 + 49, item(Items.CHAINMAIL_BOOTS));
	private static ItemFactory IRON_HELMET = map(256 + 50, item(Items.IRON_HELMET));
	private static ItemFactory IRON_CHESTPLATE = map(256 + 51, item(Items.IRON_CHESTPLATE));
	private static ItemFactory IRON_LEGGINGS = map(256 + 52, item(Items.IRON_LEGGINGS));
	private static ItemFactory IRON_BOOTS = map(256 + 53, item(Items.IRON_BOOTS));
	private static ItemFactory DIAMOND_HELMET = map(256 + 54, item(Items.DIAMOND_HELMET));
	private static ItemFactory DIAMOND_CHESTPLATE = map(256 + 55, item(Items.DIAMOND_CHESTPLATE));
	private static ItemFactory DIAMOND_LEGGINGS = map(256 + 56, item(Items.DIAMOND_LEGGINGS));
	private static ItemFactory DIAMOND_BOOTS = map(256 + 57, item(Items.DIAMOND_BOOTS));
	private static ItemFactory GOLDEN_HELMET = map(256 + 58, item(Items.GOLDEN_HELMET));
	private static ItemFactory GOLDEN_CHESTPLATE = map(256 + 59, item(Items.GOLDEN_CHESTPLATE));
	private static ItemFactory GOLDEN_LEGGINGS = map(256 + 60, item(Items.GOLDEN_LEGGINGS));
	private static ItemFactory GOLDEN_BOOTS = map(256 + 61, item(Items.GOLDEN_BOOTS));
	private static ItemFactory FLINT = map(256 + 62, item(Items.FLINT));
	private static ItemFactory RAW_PORKCHOP = map(256 + 63, item(Items.PORKCHOP));
	private static ItemFactory COOKED_PORKCHOP = map(256 + 64, item(Items.COOKED_PORKCHOP));
	private static ItemFactory PAINTING = map(256 + 65, item(Items.PAINTING));
	private static ItemFactory GOLDEN_APPLE = map(256 + 66, item(Items.GOLDEN_APPLE));
	private static ItemFactory SIGN_ITEM = map(256 + 67, item(Items.OAK_SIGN));
	private static ItemFactory WOODEN_DOOR = map(256 + 68, item(Items.OAK_DOOR));
	private static ItemFactory BUCKET = map(256 + 69, item(Items.BUCKET));
	private static ItemFactory WATER_BUCKET = map(256 + 70, item(Items.WATER_BUCKET));
	private static ItemFactory LAVA_BUCKET = map(256 + 71, item(Items.LAVA_BUCKET));
	private static ItemFactory MINECART = map(256 + 72, item(Items.MINECART));
	private static ItemFactory SADDLE = map(256 + 73, item(Items.SADDLE));
	private static ItemFactory IRON_DOOR_ITEM = map(256 + 74, item(Items.IRON_DOOR));
	private static ItemFactory REDSTONE = map(256 + 75, item(Items.REDSTONE));
	private static ItemFactory SNOWBALL = map(256 + 76, item(Items.SNOWBALL));
	private static ItemFactory BOAT = map(256 + 77, item(Items.OAK_BOAT));
	private static ItemFactory LEATHER = map(256 + 78, item(Items.LEATHER));
	private static ItemFactory MILK_BUCKET = map(256 + 79, item(Items.MILK_BUCKET));
	private static ItemFactory BRICK_ITEM = map(256 + 80, item(Items.BRICK));
	private static ItemFactory CLAY_ITEM = map(256 + 81, item(Items.CLAY_BALL));
	private static ItemFactory SUGAR_CANE = map(256 + 82, item(Items.SUGAR_CANE));
	private static ItemFactory PAPER = map(256 + 83, item(Items.PAPER));
	private static ItemFactory BOOK = map(256 + 84, item(Items.BOOK));
	private static ItemFactory SLIMEBALL = map(256 + 85, item(Items.SLIME_BALL));
	private static ItemFactory CHEST_MINECART = map(256 + 86, item(Items.CHEST_MINECART));
	private static ItemFactory FURNACE_MINECART = map(256 + 87, item(Items.FURNACE_MINECART));
	private static ItemFactory EGG = map(256 + 88, item(Items.EGG));
	private static ItemFactory COMPASS = map(256 + 89, item(Items.COMPASS));
	private static ItemFactory FISHING_ROD = map(256 + 90, item(Items.FISHING_ROD));
	private static ItemFactory CLOCK = map(256 + 91, item(Items.CLOCK));
	private static ItemFactory GLOWSTONE_DUST = map(256 + 92, item(Items.GLOWSTONE_DUST));
	private static ItemFactory RAW_FISH = map(256 + 93, item(Items.COD)); // TODO
	private static ItemFactory COOKED_FISH = map(256 + 94, item(Items.COOKED_COD)); // TODO
	private static ItemFactory DYE_POWDER = map(256 + 95, item(Items.WHITE_DYE)); // TODO
	private static ItemFactory BONE = map(256 + 96, item(Items.BONE));
	private static ItemFactory SUGAR = map(256 + 97, item(Items.SUGAR));
	private static ItemFactory CAKE_ITEM = map(256 + 98, item(Items.CAKE));
	private static ItemFactory BED_ITEM = map(256 + 99, item(Items.RED_BED));
	private static ItemFactory REDSTONE_REPEATER_ITEM = map(256 + 100, item(Items.REPEATER));
	private static ItemFactory COOKIE = map(256 + 101, item(Items.COOKIE));
	private static ItemFactory MAP = map(256 + 102, item(Items.MAP));
	private static ItemFactory SHEARS = map(256 + 103, item(Items.SHEARS));
	private static ItemFactory MUSIC_DISC_13 = map(256 + 2000, item(Items.MUSIC_DISC_13));
	private static ItemFactory MUSIC_DISC_CAT = map(256 + 2001, item(Items.MUSIC_DISC_CAT));

	private static ItemFactory item(final Item item) {
		return () -> item;
	}

	private static ItemFactory map(final int id, final ItemFactory factory) {
		return factory;
	}

	public static void bootstrap() {

	}

	@FunctionalInterface
	public interface ItemFactory {
		Item getItem();
	}

}
