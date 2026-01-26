package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.data.Item;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class BetaItems {
	private static final Map<Integer, Item> REGISTRY = new HashMap<>();

	public static Item IRON_SHOVEL = registerItem(new Item(0));
	public static Item IRON_PICKAXE = registerItem(new Item(1));
	public static Item IRON_AXE = registerItem(new Item(2));
	public static Item FLINT_AND_STEEL = registerItem(new Item(3));
	public static Item APPLE = registerItem(new Item(4));
	public static Item BOW = registerItem(new Item(5));
	public static Item ARROW = registerItem(new Item(6));
	public static Item COAL = registerItem(new Item(7));
	public static Item DIAMOND = registerItem(new Item(8));
	public static Item IRON_INGOT = registerItem(new Item(9));
	public static Item GOLD_INGOT = registerItem(new Item(10));
	public static Item IRON_SWORD = registerItem(new Item(11));
	public static Item WOODEN_SWORD = registerItem(new Item(12));
	public static Item WOODEN_SHOVEL = registerItem(new Item(13));
	public static Item WOODEN_PICKAXE = registerItem(new Item(14));
	public static Item WOODEN_AXE = registerItem(new Item(15));
	public static Item STONE_SWORD = registerItem(new Item(16));
	public static Item STONE_SHOVEL = registerItem(new Item(17));
	public static Item STONE_PICKAXE = registerItem(new Item(18));
	public static Item STONE_AXE = registerItem(new Item(19));
	public static Item DIAMOND_SWORD = registerItem(new Item(20));
	public static Item DIAMOND_SHOVEL = registerItem(new Item(21));
	public static Item DIAMOND_PICKAXE = registerItem(new Item(22));
	public static Item DIAMOND_AXE = registerItem(new Item(23));
	public static Item STICK = registerItem(new Item(24));
	public static Item BOWL = registerItem(new Item(25));
	public static Item STEW = registerItem(new Item(26));
	public static Item GOLDEN_SWORD = registerItem(new Item(27));
	public static Item GOLDEN_SHOVEL = registerItem(new Item(28));
	public static Item GOLDEN_PICKAXE = registerItem(new Item(29));
	public static Item GOLDEN_AXE = registerItem(new Item(30));
	public static Item STRING = registerItem(new Item(31));
	public static Item FEATHER = registerItem(new Item(32));
	public static Item GUN_POWDER = registerItem(new Item(33));
	public static Item WOODEN_HOE = registerItem(new Item(34));
	public static Item STONE_HOE = registerItem(new Item(35));
	public static Item IRON_HOE = registerItem(new Item(36));
	public static Item DIAMOND_HOE = registerItem(new Item(37));
	public static Item GOLDEN_HOE = registerItem(new Item(38));
	public static Item SEEDS = registerItem(new Item(39));
	public static Item WHEAT = registerItem(new Item(40));
	public static Item BREAD = registerItem(new Item(41));
	public static Item LEATHER_HELMET = registerItem(new Item(42));
	public static Item LEATHER_CHESTPLATE = registerItem(new Item(43));
	public static Item LEATHER_LEGGINGS = registerItem(new Item(44));
	public static Item LEATHER_BOOTS = registerItem(new Item(45));
	public static Item CHAIN_HELMET = registerItem(new Item(46));
	public static Item CHAIN_CHESTPLATE = registerItem(new Item(47));
	public static Item CHAIN_LEGGINGS = registerItem(new Item(48));
	public static Item CHAIN_BOOTS = registerItem(new Item(49));
	public static Item IRON_HELMET = registerItem(new Item(50));
	public static Item IRON_CHESTPLATE = registerItem(new Item(51));
	public static Item IRON_LEGGINGS = registerItem(new Item(52));
	public static Item IRON_BOOTS = registerItem(new Item(53));
	public static Item DIAMOND_HELMET = registerItem(new Item(54));
	public static Item DIAMOND_CHESTPLATE = registerItem(new Item(55));
	public static Item DIAMOND_LEGGINGS = registerItem(new Item(56));
	public static Item DIAMOND_BOOTS = registerItem(new Item(57));
	public static Item GOLDEN_HELMET = registerItem(new Item(58));
	public static Item GOLDEN_CHESTPLATE = registerItem(new Item(59));
	public static Item GOLDEN_LEGGINGS = registerItem(new Item(60));
	public static Item GOLDEN_BOOTS = registerItem(new Item(61));
	public static Item FLINT = registerItem(new Item(62));
	public static Item RAW_PORKCHOP = registerItem(new Item(63));
	public static Item COOKED_PORKCHOP = registerItem(new Item(64));
	public static Item PAINTING = registerItem(new Item(65));
	public static Item GOLDEN_APPLE = registerItem(new Item(66));
	public static Item SIGN = registerItem(new Item(67));
	public static Item WOODEN_DOOR = registerItem(new Item(68));
	public static Item BUCKET = registerItem(new Item(69));
	public static Item WATER_BUCKET = registerItem(new Item(70));
	public static Item LAVA_BUCKET = registerItem(new Item(71));
	public static Item MINECART = registerItem(new Item(72));
	public static Item SADDLE = registerItem(new Item(73));
	public static Item IRON_DOOR = registerItem(new Item(74));
	public static Item REDSTONE = registerItem(new Item(75));
	public static Item SNOWBALL = registerItem(new Item(76));
	public static Item BOAT = registerItem(new Item(77));
	public static Item LEATHER = registerItem(new Item(78));
	public static Item MILK_BUCKET = registerItem(new Item(79));
	public static Item BRICK = registerItem(new Item(80));
	public static Item CLAY = registerItem(new Item(81));
	public static Item SUGAR_CANE = registerItem(new Item(82));
	public static Item PAPER = registerItem(new Item(83));
	public static Item BOOK = registerItem(new Item(84));
	public static Item SLIMEBALL = registerItem(new Item(85));
	public static Item CHEST_MINECART = registerItem(new Item(86));
	public static Item FURNACE_MINECART = registerItem(new Item(87));
	public static Item EGG = registerItem(new Item(88));
	public static Item COMPASS = registerItem(new Item(89));
	public static Item FISHING_ROD = registerItem(new Item(90));
	public static Item CLOCK = registerItem(new Item(91));
	public static Item GLOWSTONE_DUST = registerItem(new Item(92));
	public static Item RAW_FISH = registerItem(new Item(93));
	public static Item COOKED_FISH = registerItem(new Item(94));
	public static Item DYE_POWDER = registerItem(new Item(95));
	public static Item BONE = registerItem(new Item(96));
	public static Item SUGAR = registerItem(new Item(97));
	public static Item CAKE = registerItem(new Item(98));
	public static Item BED = registerItem(new Item(99));
	public static Item REDSTONE_REPEATER = registerItem(new Item(100));
	public static Item COOKIE = registerItem(new Item(101));
	public static Item MAP = registerItem(new Item(102));
	public static Item SHEARS = registerItem(new Item(103));
	public static Item MUSIC_DISC_13 = registerItem(new Item(2000));
	public static Item MUSIC_DISC_CAT = registerItem(new Item(2001));

	private static Item registerItem(final Item item) {
		REGISTRY.put(item.id(), item);
		return item;
	}

	public static @Nullable Item byId(final int id) {
		return REGISTRY.getOrDefault(id, null);
	}
}
