package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.data.Item;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public final class BetaItems {
	private static final Set<Item> REGISTRY = new HashSet<>();

	public static final Item IRON_SHOVEL = registerItem(0);
	public static final Item IRON_PICKAXE = registerItem(1);
	public static final Item IRON_AXE = registerItem(2);
	public static final Item FLINT_AND_STEEL = registerItem(3);
	public static final Item APPLE = registerItem(4);
	public static final Item BOW = registerItem(5);
	public static final Item ARROW = registerItem(6);
	public static final Item COAL = registerItem(7);
	public static final Item DIAMOND = registerItem(8);
	public static final Item IRON_INGOT = registerItem(9);
	public static final Item GOLD_INGOT = registerItem(10);
	public static final Item IRON_SWORD = registerItem(11);
	public static final Item WOODEN_SWORD = registerItem(12);
	public static final Item WOODEN_SHOVEL = registerItem(13);
	public static final Item WOODEN_PICKAXE = registerItem(14);
	public static final Item WOODEN_AXE = registerItem(15);
	public static final Item STONE_SWORD = registerItem(16);
	public static final Item STONE_SHOVEL = registerItem(17);
	public static final Item STONE_PICKAXE = registerItem(18);
	public static final Item STONE_AXE = registerItem(19);
	public static final Item DIAMOND_SWORD = registerItem(20);
	public static final Item DIAMOND_SHOVEL = registerItem(21);
	public static final Item DIAMOND_PICKAXE = registerItem(22);
	public static final Item DIAMOND_AXE = registerItem(23);
	public static final Item STICK = registerItem(24);
	public static final Item BOWL = registerItem(25);
	public static final Item STEW = registerItem(26);
	public static final Item GOLDEN_SWORD = registerItem(27);
	public static final Item GOLDEN_SHOVEL = registerItem(28);
	public static final Item GOLDEN_PICKAXE = registerItem(29);
	public static final Item GOLDEN_AXE = registerItem(30);
	public static final Item STRING = registerItem(31);
	public static final Item FEATHER = registerItem(32);
	public static final Item GUN_POWDER = registerItem(33);
	public static final Item WOODEN_HOE = registerItem(34);
	public static final Item STONE_HOE = registerItem(35);
	public static final Item IRON_HOE = registerItem(36);
	public static final Item DIAMOND_HOE = registerItem(37);
	public static final Item GOLDEN_HOE = registerItem(38);
	public static final Item SEEDS = registerItem(39);
	public static final Item WHEAT = registerItem(40);
	public static final Item BREAD = registerItem(41);
	public static final Item LEATHER_HELMET = registerItem(42);
	public static final Item LEATHER_CHESTPLATE = registerItem(43);
	public static final Item LEATHER_LEGGINGS = registerItem(44);
	public static final Item LEATHER_BOOTS = registerItem(45);
	public static final Item CHAIN_HELMET = registerItem(46);
	public static final Item CHAIN_CHESTPLATE = registerItem(47);
	public static final Item CHAIN_LEGGINGS = registerItem(48);
	public static final Item CHAIN_BOOTS = registerItem(49);
	public static final Item IRON_HELMET = registerItem(50);
	public static final Item IRON_CHESTPLATE = registerItem(51);
	public static final Item IRON_LEGGINGS = registerItem(52);
	public static final Item IRON_BOOTS = registerItem(53);
	public static final Item DIAMOND_HELMET = registerItem(54);
	public static final Item DIAMOND_CHESTPLATE = registerItem(55);
	public static final Item DIAMOND_LEGGINGS = registerItem(56);
	public static final Item DIAMOND_BOOTS = registerItem(57);
	public static final Item GOLDEN_HELMET = registerItem(58);
	public static final Item GOLDEN_CHESTPLATE = registerItem(59);
	public static final Item GOLDEN_LEGGINGS = registerItem(60);
	public static final Item GOLDEN_BOOTS = registerItem(61);
	public static final Item FLINT = registerItem(62);
	public static final Item RAW_PORKCHOP = registerItem(63);
	public static final Item COOKED_PORKCHOP = registerItem(64);
	public static final Item PAINTING = registerItem(65);
	public static final Item GOLDEN_APPLE = registerItem(66);
	public static final Item SIGN = registerItem(67);
	public static final Item WOODEN_DOOR = registerItem(68);
	public static final Item BUCKET = registerItem(69);
	public static final Item WATER_BUCKET = registerItem(70);
	public static final Item LAVA_BUCKET = registerItem(71);
	public static final Item MINECART = registerItem(72);
	public static final Item SADDLE = registerItem(73);
	public static final Item IRON_DOOR = registerItem(74);
	public static final Item REDSTONE = registerItem(75);
	public static final Item SNOWBALL = registerItem(76);
	public static final Item BOAT = registerItem(77);
	public static final Item LEATHER = registerItem(78);
	public static final Item MILK_BUCKET = registerItem(79);
	public static final Item BRICK = registerItem(80);
	public static final Item CLAY = registerItem(81);
	public static final Item SUGAR_CANE = registerItem(82);
	public static final Item PAPER = registerItem(83);
	public static final Item BOOK = registerItem(84);
	public static final Item SLIMEBALL = registerItem(85);
	public static final Item CHEST_MINECART = registerItem(86);
	public static final Item FURNACE_MINECART = registerItem(87);
	public static final Item EGG = registerItem(88);
	public static final Item COMPASS = registerItem(89);
	public static final Item FISHING_ROD = registerItem(90);
	public static final Item CLOCK = registerItem(91);
	public static final Item GLOWSTONE_DUST = registerItem(92);
	public static final Item RAW_FISH = registerItem(93);
	public static final Item COOKED_FISH = registerItem(94);
	public static final Item DYE_POWDER = registerItem(95);
	public static final Item BONE = registerItem(96);
	public static final Item SUGAR = registerItem(97);
	public static final Item CAKE = registerItem(98);
	public static final Item BED = registerItem(99);
	public static final Item REDSTONE_REPEATER = registerItem(100);
	public static final Item COOKIE = registerItem(101);
	public static final Item MAP = registerItem(102);
	public static final Item SHEARS = registerItem(103);
	public static final Item MUSIC_DISC_13 = registerItem(2000);
	public static final Item MUSIC_DISC_CAT = registerItem(2001);

	public static void bootstrap() {
	}

	public static Item registerItem(final int id) {
		final Item item = new Item(256 + id);
		REGISTRY.add(item);
		return item;
	}

	public static Item registerItem(final Item item) {
		REGISTRY.add(item);
		return item;
	}

	public static @Nullable Item byId(final int id) {
		return REGISTRY.stream().filter(it -> it.id() == id).findFirst().orElse(null);
	}
}
