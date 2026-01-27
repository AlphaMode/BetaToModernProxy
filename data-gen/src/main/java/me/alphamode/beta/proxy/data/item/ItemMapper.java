package me.alphamode.beta.proxy.data.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.nio.file.Path;

public class ItemMapper {
	private static void put(final int betaId, final CompoundTag tag, final ItemFactory factory) {
		tag.putInt(String.valueOf(betaId), Item.getId(factory.getItem()));
	}

	private static ItemFactory item(final Item item) {
		return () -> item;
	}

	private static ItemFactory planks() {
		// TODO
		return () -> Items.OAK_PLANKS;
	}

	private static ItemFactory sapling() {
		// TODO
		return () -> Items.OAK_SAPLING;
	}

	private static ItemFactory logs() {
		// TODO
		return () -> Items.OAK_LOG;
	}

	private static ItemFactory leaves() {
		// TODO
		return () -> Items.OAK_LEAVES;
	}

	private static ItemFactory wool() {
		// TODO
		return () -> Items.WHITE_WOOL;
	}

	private static ItemFactory stairs() {
		// TODO
		return () -> Items.OAK_STAIRS;
	}

	private static ItemFactory slabs() {
		// TODO
		return () -> Items.OAK_SLAB;
	}

	private static ItemFactory farmland() {
		// TODO
		return () -> Items.FARMLAND;
	}

	private static ItemFactory furnace() {
		// TODO
		return () -> Items.FURNACE;
	}

	private static ItemFactory button() {
		// TODO
		return () -> Items.OAK_BUTTON;
	}

	private static ItemFactory fence() {
		// TODO
		return () -> Items.OAK_FENCE;
	}

	private static ItemFactory dye() {
		// TODO
		return () -> Items.WHITE_DYE;
	}

