package me.alphamode.beta.proxy.data.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.nio.file.Path;

public class ItemMapper {
	private static void put(final int betaId, final CompoundTag tag, final ItemFactory factory) {
		tag.put(String.valueOf(betaId), new IntTag(Item.getId(factory.getItem())));
	}

	private static ItemFactory item(final Item item) {
		return () -> item;
	}

	private static ItemFactory map(final int id, final ItemFactory factory) {
		return factory;
	}

	public static void writeItems(final Path outputPath) {
		final CompoundTag tag = new CompoundTag();
		put(ItemsStuff.STONE, tag, item(Items.STONE));
		put(ItemsStuff.GRASS, tag, item(Items.GRASS_BLOCK));
		put(ItemsStuff.DIRT, tag, item(Items.DIRT));
		put(ItemsStuff.COBBLESTONE, tag, item(Items.COBBLESTONE));
		put(ItemsStuff.PLANKS, tag, item(Items.OAK_PLANKS));  // TODO
		put(ItemsStuff.SAPLING, tag, item(Items.OAK_SAPLING));  // TODO
		put(ItemsStuff.BEDROCK, tag, item(Items.BEDROCK));
		put(ItemsStuff.FLOWING_WATER, tag, item(Items.WATER_BUCKET));
		put(ItemsStuff.STILL_WATER, tag, item(Items.WATER_BUCKET));
		put(ItemsStuff.FLOWING_LAVA, tag, item(Items.LAVA_BUCKET));
		put(ItemsStuff.STILL_LAVA, tag, item(Items.LAVA_BUCKET));
		put(ItemsStuff.SAND, tag, item(Items.SAND));
		put(ItemsStuff.GRAVEL, tag, item(Items.GRAVEL));
		put(ItemsStuff.GOLDEN_ORE, tag, item(Items.GOLD_ORE));
		put(ItemsStuff.IRON_ORE, tag, item(Items.IRON_ORE));
		put(ItemsStuff.COAL_ORE, tag, item(Items.COAL_ORE));
		put(ItemsStuff.WOOD, tag, item(Items.OAK_LOG)); // TODO
		put(ItemsStuff.LEAVES, tag, item(Items.OAK_LEAVES)); // TODO
		put(ItemsStuff.SPONGE, tag, item(Items.SPONGE));
		put(ItemsStuff.GLASS, tag, item(Items.GLASS));
		put(ItemsStuff.LAPIS_ORE, tag, item(Items.LAPIS_ORE));
		put(ItemsStuff.LAPIS_BLOCK, tag, item(Items.LAPIS_BLOCK));
		put(ItemsStuff.DISPENSER, tag, item(Items.DISPENSER));
		put(ItemsStuff.SANDSTONE, tag, item(Items.SANDSTONE));
		put(ItemsStuff.NOTEBLOCk, tag, item(Items.NOTE_BLOCK));
		put(ItemsStuff.BED, tag, item(Items.RED_BED));
		put(ItemsStuff.POWERED_RAIL, tag, item(Items.POWERED_RAIL));
		put(ItemsStuff.DETECTOR_RAIL, tag, item(Items.DETECTOR_RAIL));
		put(ItemsStuff.STICKY_PISTON, tag, item(Items.STICKY_PISTON));
		put(ItemsStuff.WEB, tag, item(Items.COBWEB));
		put(ItemsStuff.TALL_GRASS, tag, item(Items.TALL_GRASS));
		put(ItemsStuff.DEAD_BUSH, tag, item(Items.DEAD_BUSH));
		put(ItemsStuff.PISTON, tag, item(Items.PISTON));
		put(ItemsStuff.PISTON_EXTENDED, tag, item(Items.PISTON));
		put(ItemsStuff.CLOTH, tag, item(Items.WHITE_WOOL)); // TODO
		put(ItemsStuff.MOVING_PISTON, tag, item(Items.PISTON));
		put(ItemsStuff.YELLOW_PLANT, tag, item(Items.DANDELION));
		put(ItemsStuff.POPPY, tag, item(Items.POPPY));
		put(ItemsStuff.BROWN_MUSHROOM, tag, item(Items.BROWN_MUSHROOM));
		put(ItemsStuff.RED_MUSHROOM, tag, item(Items.RED_MUSHROOM));
		put(ItemsStuff.GOLDEN_BLOCK, tag, item(Items.GOLD_BLOCK));
		put(ItemsStuff.IRON_BLOCK, tag, item(Items.IRON_BLOCK));
		put(ItemsStuff.DOUBLE_STAIR, tag, item(Items.OAK_STAIRS)); // TODO
		put(ItemsStuff.SINGLE_STAIR, tag, item(Items.OAK_STAIRS)); // TODO
		put(ItemsStuff.BRICK, tag, item(Items.BRICKS));
		put(ItemsStuff.TNT, tag, item(Items.TNT));
		put(ItemsStuff.BOOKSHELF, tag, item(Items.BOOKSHELF));
		put(ItemsStuff.MOSSY_COBBLESTONE, tag, item(Items.MOSSY_COBBLESTONE));
		put(ItemsStuff.OBSIDIAN, tag, item(Items.OBSIDIAN));
		put(ItemsStuff.TORCH, tag, item(Items.TORCH));
		put(ItemsStuff.FIRE, tag, item(Items.FLINT_AND_STEEL));
		put(ItemsStuff.MOB_SPAWNER, tag, item(Items.SPAWNER));
		put(ItemsStuff.OAK_STAIRS, tag, item(Items.OAK_STAIRS));
		put(ItemsStuff.CHEST, tag, item(Items.CHEST));
		put(ItemsStuff.REDSTONE_DUST, tag, item(Items.REDSTONE));
		put(ItemsStuff.DIAMOND_ORE, tag, item(Items.DIAMOND_ORE));
		put(ItemsStuff.DIAMOND_BLOCK, tag, item(Items.DIAMOND_BLOCK));
		put(ItemsStuff.CRAFTING_TABLE, tag, item(Items.CRAFTING_TABLE));
		put(ItemsStuff.CROPS, tag, item(Items.WHEAT_SEEDS));
		put(ItemsStuff.TILLED_FARMLAND, tag, item(Items.FARMLAND)); // TODO (tilled)
		put(ItemsStuff.FURNACE, tag, item(Items.FURNACE));
		put(ItemsStuff.FURNACE_ACTIVE, tag, item(Items.FURNACE)); // todo (active)
		put(ItemsStuff.SIGN, tag, item(Items.OAK_SIGN));
		put(ItemsStuff.OAK_DOOR, tag, item(Items.OAK_DOOR));
		put(ItemsStuff.LADDER, tag, item(Items.LADDER));
		put(ItemsStuff.RAIL, tag, item(Items.RAIL));
		put(ItemsStuff.COBBLESTONE_STAIRS, tag, item(Items.COBBLESTONE_STAIRS));
		put(ItemsStuff.WALL_SIGN, tag, item(Items.OAK_SIGN));
		put(ItemsStuff.LEVER, tag, item(Items.LEVER));
		put(ItemsStuff.STONE_PRESSURE_PLATE, tag, item(Items.STONE_PRESSURE_PLATE));
		put(ItemsStuff.IRON_DOOR, tag, item(Items.IRON_DOOR));
		put(ItemsStuff.OAK_PRESSURE_PLATE, tag, item(Items.OAK_PRESSURE_PLATE));
		put(ItemsStuff.REDSTONE_ORE, tag, item(Items.REDSTONE_ORE));
		put(ItemsStuff.REDSTONE_ORE_ACTIVE, tag, item(Items.REDSTONE_ORE));
		put(ItemsStuff.REDSTONE_TORCH, tag, item(Items.REDSTONE_TORCH));
		put(ItemsStuff.REDSTONE_TORCH_ACTIVE, tag, item(Items.REDSTONE_TORCH));
		put(ItemsStuff.BUTTON, tag, item(Items.OAK_BUTTON)); // todo?
		put(ItemsStuff.SNOW, tag, item(Items.SNOW));
		put(ItemsStuff.ICE, tag, item(Items.ICE));
		put(ItemsStuff.SNOW_BLOCK, tag, item(Items.SNOW_BLOCK));
		put(ItemsStuff.CACTUS, tag, item(Items.CACTUS));
		put(ItemsStuff.CLAY, tag, item(Items.CLAY));
		put(ItemsStuff.REED, tag, item(Items.SUGAR_CANE));
		put(ItemsStuff.JUKEBOX, tag, item(Items.JUKEBOX));
		put(ItemsStuff.FENCE, tag, item(Items.OAK_FENCE)); // todo
		put(ItemsStuff.PUMPKIN, tag, item(Items.PUMPKIN));
		put(ItemsStuff.NETHERRACK, tag, item(Items.NETHERRACK));
		put(ItemsStuff.SOUL_SAND, tag, item(Items.SOUL_SAND));
		put(ItemsStuff.GLOWSTONE, tag, item(Items.GLOWSTONE));
		put(ItemsStuff.PORTAL, tag, item(Items.PURPLE_WOOL)); // todo
		put(ItemsStuff.LANTERN, tag, item(Items.LANTERN));
		put(ItemsStuff.CAKE, tag, item(Items.CAKE));
		put(ItemsStuff.REDSTONE_REPEATER, tag, item(Items.REPEATER));
		put(ItemsStuff.REDSTONE_REPEATER_ACTIVE, tag, item(Items.REPEATER)); // TODO: active
		put(ItemsStuff.LOCKED_CHEST, tag, item(Items.CHEST));
		put(ItemsStuff.TRAPDOOR, tag, item(Items.OAK_TRAPDOOR)); // todo: was there iron?
		put(ItemsStuff.IRON_SHOVEL, tag, item(Items.IRON_SHOVEL));
		put(ItemsStuff.IRON_PICKAXE, tag, item(Items.IRON_PICKAXE));
		put(ItemsStuff.IRON_AXE, tag, item(Items.IRON_AXE));
		put(ItemsStuff.FLINT_AND_STEEL, tag, item(Items.FLINT_AND_STEEL));
		put(ItemsStuff.APPLE, tag, item(Items.APPLE));
		put(ItemsStuff.BOW, tag, item(Items.BOW));
		put(ItemsStuff.ARROW, tag, item(Items.ARROW));
		put(ItemsStuff.COAL, tag, item(Items.COAL));
		put(ItemsStuff.DIAMOND, tag, item(Items.DIAMOND));
		put(ItemsStuff.IRON_INGOT, tag, item(Items.IRON_INGOT));
		put(ItemsStuff.GOLD_INGOT, tag, item(Items.GOLD_INGOT));
		put(ItemsStuff.IRON_SWORD, tag, item(Items.IRON_SWORD));
		put(ItemsStuff.WOODEN_SWORD, tag, item(Items.WOODEN_SWORD));
		put(ItemsStuff.WOODEN_SHOVEL, tag, item(Items.WOODEN_SHOVEL));
		put(ItemsStuff.WOODEN_PICKAXE, tag, item(Items.WOODEN_PICKAXE));
		put(ItemsStuff.WOODEN_AXE, tag, item(Items.WOODEN_AXE));
		put(ItemsStuff.STONE_SWORD, tag, item(Items.STONE_SWORD));
		put(ItemsStuff.STONE_SHOVEL, tag, item(Items.STONE_SHOVEL));
		put(ItemsStuff.STONE_PICKAXE, tag, item(Items.STONE_PICKAXE));
		put(ItemsStuff.STONE_AXE, tag, item(Items.STONE_AXE));
		put(ItemsStuff.DIAMOND_SWORD, tag, item(Items.DIAMOND_SWORD));
		put(ItemsStuff.DIAMOND_SHOVEL, tag, item(Items.DIAMOND_SHOVEL));
		put(ItemsStuff.DIAMOND_PICKAXE, tag, item(Items.DIAMOND_PICKAXE));
		put(ItemsStuff.DIAMOND_AXE, tag, item(Items.DIAMOND_AXE));
		put(ItemsStuff.STICK, tag, item(Items.STICK));
		put(ItemsStuff.BOWL, tag, item(Items.BOWL));
		put(ItemsStuff.STEW, tag, item(Items.MUSHROOM_STEW));
		put(ItemsStuff.GOLDEN_SWORD, tag, item(Items.GOLDEN_SWORD));
		put(ItemsStuff.GOLDEN_SHOVEL, tag, item(Items.GOLDEN_SHOVEL));
		put(ItemsStuff.GOLDEN_PICKAXE, tag, item(Items.GOLDEN_PICKAXE));
		put(ItemsStuff.GOLDEN_AXE, tag, item(Items.GOLDEN_AXE));
		put(ItemsStuff.STRING, tag, item(Items.STRING));
		put(ItemsStuff.FEATHER, tag, item(Items.FEATHER));
		put(ItemsStuff.GUN_POWDER, tag, item(Items.GUNPOWDER));
		put(ItemsStuff.WOODEN_HOE, tag, item(Items.WOODEN_HOE));
		put(ItemsStuff.STONE_HOE, tag, item(Items.STONE_HOE));
		put(ItemsStuff.IRON_HOE, tag, item(Items.IRON_HOE));
		put(ItemsStuff.DIAMOND_HOE, tag, item(Items.DIAMOND_HOE));
		put(ItemsStuff.GOLDEN_HOE, tag, item(Items.GOLDEN_HOE));
		put(ItemsStuff.SEEDS, tag, item(Items.WHEAT_SEEDS));
		put(ItemsStuff.WHEAT, tag, item(Items.WHEAT));
		put(ItemsStuff.BREAD, tag, item(Items.BREAD));
		put(ItemsStuff.LEATHER_HELMET, tag, item(Items.LEATHER_HELMET));
		put(ItemsStuff.LEATHER_CHESTPLATE, tag, item(Items.LEATHER_CHESTPLATE));
		put(ItemsStuff.LEATHER_LEGGINGS, tag, item(Items.LEATHER_LEGGINGS));
		put(ItemsStuff.LEATHER_BOOTS, tag, item(Items.LEATHER_BOOTS));
		put(ItemsStuff.CHAIN_HELMET, tag, item(Items.CHAINMAIL_HELMET));
		put(ItemsStuff.CHAIN_CHESTPLATE, tag, item(Items.CHAINMAIL_CHESTPLATE));
		put(ItemsStuff.CHAIN_LEGGINGS, tag, item(Items.CHAINMAIL_LEGGINGS));
		put(ItemsStuff.CHAIN_BOOTS, tag, item(Items.CHAINMAIL_BOOTS));
		put(ItemsStuff.IRON_HELMET, tag, item(Items.IRON_HELMET));
		put(ItemsStuff.IRON_CHESTPLATE, tag, item(Items.IRON_CHESTPLATE));
		put(ItemsStuff.IRON_LEGGINGS, tag, item(Items.IRON_LEGGINGS));
		put(ItemsStuff.IRON_BOOTS, tag, item(Items.IRON_BOOTS));
		put(ItemsStuff.DIAMOND_HELMET, tag, item(Items.DIAMOND_HELMET));
		put(ItemsStuff.DIAMOND_CHESTPLATE, tag, item(Items.DIAMOND_CHESTPLATE));
		put(ItemsStuff.DIAMOND_LEGGINGS, tag, item(Items.DIAMOND_LEGGINGS));
		put(ItemsStuff.DIAMOND_BOOTS, tag, item(Items.DIAMOND_BOOTS));
		put(ItemsStuff.GOLDEN_HELMET, tag, item(Items.GOLDEN_HELMET));
		put(ItemsStuff.GOLDEN_CHESTPLATE, tag, item(Items.GOLDEN_CHESTPLATE));
		put(ItemsStuff.GOLDEN_LEGGINGS, tag, item(Items.GOLDEN_LEGGINGS));
		put(ItemsStuff.GOLDEN_BOOTS, tag, item(Items.GOLDEN_BOOTS));
		put(ItemsStuff.FLINT, tag, item(Items.FLINT));
		put(ItemsStuff.RAW_PORKCHOP, tag, item(Items.PORKCHOP));
		put(ItemsStuff.COOKED_PORKCHOP, tag, item(Items.COOKED_PORKCHOP));
		put(ItemsStuff.PAINTING, tag, item(Items.PAINTING));
		put(ItemsStuff.GOLDEN_APPLE, tag, item(Items.GOLDEN_APPLE));
		put(ItemsStuff.SIGN_ITEM, tag, item(Items.OAK_SIGN));
		put(ItemsStuff.WOODEN_DOOR, tag, item(Items.OAK_DOOR));
		put(ItemsStuff.BUCKET, tag, item(Items.BUCKET));
		put(ItemsStuff.WATER_BUCKET, tag, item(Items.WATER_BUCKET));
		put(ItemsStuff.LAVA_BUCKET, tag, item(Items.LAVA_BUCKET));
		put(ItemsStuff.MINECART, tag, item(Items.MINECART));
		put(ItemsStuff.SADDLE, tag, item(Items.SADDLE));
		put(ItemsStuff.IRON_DOOR_ITEM, tag, item(Items.IRON_DOOR));
		put(ItemsStuff.REDSTONE, tag, item(Items.REDSTONE));
		put(ItemsStuff.SNOWBALL, tag, item(Items.SNOWBALL));
		put(ItemsStuff.BOAT, tag, item(Items.OAK_BOAT));
		put(ItemsStuff.LEATHER, tag, item(Items.LEATHER));
		put(ItemsStuff.MILK_BUCKET, tag, item(Items.MILK_BUCKET));
		put(ItemsStuff.BRICK_ITEM, tag, item(Items.BRICK));
		put(ItemsStuff.CLAY_ITEM, tag, item(Items.CLAY_BALL));
		put(ItemsStuff.SUGAR_CANE, tag, item(Items.SUGAR_CANE));
		put(ItemsStuff.PAPER, tag, item(Items.PAPER));
		put(ItemsStuff.BOOK, tag, item(Items.BOOK));
		put(ItemsStuff.SLIMEBALL, tag, item(Items.SLIME_BALL));
		put(ItemsStuff.CHEST_MINECART, tag, item(Items.CHEST_MINECART));
		put(ItemsStuff.FURNACE_MINECART, tag, item(Items.FURNACE_MINECART));
		put(ItemsStuff.EGG, tag, item(Items.EGG));
		put(ItemsStuff.COMPASS, tag, item(Items.COMPASS));
		put(ItemsStuff.FISHING_ROD, tag, item(Items.FISHING_ROD));
		put(ItemsStuff.CLOCK, tag, item(Items.CLOCK));
		put(ItemsStuff.GLOWSTONE_DUST, tag, item(Items.GLOWSTONE_DUST));
		put(ItemsStuff.RAW_FISH, tag, item(Items.COD)); // TODO
		put(ItemsStuff.COOKED_FISH, tag, item(Items.COOKED_COD)); // TODO
		put(ItemsStuff.DYE_POWDER, tag, item(Items.WHITE_DYE)); // TODO
		put(ItemsStuff.BONE, tag, item(Items.BONE));
		put(ItemsStuff.SUGAR, tag, item(Items.SUGAR));
		put(ItemsStuff.CAKE_ITEM, tag, item(Items.CAKE));
		put(ItemsStuff.BED_ITEM, tag, item(Items.RED_BED));
		put(ItemsStuff.REDSTONE_REPEATER_ITEM, tag, item(Items.REPEATER));
		put(ItemsStuff.COOKIE, tag, item(Items.COOKIE));
		put(ItemsStuff.MAP, tag, item(Items.MAP));
		put(ItemsStuff.SHEARS, tag, item(Items.SHEARS));
		put(ItemsStuff.MUSIC_DISC_13, tag, item(Items.MUSIC_DISC_13));
		put(ItemsStuff.MUSIC_DISC_CAT, tag, item(Items.MUSIC_DISC_CAT));
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
