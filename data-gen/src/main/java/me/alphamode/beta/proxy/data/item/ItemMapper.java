package me.alphamode.beta.proxy.data.item;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class ItemMapper {
	private static void put(final CompoundTag tag, final ItemFactory factory) {
		final Optional<ItemRef> optionalItem = factory.reference();
		final String key = String.valueOf(factory.getBetaItem().id());
		if (optionalItem.isPresent()) {
			tag.put(key, optionalItem.get().encode());
		} else {
			final CompoundTag mapping = new CompoundTag();
			for (var entry : factory.auxMapping().entrySet()) {
				mapping.put(String.valueOf(entry.getKey()), entry.getValue().encode());
			}

			tag.put(key, mapping);
		}
	}

	private static ItemFactory defaultFactory(final BetaItem betaItem, final Item item) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.of(ItemRef.ofBeta(betaItem, item));
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return Map.of();
			}
		};
	}

	private static ItemFactory sapling(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.OAK_SAPLING))
						.put(1, ItemRef.ofBeta(betaItem, Items.SPRUCE_SAPLING))
						.put(2, ItemRef.ofBeta(betaItem, Items.BIRCH_SAPLING))
						.build();
			}
		};
	}

	private static ItemFactory logs(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.OAK_LOG))
						.put(1, ItemRef.ofBeta(betaItem, Items.SPRUCE_LOG))
						.put(2, ItemRef.ofBeta(betaItem, Items.BIRCH_LOG))
						.build();
			}
		};
	}

	private static ItemFactory leaves(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.OAK_LEAVES))
						.put(1, ItemRef.ofBeta(betaItem, Items.SPRUCE_LEAVES))
						.put(2, ItemRef.ofBeta(betaItem, Items.BIRCH_LEAVES))
						.build();
			}
		};
	}

	private static ItemFactory bush(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.DEAD_BUSH))
						.put(1, ItemRef.ofBeta(betaItem, Items.SHORT_GRASS))
						.put(2, ItemRef.ofBeta(betaItem, Items.FERN))
						.build();
			}
		};
	}

	private static ItemFactory wool(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.WHITE_WOOL))
						.put(1, ItemRef.ofBeta(betaItem, Items.ORANGE_WOOL))
						.put(2, ItemRef.ofBeta(betaItem, Items.MAGENTA_WOOL))
						.put(3, ItemRef.ofBeta(betaItem, Items.LIGHT_BLUE_WOOL))
						.put(4, ItemRef.ofBeta(betaItem, Items.YELLOW_WOOL))
						.put(5, ItemRef.ofBeta(betaItem, Items.LIME_WOOL))
						.put(6, ItemRef.ofBeta(betaItem, Items.PINK_WOOL))
						.put(7, ItemRef.ofBeta(betaItem, Items.GRAY_WOOL))
						.put(8, ItemRef.ofBeta(betaItem, Items.LIGHT_GRAY_WOOL))
						.put(9, ItemRef.ofBeta(betaItem, Items.CYAN_WOOL))
						.put(10, ItemRef.ofBeta(betaItem, Items.PURPLE_WOOL))
						.put(11, ItemRef.ofBeta(betaItem, Items.BLUE_WOOL))
						.put(12, ItemRef.ofBeta(betaItem, Items.BROWN_WOOL))
						.put(13, ItemRef.ofBeta(betaItem, Items.GREEN_WOOL))
						.put(14, ItemRef.ofBeta(betaItem, Items.RED_WOOL))
						.put(15, ItemRef.ofBeta(betaItem, Items.BLACK_WOOL))
						.build();
			}
		};
	}

	private static ItemFactory doubleSlab(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			// NOTE: closest mapping
			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.SMOOTH_STONE))
						.put(1, ItemRef.ofBeta(betaItem, Items.SANDSTONE))
						.put(2, ItemRef.ofBeta(betaItem, Items.OAK_PLANKS))
						.put(3, ItemRef.ofBeta(betaItem, Items.COBBLESTONE))
						.build();
			}
		};
	}

	private static ItemFactory slab(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.SMOOTH_STONE_SLAB))
						.put(1, ItemRef.ofBeta(betaItem, Items.SANDSTONE_SLAB))
						.put(2, ItemRef.ofBeta(betaItem, Items.OAK_SLAB))
						.put(3, ItemRef.ofBeta(betaItem, Items.COBBLESTONE_SLAB))
						.build();
			}
		};
	}

	private static ItemFactory dye(final BetaItem betaItem) {
		return new ItemFactory() {
			@Override
			public BetaItem getBetaItem() {
				return betaItem;
			}

			@Override
			public Optional<ItemRef> reference() {
				return Optional.empty();
			}

			@Override
			public Map<Integer, ItemRef> auxMapping() {
				return ImmutableMap.<Integer, ItemRef>builder()
						.put(0, ItemRef.ofBeta(betaItem, Items.INK_SAC))
						.put(1, ItemRef.ofBeta(betaItem, Items.RED_DYE))
						.put(2, ItemRef.ofBeta(betaItem, Items.GREEN_DYE))
						.put(3, ItemRef.ofBeta(betaItem, Items.BROWN_DYE))
						.put(4, ItemRef.ofBeta(betaItem, Items.LAPIS_LAZULI))
						.put(5, ItemRef.ofBeta(betaItem, Items.MAGENTA_DYE))
						.put(6, ItemRef.ofBeta(betaItem, Items.CYAN_DYE))
						.put(7, ItemRef.ofBeta(betaItem, Items.LIGHT_GRAY_DYE))
						.put(8, ItemRef.ofBeta(betaItem, Items.GRAY_DYE))
						.put(9, ItemRef.ofBeta(betaItem, Items.PINK_DYE))
						.put(10, ItemRef.ofBeta(betaItem, Items.LIME_DYE))
						.put(11, ItemRef.ofBeta(betaItem, Items.YELLOW_DYE))
						.put(12, ItemRef.ofBeta(betaItem, Items.LIGHT_BLUE_DYE))
						.put(13, ItemRef.ofBeta(betaItem, Items.PINK_DYE))
						.put(14, ItemRef.ofBeta(betaItem, Items.ORANGE_DYE))
						.put(15, ItemRef.ofBeta(betaItem, Items.BONE_MEAL))
						.build();
			}
		};
	}

	public static void writeItems(final Path outputPath) {
		final CompoundTag tag = new CompoundTag();

		// Blocks
		put(tag, defaultFactory(BetaItems.STONE, Items.STONE));
		put(tag, defaultFactory(BetaItems.GRASS, Items.GRASS_BLOCK));
		put(tag, defaultFactory(BetaItems.DIRT, Items.DIRT));
		put(tag, defaultFactory(BetaItems.COBBLESTONE, Items.COBBLESTONE));
		put(tag, defaultFactory(BetaItems.PLANKS, Items.OAK_PLANKS));
		put(tag, sapling(BetaItems.SAPLING));
		put(tag, defaultFactory(BetaItems.BEDROCK, Items.BEDROCK));
		put(tag, defaultFactory(BetaItems.FLOWING_WATER, Items.WATER_BUCKET));
		put(tag, defaultFactory(BetaItems.STILL_WATER, Items.WATER_BUCKET));
		put(tag, defaultFactory(BetaItems.FLOWING_LAVA, Items.LAVA_BUCKET));
		put(tag, defaultFactory(BetaItems.STILL_LAVA, Items.LAVA_BUCKET));
		put(tag, defaultFactory(BetaItems.SAND, Items.SAND));
		put(tag, defaultFactory(BetaItems.GRAVEL, Items.GRAVEL));
		put(tag, defaultFactory(BetaItems.GOLDEN_ORE, Items.GOLD_ORE));
		put(tag, defaultFactory(BetaItems.IRON_ORE, Items.IRON_ORE));
		put(tag, defaultFactory(BetaItems.COAL_ORE, Items.COAL_ORE));
		put(tag, logs(BetaItems.WOOD));
		put(tag, leaves(BetaItems.LEAVES));
		put(tag, defaultFactory(BetaItems.SPONGE, Items.SPONGE));
		put(tag, defaultFactory(BetaItems.GLASS, Items.GLASS));
		put(tag, defaultFactory(BetaItems.LAPIS_ORE, Items.LAPIS_ORE));
		put(tag, defaultFactory(BetaItems.LAPIS_BLOCK, Items.LAPIS_BLOCK));
		put(tag, defaultFactory(BetaItems.DISPENSER, Items.DISPENSER));
		put(tag, defaultFactory(BetaItems.SANDSTONE, Items.SANDSTONE));
		put(tag, defaultFactory(BetaItems.NOTEBLOCK, Items.NOTE_BLOCK));
		put(tag, defaultFactory(BetaItems.BED, Items.RED_BED));
		put(tag, defaultFactory(BetaItems.POWERED_RAIL, Items.POWERED_RAIL));
		put(tag, defaultFactory(BetaItems.DETECTOR_RAIL, Items.DETECTOR_RAIL));
		put(tag, defaultFactory(BetaItems.STICKY_PISTON, Items.STICKY_PISTON));
		put(tag, defaultFactory(BetaItems.WEB, Items.COBWEB));
		put(tag, bush(BetaItems.TALL_GRASS));
		put(tag, defaultFactory(BetaItems.DEAD_BUSH, Items.DEAD_BUSH));
		put(tag, defaultFactory(BetaItems.PISTON, Items.PISTON));
		put(tag, defaultFactory(BetaItems.PISTON_EXTENDED, Items.PISTON));
		put(tag, wool(BetaItems.CLOTH));
		put(tag, defaultFactory(BetaItems.MOVING_PISTON, Items.PISTON));
		put(tag, defaultFactory(BetaItems.YELLOW_PLANT, Items.DANDELION));
		put(tag, defaultFactory(BetaItems.POPPY, Items.POPPY));
		put(tag, defaultFactory(BetaItems.BROWN_MUSHROOM, Items.BROWN_MUSHROOM));
		put(tag, defaultFactory(BetaItems.RED_MUSHROOM, Items.RED_MUSHROOM));
		put(tag, defaultFactory(BetaItems.GOLDEN_BLOCK, Items.GOLD_BLOCK));
		put(tag, defaultFactory(BetaItems.IRON_BLOCK, Items.IRON_BLOCK));
		put(tag, doubleSlab(BetaItems.DOUBLE_SLAB));
		put(tag, slab(BetaItems.HALF_SLAB));
		put(tag, defaultFactory(BetaItems.BRICK, Items.BRICKS));
		put(tag, defaultFactory(BetaItems.TNT, Items.TNT));
		put(tag, defaultFactory(BetaItems.BOOKSHELF, Items.BOOKSHELF));
		put(tag, defaultFactory(BetaItems.MOSSY_COBBLESTONE, Items.MOSSY_COBBLESTONE));
		put(tag, defaultFactory(BetaItems.OBSIDIAN, Items.OBSIDIAN));
		put(tag, defaultFactory(BetaItems.TORCH, Items.TORCH));
		put(tag, defaultFactory(BetaItems.FIRE, Items.FLINT_AND_STEEL));
		put(tag, defaultFactory(BetaItems.MOB_SPAWNER, Items.SPAWNER));
		put(tag, defaultFactory(BetaItems.OAK_STAIRS, Items.OAK_STAIRS));
		put(tag, defaultFactory(BetaItems.CHEST, Items.CHEST));
		put(tag, defaultFactory(BetaItems.REDSTONE_DUST, Items.REDSTONE));
		put(tag, defaultFactory(BetaItems.DIAMOND_ORE, Items.DIAMOND_ORE));
		put(tag, defaultFactory(BetaItems.DIAMOND_BLOCK, Items.DIAMOND_BLOCK));
		put(tag, defaultFactory(BetaItems.CRAFTING_TABLE, Items.CRAFTING_TABLE));
		put(tag, defaultFactory(BetaItems.CROPS, Items.WHEAT_SEEDS));
		put(tag, defaultFactory(BetaItems.TILLED_FARMLAND, Items.FARMLAND));
		put(tag, defaultFactory(BetaItems.FURNACE, Items.FURNACE));
		put(tag, defaultFactory(BetaItems.FURNACE_ACTIVE, Items.FURNACE));
		put(tag, defaultFactory(BetaItems.SIGN, Items.OAK_SIGN));
		put(tag, defaultFactory(BetaItems.OAK_DOOR, Items.OAK_DOOR));
		put(tag, defaultFactory(BetaItems.LADDER, Items.LADDER));
		put(tag, defaultFactory(BetaItems.RAIL, Items.RAIL));
		put(tag, defaultFactory(BetaItems.COBBLESTONE_STAIRS, Items.COBBLESTONE_STAIRS));
		put(tag, defaultFactory(BetaItems.WALL_SIGN, Items.OAK_SIGN));
		put(tag, defaultFactory(BetaItems.LEVER, Items.LEVER));
		put(tag, defaultFactory(BetaItems.STONE_PRESSURE_PLATE, Items.STONE_PRESSURE_PLATE));
		put(tag, defaultFactory(BetaItems.IRON_DOOR, Items.IRON_DOOR));
		put(tag, defaultFactory(BetaItems.OAK_PRESSURE_PLATE, Items.OAK_PRESSURE_PLATE));
		put(tag, defaultFactory(BetaItems.REDSTONE_ORE, Items.REDSTONE_ORE));
		put(tag, defaultFactory(BetaItems.REDSTONE_ORE_ACTIVE, Items.REDSTONE_ORE));
		put(tag, defaultFactory(BetaItems.REDSTONE_TORCH, Items.REDSTONE_TORCH));
		put(tag, defaultFactory(BetaItems.REDSTONE_TORCH_ACTIVE, Items.REDSTONE_TORCH));
		put(tag, defaultFactory(BetaItems.BUTTON, Items.STONE_BUTTON));
		put(tag, defaultFactory(BetaItems.SNOW, Items.SNOW));
		put(tag, defaultFactory(BetaItems.ICE, Items.ICE));
		put(tag, defaultFactory(BetaItems.SNOW_BLOCK, Items.SNOW_BLOCK));
		put(tag, defaultFactory(BetaItems.CACTUS, Items.CACTUS));
		put(tag, defaultFactory(BetaItems.CLAY, Items.CLAY));
		put(tag, defaultFactory(BetaItems.REED, Items.SUGAR_CANE));
		put(tag, defaultFactory(BetaItems.JUKEBOX, Items.JUKEBOX));
		put(tag, defaultFactory(BetaItems.FENCE, Items.OAK_FENCE));
		put(tag, defaultFactory(BetaItems.PUMPKIN, Items.PUMPKIN));
		put(tag, defaultFactory(BetaItems.NETHERRACK, Items.NETHERRACK));
		put(tag, defaultFactory(BetaItems.SOUL_SAND, Items.SOUL_SAND));
		put(tag, defaultFactory(BetaItems.GLOWSTONE, Items.GLOWSTONE));
		put(tag, defaultFactory(BetaItems.PORTAL, Items.PURPLE_WOOL));
		put(tag, defaultFactory(BetaItems.LANTERN, Items.LANTERN));
		put(tag, defaultFactory(BetaItems.CAKE, Items.CAKE));
		put(tag, defaultFactory(BetaItems.REDSTONE_REPEATER, Items.REPEATER));
		put(tag, defaultFactory(BetaItems.REDSTONE_REPEATER_ACTIVE, Items.REPEATER));
		put(tag, defaultFactory(BetaItems.LOCKED_CHEST, Items.CHEST));
		put(tag, defaultFactory(BetaItems.TRAPDOOR, Items.OAK_TRAPDOOR)); // todo: was there iron?

		// Items
		put(tag, defaultFactory(BetaItems.IRON_SHOVEL, Items.IRON_SHOVEL));
		put(tag, defaultFactory(BetaItems.IRON_PICKAXE, Items.IRON_PICKAXE));
		put(tag, defaultFactory(BetaItems.IRON_AXE, Items.IRON_AXE));
		put(tag, defaultFactory(BetaItems.FLINT_AND_STEEL, Items.FLINT_AND_STEEL));
		put(tag, defaultFactory(BetaItems.APPLE, Items.APPLE));
		put(tag, defaultFactory(BetaItems.BOW, Items.BOW));
		put(tag, defaultFactory(BetaItems.ARROW, Items.ARROW));
		put(tag, defaultFactory(BetaItems.COAL, Items.COAL));
		put(tag, defaultFactory(BetaItems.DIAMOND, Items.DIAMOND));
		put(tag, defaultFactory(BetaItems.IRON_INGOT, Items.IRON_INGOT));
		put(tag, defaultFactory(BetaItems.GOLD_INGOT, Items.GOLD_INGOT));
		put(tag, defaultFactory(BetaItems.IRON_SWORD, Items.IRON_SWORD));
		put(tag, defaultFactory(BetaItems.WOODEN_SWORD, Items.WOODEN_SWORD));
		put(tag, defaultFactory(BetaItems.WOODEN_SHOVEL, Items.WOODEN_SHOVEL));
		put(tag, defaultFactory(BetaItems.WOODEN_PICKAXE, Items.WOODEN_PICKAXE));
		put(tag, defaultFactory(BetaItems.WOODEN_AXE, Items.WOODEN_AXE));
		put(tag, defaultFactory(BetaItems.STONE_SWORD, Items.STONE_SWORD));
		put(tag, defaultFactory(BetaItems.STONE_SHOVEL, Items.STONE_SHOVEL));
		put(tag, defaultFactory(BetaItems.STONE_PICKAXE, Items.STONE_PICKAXE));
		put(tag, defaultFactory(BetaItems.STONE_AXE, Items.STONE_AXE));
		put(tag, defaultFactory(BetaItems.DIAMOND_SWORD, Items.DIAMOND_SWORD));
		put(tag, defaultFactory(BetaItems.DIAMOND_SHOVEL, Items.DIAMOND_SHOVEL));
		put(tag, defaultFactory(BetaItems.DIAMOND_PICKAXE, Items.DIAMOND_PICKAXE));
		put(tag, defaultFactory(BetaItems.DIAMOND_AXE, Items.DIAMOND_AXE));
		put(tag, defaultFactory(BetaItems.STICK, Items.STICK));
		put(tag, defaultFactory(BetaItems.BOWL, Items.BOWL));
		put(tag, defaultFactory(BetaItems.STEW, Items.MUSHROOM_STEW));
		put(tag, defaultFactory(BetaItems.GOLDEN_SWORD, Items.GOLDEN_SWORD));
		put(tag, defaultFactory(BetaItems.GOLDEN_SHOVEL, Items.GOLDEN_SHOVEL));
		put(tag, defaultFactory(BetaItems.GOLDEN_PICKAXE, Items.GOLDEN_PICKAXE));
		put(tag, defaultFactory(BetaItems.GOLDEN_AXE, Items.GOLDEN_AXE));
		put(tag, defaultFactory(BetaItems.STRING, Items.STRING));
		put(tag, defaultFactory(BetaItems.FEATHER, Items.FEATHER));
		put(tag, defaultFactory(BetaItems.GUN_POWDER, Items.GUNPOWDER));
		put(tag, defaultFactory(BetaItems.WOODEN_HOE, Items.WOODEN_HOE));
		put(tag, defaultFactory(BetaItems.STONE_HOE, Items.STONE_HOE));
		put(tag, defaultFactory(BetaItems.IRON_HOE, Items.IRON_HOE));
		put(tag, defaultFactory(BetaItems.DIAMOND_HOE, Items.DIAMOND_HOE));
		put(tag, defaultFactory(BetaItems.GOLDEN_HOE, Items.GOLDEN_HOE));
		put(tag, defaultFactory(BetaItems.SEEDS, Items.WHEAT_SEEDS));
		put(tag, defaultFactory(BetaItems.WHEAT, Items.WHEAT));
		put(tag, defaultFactory(BetaItems.BREAD, Items.BREAD));
		put(tag, defaultFactory(BetaItems.LEATHER_HELMET, Items.LEATHER_HELMET));
		put(tag, defaultFactory(BetaItems.LEATHER_CHESTPLATE, Items.LEATHER_CHESTPLATE));
		put(tag, defaultFactory(BetaItems.LEATHER_LEGGINGS, Items.LEATHER_LEGGINGS));
		put(tag, defaultFactory(BetaItems.LEATHER_BOOTS, Items.LEATHER_BOOTS));
		put(tag, defaultFactory(BetaItems.CHAIN_HELMET, Items.CHAINMAIL_HELMET));
		put(tag, defaultFactory(BetaItems.CHAIN_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE));
		put(tag, defaultFactory(BetaItems.CHAIN_LEGGINGS, Items.CHAINMAIL_LEGGINGS));
		put(tag, defaultFactory(BetaItems.CHAIN_BOOTS, Items.CHAINMAIL_BOOTS));
		put(tag, defaultFactory(BetaItems.IRON_HELMET, Items.IRON_HELMET));
		put(tag, defaultFactory(BetaItems.IRON_CHESTPLATE, Items.IRON_CHESTPLATE));
		put(tag, defaultFactory(BetaItems.IRON_LEGGINGS, Items.IRON_LEGGINGS));
		put(tag, defaultFactory(BetaItems.IRON_BOOTS, Items.IRON_BOOTS));
		put(tag, defaultFactory(BetaItems.DIAMOND_HELMET, Items.DIAMOND_HELMET));
		put(tag, defaultFactory(BetaItems.DIAMOND_CHESTPLATE, Items.DIAMOND_CHESTPLATE));
		put(tag, defaultFactory(BetaItems.DIAMOND_LEGGINGS, Items.DIAMOND_LEGGINGS));
		put(tag, defaultFactory(BetaItems.DIAMOND_BOOTS, Items.DIAMOND_BOOTS));
		put(tag, defaultFactory(BetaItems.GOLDEN_HELMET, Items.GOLDEN_HELMET));
		put(tag, defaultFactory(BetaItems.GOLDEN_CHESTPLATE, Items.GOLDEN_CHESTPLATE));
		put(tag, defaultFactory(BetaItems.GOLDEN_LEGGINGS, Items.GOLDEN_LEGGINGS));
		put(tag, defaultFactory(BetaItems.GOLDEN_BOOTS, Items.GOLDEN_BOOTS));
		put(tag, defaultFactory(BetaItems.FLINT, Items.FLINT));
		put(tag, defaultFactory(BetaItems.RAW_PORKCHOP, Items.PORKCHOP));
		put(tag, defaultFactory(BetaItems.COOKED_PORKCHOP, Items.COOKED_PORKCHOP));
		put(tag, defaultFactory(BetaItems.PAINTING, Items.PAINTING));
		put(tag, defaultFactory(BetaItems.GOLDEN_APPLE, Items.GOLDEN_APPLE));
		put(tag, defaultFactory(BetaItems.SIGN_ITEM, Items.OAK_SIGN));
		put(tag, defaultFactory(BetaItems.WOODEN_DOOR, Items.OAK_DOOR));
		put(tag, defaultFactory(BetaItems.BUCKET, Items.BUCKET));
		put(tag, defaultFactory(BetaItems.WATER_BUCKET, Items.WATER_BUCKET));
		put(tag, defaultFactory(BetaItems.LAVA_BUCKET, Items.LAVA_BUCKET));
		put(tag, defaultFactory(BetaItems.MINECART, Items.MINECART));
		put(tag, defaultFactory(BetaItems.SADDLE, Items.SADDLE));
		put(tag, defaultFactory(BetaItems.IRON_DOOR_ITEM, Items.IRON_DOOR));
		put(tag, defaultFactory(BetaItems.REDSTONE, Items.REDSTONE));
		put(tag, defaultFactory(BetaItems.SNOWBALL, Items.SNOWBALL));
		put(tag, defaultFactory(BetaItems.BOAT, Items.OAK_BOAT));
		put(tag, defaultFactory(BetaItems.LEATHER, Items.LEATHER));
		put(tag, defaultFactory(BetaItems.MILK_BUCKET, Items.MILK_BUCKET));
		put(tag, defaultFactory(BetaItems.BRICK_ITEM, Items.BRICK));
		put(tag, defaultFactory(BetaItems.CLAY_ITEM, Items.CLAY_BALL));
		put(tag, defaultFactory(BetaItems.SUGAR_CANE, Items.SUGAR_CANE));
		put(tag, defaultFactory(BetaItems.PAPER, Items.PAPER));
		put(tag, defaultFactory(BetaItems.BOOK, Items.BOOK));
		put(tag, defaultFactory(BetaItems.SLIMEBALL, Items.SLIME_BALL));
		put(tag, defaultFactory(BetaItems.CHEST_MINECART, Items.CHEST_MINECART));
		put(tag, defaultFactory(BetaItems.FURNACE_MINECART, Items.FURNACE_MINECART));
		put(tag, defaultFactory(BetaItems.EGG, Items.EGG));
		put(tag, defaultFactory(BetaItems.COMPASS, Items.COMPASS));
		put(tag, defaultFactory(BetaItems.FISHING_ROD, Items.FISHING_ROD));
		put(tag, defaultFactory(BetaItems.CLOCK, Items.CLOCK));
		put(tag, defaultFactory(BetaItems.GLOWSTONE_DUST, Items.GLOWSTONE_DUST));
		put(tag, defaultFactory(BetaItems.RAW_FISH, Items.COD));
		put(tag, defaultFactory(BetaItems.COOKED_FISH, Items.COOKED_COD));
		put(tag, dye(BetaItems.DYE_POWDER));
		put(tag, defaultFactory(BetaItems.BONE, Items.BONE));
		put(tag, defaultFactory(BetaItems.SUGAR, Items.SUGAR));
		put(tag, defaultFactory(BetaItems.CAKE_ITEM, Items.CAKE));
		put(tag, defaultFactory(BetaItems.BED_ITEM, Items.RED_BED));
		put(tag, defaultFactory(BetaItems.REDSTONE_REPEATER_ITEM, Items.REPEATER));
		put(tag, defaultFactory(BetaItems.COOKIE, Items.COOKIE));
		put(tag, defaultFactory(BetaItems.MAP, Items.MAP));
		put(tag, defaultFactory(BetaItems.SHEARS, Items.SHEARS));
		put(tag, defaultFactory(BetaItems.MUSIC_DISC_13, Items.MUSIC_DISC_13));
		put(tag, defaultFactory(BetaItems.MUSIC_DISC_CAT, Items.MUSIC_DISC_CAT));
		try {
			NbtIo.writeCompressed(tag, outputPath);
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}

	public record ItemRef(Item item, DataComponentMap components) {
		public static ItemRef of(final Item item) {
			return new ItemRef(item, DataComponentMap.EMPTY);
		}

		public static ItemRef ofBeta(final BetaItem betaItem, final Item vanillaItem) {
			final DataComponentMap.Builder builder = DataComponentMap.builder();
			builder.set(DataComponents.MAX_STACK_SIZE, betaItem.maxStackSize());
			if (betaItem.maxDamage() > 0) {
				builder.set(DataComponents.MAX_DAMAGE, betaItem.maxDamage());
			} else {
				builder.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);
			}

			if (betaItem instanceof BetaFood) {
				builder.set(DataComponents.CONSUMABLE, Consumable.builder()
						.animation(ItemUseAnimation.NONE)
						.consumeSeconds(0)
						.sound(BuiltInRegistries.SOUND_EVENT.get(SoundEvents.EMPTY.location()).get())
						.build());
			}

			return new ItemRef(vanillaItem, builder.build());
		}

		public CompoundTag encode() {
			final CompoundTag refTag = new CompoundTag();
			refTag.putInt("id", Item.getId(item));

			final CompoundTag componentsTag = new CompoundTag();
			for (var entry : components) {
				final Tag tag = jsonToTag(entry.encodeValue(JsonOps.INSTANCE).getOrThrow());
				if (tag != null) {
					componentsTag.put(entry.type().toString(), tag);
				}
			}

			refTag.put("default_components", componentsTag);
			return refTag;
		}
	}

	public interface ItemFactory {
		BetaItem getBetaItem();

		Optional<ItemRef> reference();

		Map<Integer, ItemRef> auxMapping();
	}

	private static Tag jsonToTag(final JsonElement element) {
		switch (element) {
			case JsonObject object -> {
				final CompoundTag tag = new CompoundTag();
				for (final var entry : object.entrySet()) {
					tag.put(entry.getKey(), jsonToTag(entry.getValue()));
				}

				return tag;
			}

			case JsonArray array -> {
				final ListTag tags = new ListTag();
				for (final var value : array) {
					tags.add(jsonToTag(value));
				}

				return tags;
			}

			case JsonPrimitive primitive -> {
				if (primitive.isNumber()) {
					return new DoubleTag(primitive.getAsNumber().doubleValue());
				} else if (primitive.isBoolean()) {
					return new IntTag(primitive.getAsBoolean() ? 1 : 0);
				} else if (primitive.isString()) {
					return new StringTag(primitive.getAsString());
				} else {
					throw new UnsupportedOperationException();
				}
			}

			case JsonNull jsonNull -> {
				return null;
			}

			case null, default -> throw new UnsupportedOperationException();
		}
	}
}