	public static void writeItems(final Path outputPath) {
		final CompoundTag tag = new CompoundTag();
		put(BetaItems.STONE, tag, item(Items.STONE));
		put(BetaItems.GRASS, tag, item(Items.GRASS_BLOCK));
		put(BetaItems.DIRT, tag, item(Items.DIRT));
		put(BetaItems.COBBLESTONE, tag, item(Items.COBBLESTONE));
		put(BetaItems.PLANKS, tag, planks());
		put(BetaItems.SAPLING, tag, sapling());
		put(BetaItems.BEDROCK, tag, item(Items.BEDROCK));
		put(BetaItems.FLOWING_WATER, tag, item(Items.WATER_BUCKET));
		put(BetaItems.STILL_WATER, tag, item(Items.WATER_BUCKET));
		put(BetaItems.FLOWING_LAVA, tag, item(Items.LAVA_BUCKET));
		put(BetaItems.STILL_LAVA, tag, item(Items.LAVA_BUCKET));
		put(BetaItems.SAND, tag, item(Items.SAND));
		put(BetaItems.GRAVEL, tag, item(Items.GRAVEL));
		put(BetaItems.GOLDEN_ORE, tag, item(Items.GOLD_ORE));
		put(BetaItems.IRON_ORE, tag, item(Items.IRON_ORE));
		put(BetaItems.COAL_ORE, tag, item(Items.COAL_ORE));
		put(BetaItems.WOOD, tag, logs());
		put(BetaItems.LEAVES, tag, leaves());
		put(BetaItems.SPONGE, tag, item(Items.SPONGE));
		put(BetaItems.GLASS, tag, item(Items.GLASS));
		put(BetaItems.LAPIS_ORE, tag, item(Items.LAPIS_ORE));
		put(BetaItems.LAPIS_BLOCK, tag, item(Items.LAPIS_BLOCK));
		put(BetaItems.DISPENSER, tag, item(Items.DISPENSER));
		put(BetaItems.SANDSTONE, tag, item(Items.SANDSTONE));
		put(BetaItems.NOTEBLOCk, tag, item(Items.NOTE_BLOCK));
		put(BetaItems.BED, tag, item(Items.RED_BED));
		put(BetaItems.POWERED_RAIL, tag, item(Items.POWERED_RAIL));
		put(BetaItems.DETECTOR_RAIL, tag, item(Items.DETECTOR_RAIL));
		put(BetaItems.STICKY_PISTON, tag, item(Items.STICKY_PISTON));
		put(BetaItems.WEB, tag, item(Items.COBWEB));
		put(BetaItems.TALL_GRASS, tag, item(Items.TALL_GRASS));
		put(BetaItems.DEAD_BUSH, tag, item(Items.DEAD_BUSH));
		put(BetaItems.PISTON, tag, item(Items.PISTON));
		put(BetaItems.PISTON_EXTENDED, tag, item(Items.PISTON));
		put(BetaItems.CLOTH, tag, wool());
		put(BetaItems.MOVING_PISTON, tag, item(Items.PISTON));
		put(BetaItems.YELLOW_PLANT, tag, item(Items.DANDELION));
		put(BetaItems.POPPY, tag, item(Items.POPPY));
		put(BetaItems.BROWN_MUSHROOM, tag, item(Items.BROWN_MUSHROOM));
		put(BetaItems.RED_MUSHROOM, tag, item(Items.RED_MUSHROOM));
		put(BetaItems.GOLDEN_BLOCK, tag, item(Items.GOLD_BLOCK));
		put(BetaItems.IRON_BLOCK, tag, item(Items.IRON_BLOCK));
		put(BetaItems.DOUBLE_STAIR, tag, stairs());
		put(BetaItems.HALF_SLAB, tag, slabs());
		put(BetaItems.BRICK, tag, item(Items.BRICKS));
		put(BetaItems.TNT, tag, item(Items.TNT));
		put(BetaItems.BOOKSHELF, tag, item(Items.BOOKSHELF));
		put(BetaItems.MOSSY_COBBLESTONE, tag, item(Items.MOSSY_COBBLESTONE));
		put(BetaItems.OBSIDIAN, tag, item(Items.OBSIDIAN));
		put(BetaItems.TORCH, tag, item(Items.TORCH));
		put(BetaItems.FIRE, tag, item(Items.FLINT_AND_STEEL));
		put(BetaItems.MOB_SPAWNER, tag, item(Items.SPAWNER));
		put(BetaItems.OAK_STAIRS, tag, item(Items.OAK_STAIRS));
		put(BetaItems.CHEST, tag, item(Items.CHEST));
		put(BetaItems.REDSTONE_DUST, tag, item(Items.REDSTONE));
		put(BetaItems.DIAMOND_ORE, tag, item(Items.DIAMOND_ORE));
		put(BetaItems.DIAMOND_BLOCK, tag, item(Items.DIAMOND_BLOCK));
		put(BetaItems.CRAFTING_TABLE, tag, item(Items.CRAFTING_TABLE));
		put(BetaItems.CROPS, tag, item(Items.WHEAT_SEEDS));
		put(BetaItems.TILLED_FARMLAND, tag, farmland());
		put(BetaItems.FURNACE, tag, item(Items.FURNACE));
		put(BetaItems.FURNACE_ACTIVE, tag, furnace());
		put(BetaItems.SIGN, tag, item(Items.OAK_SIGN));
		put(BetaItems.OAK_DOOR, tag, item(Items.OAK_DOOR));
		put(BetaItems.LADDER, tag, item(Items.LADDER));
		put(BetaItems.RAIL, tag, item(Items.RAIL));
		put(BetaItems.COBBLESTONE_STAIRS, tag, item(Items.COBBLESTONE_STAIRS));
		put(BetaItems.WALL_SIGN, tag, item(Items.OAK_SIGN));
		put(BetaItems.LEVER, tag, item(Items.LEVER));
		put(BetaItems.STONE_PRESSURE_PLATE, tag, item(Items.STONE_PRESSURE_PLATE));
		put(BetaItems.IRON_DOOR, tag, item(Items.IRON_DOOR));
		put(BetaItems.OAK_PRESSURE_PLATE, tag, item(Items.OAK_PRESSURE_PLATE));
		put(BetaItems.REDSTONE_ORE, tag, item(Items.REDSTONE_ORE));
		put(BetaItems.REDSTONE_ORE_ACTIVE, tag, item(Items.REDSTONE_ORE));
		put(BetaItems.REDSTONE_TORCH, tag, item(Items.REDSTONE_TORCH));
		put(BetaItems.REDSTONE_TORCH_ACTIVE, tag, item(Items.REDSTONE_TORCH));
		put(BetaItems.BUTTON, tag, button());
		put(BetaItems.SNOW, tag, item(Items.SNOW));
		put(BetaItems.ICE, tag, item(Items.ICE));
		put(BetaItems.SNOW_BLOCK, tag, item(Items.SNOW_BLOCK));
		put(BetaItems.CACTUS, tag, item(Items.CACTUS));
		put(BetaItems.CLAY, tag, item(Items.CLAY));
		put(BetaItems.REED, tag, item(Items.SUGAR_CANE));
		put(BetaItems.JUKEBOX, tag, item(Items.JUKEBOX));
		put(BetaItems.FENCE, tag, fence());
		put(BetaItems.PUMPKIN, tag, item(Items.PUMPKIN));
		put(BetaItems.NETHERRACK, tag, item(Items.NETHERRACK));
		put(BetaItems.SOUL_SAND, tag, item(Items.SOUL_SAND));
		put(BetaItems.GLOWSTONE, tag, item(Items.GLOWSTONE));
		put(BetaItems.PORTAL, tag, item(Items.PURPLE_WOOL));
		put(BetaItems.LANTERN, tag, item(Items.LANTERN));
		put(BetaItems.CAKE, tag, item(Items.CAKE));
		put(BetaItems.REDSTONE_REPEATER, tag, item(Items.REPEATER));
		put(BetaItems.REDSTONE_REPEATER_ACTIVE, tag, item(Items.REPEATER));
		put(BetaItems.LOCKED_CHEST, tag, item(Items.CHEST));
		put(BetaItems.TRAPDOOR, tag, item(Items.OAK_TRAPDOOR)); // todo: was there iron?
		put(BetaItems.IRON_SHOVEL, tag, item(Items.IRON_SHOVEL));
		put(BetaItems.IRON_PICKAXE, tag, item(Items.IRON_PICKAXE));
		put(BetaItems.IRON_AXE, tag, item(Items.IRON_AXE));
		put(BetaItems.FLINT_AND_STEEL, tag, item(Items.FLINT_AND_STEEL));
		put(BetaItems.APPLE, tag, item(Items.APPLE));
		put(BetaItems.BOW, tag, item(Items.BOW));
		put(BetaItems.ARROW, tag, item(Items.ARROW));
		put(BetaItems.COAL, tag, item(Items.COAL));
		put(BetaItems.DIAMOND, tag, item(Items.DIAMOND));
		put(BetaItems.IRON_INGOT, tag, item(Items.IRON_INGOT));
		put(BetaItems.GOLD_INGOT, tag, item(Items.GOLD_INGOT));
		put(BetaItems.IRON_SWORD, tag, item(Items.IRON_SWORD));
		put(BetaItems.WOODEN_SWORD, tag, item(Items.WOODEN_SWORD));
		put(BetaItems.WOODEN_SHOVEL, tag, item(Items.WOODEN_SHOVEL));
		put(BetaItems.WOODEN_PICKAXE, tag, item(Items.WOODEN_PICKAXE));
		put(BetaItems.WOODEN_AXE, tag, item(Items.WOODEN_AXE));
		put(BetaItems.STONE_SWORD, tag, item(Items.STONE_SWORD));
		put(BetaItems.STONE_SHOVEL, tag, item(Items.STONE_SHOVEL));
		put(BetaItems.STONE_PICKAXE, tag, item(Items.STONE_PICKAXE));
		put(BetaItems.STONE_AXE, tag, item(Items.STONE_AXE));
		put(BetaItems.DIAMOND_SWORD, tag, item(Items.DIAMOND_SWORD));
		put(BetaItems.DIAMOND_SHOVEL, tag, item(Items.DIAMOND_SHOVEL));
		put(BetaItems.DIAMOND_PICKAXE, tag, item(Items.DIAMOND_PICKAXE));
		put(BetaItems.DIAMOND_AXE, tag, item(Items.DIAMOND_AXE));
		put(BetaItems.STICK, tag, item(Items.STICK));
		put(BetaItems.BOWL, tag, item(Items.BOWL));
		put(BetaItems.STEW, tag, item(Items.MUSHROOM_STEW));
		put(BetaItems.GOLDEN_SWORD, tag, item(Items.GOLDEN_SWORD));
		put(BetaItems.GOLDEN_SHOVEL, tag, item(Items.GOLDEN_SHOVEL));
		put(BetaItems.GOLDEN_PICKAXE, tag, item(Items.GOLDEN_PICKAXE));
		put(BetaItems.GOLDEN_AXE, tag, item(Items.GOLDEN_AXE));
		put(BetaItems.STRING, tag, item(Items.STRING));
		put(BetaItems.FEATHER, tag, item(Items.FEATHER));
		put(BetaItems.GUN_POWDER, tag, item(Items.GUNPOWDER));
		put(BetaItems.WOODEN_HOE, tag, item(Items.WOODEN_HOE));
		put(BetaItems.STONE_HOE, tag, item(Items.STONE_HOE));
		put(BetaItems.IRON_HOE, tag, item(Items.IRON_HOE));
		put(BetaItems.DIAMOND_HOE, tag, item(Items.DIAMOND_HOE));
		put(BetaItems.GOLDEN_HOE, tag, item(Items.GOLDEN_HOE));
		put(BetaItems.SEEDS, tag, item(Items.WHEAT_SEEDS));
		put(BetaItems.WHEAT, tag, item(Items.WHEAT));
		put(BetaItems.BREAD, tag, item(Items.BREAD));
		put(BetaItems.LEATHER_HELMET, tag, item(Items.LEATHER_HELMET));
		put(BetaItems.LEATHER_CHESTPLATE, tag, item(Items.LEATHER_CHESTPLATE));
		put(BetaItems.LEATHER_LEGGINGS, tag, item(Items.LEATHER_LEGGINGS));
		put(BetaItems.LEATHER_BOOTS, tag, item(Items.LEATHER_BOOTS));
		put(BetaItems.CHAIN_HELMET, tag, item(Items.CHAINMAIL_HELMET));
		put(BetaItems.CHAIN_CHESTPLATE, tag, item(Items.CHAINMAIL_CHESTPLATE));
		put(BetaItems.CHAIN_LEGGINGS, tag, item(Items.CHAINMAIL_LEGGINGS));
		put(BetaItems.CHAIN_BOOTS, tag, item(Items.CHAINMAIL_BOOTS));
		put(BetaItems.IRON_HELMET, tag, item(Items.IRON_HELMET));
		put(BetaItems.IRON_CHESTPLATE, tag, item(Items.IRON_CHESTPLATE));
		put(BetaItems.IRON_LEGGINGS, tag, item(Items.IRON_LEGGINGS));
		put(BetaItems.IRON_BOOTS, tag, item(Items.IRON_BOOTS));
		put(BetaItems.DIAMOND_HELMET, tag, item(Items.DIAMOND_HELMET));
		put(BetaItems.DIAMOND_CHESTPLATE, tag, item(Items.DIAMOND_CHESTPLATE));
		put(BetaItems.DIAMOND_LEGGINGS, tag, item(Items.DIAMOND_LEGGINGS));
		put(BetaItems.DIAMOND_BOOTS, tag, item(Items.DIAMOND_BOOTS));
		put(BetaItems.GOLDEN_HELMET, tag, item(Items.GOLDEN_HELMET));
		put(BetaItems.GOLDEN_CHESTPLATE, tag, item(Items.GOLDEN_CHESTPLATE));
		put(BetaItems.GOLDEN_LEGGINGS, tag, item(Items.GOLDEN_LEGGINGS));
		put(BetaItems.GOLDEN_BOOTS, tag, item(Items.GOLDEN_BOOTS));
		put(BetaItems.FLINT, tag, item(Items.FLINT));
		put(BetaItems.RAW_PORKCHOP, tag, item(Items.PORKCHOP));
		put(BetaItems.COOKED_PORKCHOP, tag, item(Items.COOKED_PORKCHOP));
		put(BetaItems.PAINTING, tag, item(Items.PAINTING));
		put(BetaItems.GOLDEN_APPLE, tag, item(Items.GOLDEN_APPLE));
		put(BetaItems.SIGN_ITEM, tag, item(Items.OAK_SIGN));
		put(BetaItems.WOODEN_DOOR, tag, item(Items.OAK_DOOR));
		put(BetaItems.BUCKET, tag, item(Items.BUCKET));
		put(BetaItems.WATER_BUCKET, tag, item(Items.WATER_BUCKET));
		put(BetaItems.LAVA_BUCKET, tag, item(Items.LAVA_BUCKET));
		put(BetaItems.MINECART, tag, item(Items.MINECART));
		put(BetaItems.SADDLE, tag, item(Items.SADDLE));
		put(BetaItems.IRON_DOOR_ITEM, tag, item(Items.IRON_DOOR));
		put(BetaItems.REDSTONE, tag, item(Items.REDSTONE));
		put(BetaItems.SNOWBALL, tag, item(Items.SNOWBALL));
		put(BetaItems.BOAT, tag, item(Items.OAK_BOAT));
		put(BetaItems.LEATHER, tag, item(Items.LEATHER));
		put(BetaItems.MILK_BUCKET, tag, item(Items.MILK_BUCKET));
		put(BetaItems.BRICK_ITEM, tag, item(Items.BRICK));
		put(BetaItems.CLAY_ITEM, tag, item(Items.CLAY_BALL));
		put(BetaItems.SUGAR_CANE, tag, item(Items.SUGAR_CANE));
		put(BetaItems.PAPER, tag, item(Items.PAPER));
		put(BetaItems.BOOK, tag, item(Items.BOOK));
		put(BetaItems.SLIMEBALL, tag, item(Items.SLIME_BALL));
		put(BetaItems.CHEST_MINECART, tag, item(Items.CHEST_MINECART));
		put(BetaItems.FURNACE_MINECART, tag, item(Items.FURNACE_MINECART));
		put(BetaItems.EGG, tag, item(Items.EGG));
		put(BetaItems.COMPASS, tag, item(Items.COMPASS));
		put(BetaItems.FISHING_ROD, tag, item(Items.FISHING_ROD));
		put(BetaItems.CLOCK, tag, item(Items.CLOCK));
		put(BetaItems.GLOWSTONE_DUST, tag, item(Items.GLOWSTONE_DUST));
		put(BetaItems.RAW_FISH, tag, item(Items.COD));
		put(BetaItems.COOKED_FISH, tag, item(Items.COOKED_COD));
		put(BetaItems.DYE_POWDER, tag, dye());
		put(BetaItems.BONE, tag, item(Items.BONE));
		put(BetaItems.SUGAR, tag, item(Items.SUGAR));
		put(BetaItems.CAKE_ITEM, tag, item(Items.CAKE));
		put(BetaItems.BED_ITEM, tag, item(Items.RED_BED));
		put(BetaItems.REDSTONE_REPEATER_ITEM, tag, item(Items.REPEATER));
		put(BetaItems.COOKIE, tag, item(Items.COOKIE));
		put(BetaItems.MAP, tag, item(Items.MAP));
		put(BetaItems.SHEARS, tag, item(Items.SHEARS));
		put(BetaItems.MUSIC_DISC_13, tag, item(Items.MUSIC_DISC_13));
		put(BetaItems.MUSIC_DISC_CAT, tag, item(Items.MUSIC_DISC_CAT));
		try {
			NbtIo.writeCompressed(tag, outputPath);
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}

	@FunctionalInterface
	public interface ItemFactory {
		Item getItem();
	}
}
