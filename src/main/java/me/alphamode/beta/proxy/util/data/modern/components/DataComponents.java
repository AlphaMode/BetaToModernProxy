package me.alphamode.beta.proxy.util.data.modern.components;

import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public final class DataComponents {
	private static final Map<Identifier, DataComponentType<?>> REGISTRY = new HashMap<>();

//	public static final DataComponentType<CustomData> CUSTOM_DATA = register("custom_data", b -> b);

	public static final DataComponentType<Integer> MAX_STACK_SIZE = register("max_stack_size", b -> b.persistent(ModernCodecs.intRange(1, 99)).networkSynchronized(ModernStreamCodecs.VAR_INT));

	public static final DataComponentType<Integer> MAX_DAMAGE = register("max_damage", b -> b.persistent(ModernCodecs.POSITIVE_INT).networkSynchronized(ModernStreamCodecs.VAR_INT));

	public static final DataComponentType<Integer> DAMAGE = register("damage", b -> b.persistent(ModernCodecs.NON_NEGATIVE_INT).networkSynchronized(ModernStreamCodecs.VAR_INT));

//	public static final DataComponentType<Unit> UNBREAKABLE = register("unbreakable", b -> b.networkSynchronized(Unit.STREAM_CODEC));

//	public static final DataComponentType<UseEffects> USE_EFFECTS = register("use_effects", b -> b.networkSynchronized(UseEffects.STREAM_CODEC));

	public static final DataComponentType<TextComponent> CUSTOM_NAME = register("custom_name", b -> b.networkSynchronized(ModernStreamCodecs.TEXT_COMPONENT));

	public static final DataComponentType<Float> MINIMUM_ATTACK_CHARGE = register("minimum_attack_charge", b -> b.networkSynchronized(BasicStreamCodecs.FLOAT));

//	public static final DataComponentType<EitherHolder<DamageType>> DAMAGE_TYPE = register("damage_type", b -> bRegistries.DAMAGE_TYPE, DamageType.CODEC)).networkSynchronized(EitherHolder.streamCodec(Registries.DAMAGE_TYPE, DamageType.STREAM_CODEC)));

	public static final DataComponentType<TextComponent> ITEM_NAME = register("item_name", b -> b.networkSynchronized(ModernStreamCodecs.TEXT_COMPONENT));

	public static final DataComponentType<Identifier> ITEM_MODEL = register("item_model", b -> b.networkSynchronized(ModernStreamCodecs.IDENTIFIER));

//	public static final DataComponentType<ItemLore> LORE = register("lore", b -> b.networkSynchronized(ItemLore.STREAM_CODEC));
//
//	public static final DataComponentType<Rarity> RARITY = register("rarity", b -> b.networkSynchronized(Rarity.STREAM_CODEC));
//
//	public static final DataComponentType<ItemEnchantments> ENCHANTMENTS = register("enchantments", b -> b.networkSynchronized(ItemEnchantments.STREAM_CODEC));
//
//	public static final DataComponentType<AdventureModePredicate> CAN_PLACE_ON = register("can_place_on", b -> b.networkSynchronized(AdventureModePredicate.STREAM_CODEC));
//
//	public static final DataComponentType<AdventureModePredicate> CAN_BREAK = register("can_break", b -> b.networkSynchronized(AdventureModePredicate.STREAM_CODEC));
//
//	public static final DataComponentType<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS = register("attribute_modifiers", b -> b.networkSynchronized(ItemAttributeModifiers.STREAM_CODEC));
//
//	public static final DataComponentType<CustomModelData> CUSTOM_MODEL_DATA = register("custom_model_data", b -> b.networkSynchronized(CustomModelData.STREAM_CODEC));
//
//	public static final DataComponentType<TooltipDisplay> TOOLTIP_DISPLAY = register("tooltip_display", b -> b.networkSynchronized(TooltipDisplay.STREAM_CODEC));

	public static final DataComponentType<Integer> REPAIR_COST = register("repair_cost", b -> b.networkSynchronized(ModernStreamCodecs.VAR_INT));

//	public static final DataComponentType<Unit> CREATIVE_SLOT_LOCK = register("creative_slot_lock", b -> b.networkSynchronized(Unit.STREAM_CODEC));

	public static final DataComponentType<Boolean> ENCHANTMENT_GLINT_OVERRIDE = register("enchantment_glint_override", b -> b.networkSynchronized(BasicStreamCodecs.BOOL));

//	public static final DataComponentType<Unit> INTANGIBLE_PROJECTILE = register("intangible_projectile", b -> b);
//
//	public static final DataComponentType<FoodProperties> FOOD = register("food", b -> b.networkSynchronized(FoodProperties.DIRECT_STREAM_CODEC));
//
//	public static final DataComponentType<Consumable> CONSUMABLE = register("consumable", b -> b.networkSynchronized(Consumable.STREAM_CODEC));
//
//	public static final DataComponentType<UseRemainder> USE_REMAINDER = register("use_remainder", b -> b.networkSynchronized(UseRemainder.STREAM_CODEC));
//
//	public static final DataComponentType<UseCooldown> USE_COOLDOWN = register("use_cooldown", b -> b.networkSynchronized(UseCooldown.STREAM_CODEC));
//
//	public static final DataComponentType<DamageResistant> DAMAGE_RESISTANT = register("damage_resistant", b -> b.networkSynchronized(DamageResistant.STREAM_CODEC));
//
//	public static final DataComponentType<Tool> TOOL = register("tool", b -> b.networkSynchronized(Tool.STREAM_CODEC));
//
//	public static final DataComponentType<Weapon> WEAPON = register("weapon", b -> b.networkSynchronized(Weapon.STREAM_CODEC));
//
//	public static final DataComponentType<AttackRange> ATTACK_RANGE = register("attack_range", b -> b.networkSynchronized(AttackRange.STREAM_CODEC));
//
//	public static final DataComponentType<Enchantable> ENCHANTABLE = register("enchantable", b -> b.networkSynchronized(Enchantable.STREAM_CODEC));
//
//	public static final DataComponentType<Equippable> EQUIPPABLE = register("equippable", b -> b.networkSynchronized(Equippable.STREAM_CODEC));
//
//	public static final DataComponentType<Repairable> REPAIRABLE = register("repairable", b -> b.networkSynchronized(Repairable.STREAM_CODEC));
//
//	public static final DataComponentType<Unit> GLIDER = register("glider", b -> b.networkSynchronized(Unit.STREAM_CODEC));

	public static final DataComponentType<Identifier> TOOLTIP_STYLE = register("tooltip_style", b -> b.networkSynchronized(ModernStreamCodecs.IDENTIFIER));

//	public static final DataComponentType<DeathProtection> DEATH_PROTECTION = register("death_protection", b -> b.networkSynchronized(DeathProtection.STREAM_CODEC));
//
//	public static final DataComponentType<BlocksAttacks> BLOCKS_ATTACKS = register("blocks_attacks", b -> b.networkSynchronized(BlocksAttacks.STREAM_CODEC));
//
//	public static final DataComponentType<PiercingWeapon> PIERCING_WEAPON = register("piercing_weapon", b -> b.networkSynchronized(PiercingWeapon.STREAM_CODEC));
//
//	public static final DataComponentType<KineticWeapon> KINETIC_WEAPON = register("kinetic_weapon", b -> b.networkSynchronized(KineticWeapon.STREAM_CODEC));
//
//	public static final DataComponentType<SwingAnimation> SWING_ANIMATION = register("swing_animation", b -> b.networkSynchronized(SwingAnimation.STREAM_CODEC));
//
//	public static final DataComponentType<ItemEnchantments> STORED_ENCHANTMENTS = register("stored_enchantments", b -> b.networkSynchronized(ItemEnchantments.STREAM_CODEC));
//
//	public static final DataComponentType<DyedItemColor> DYED_COLOR = register("dyed_color", b -> b.networkSynchronized(DyedItemColor.STREAM_CODEC));
//
//	public static final DataComponentType<MapItemColor> MAP_COLOR = register("map_color", b -> b.networkSynchronized(MapItemColor.STREAM_CODEC));
//
//	public static final DataComponentType<MapId> MAP_ID = register("map_id", b -> b.networkSynchronized(MapId.STREAM_CODEC));
//
//	public static final DataComponentType<MapDecorations> MAP_DECORATIONS = register("map_decorations", b -> b);
//
//	public static final DataComponentType<MapPostProcessing> MAP_POST_PROCESSING = register("map_post_processing", b -> b.networkSynchronized(MapPostProcessing.STREAM_CODEC));
//
//	public static final DataComponentType<ChargedProjectiles> CHARGED_PROJECTILES = register("charged_projectiles", b -> b.networkSynchronized(ChargedProjectiles.STREAM_CODEC));
//
//	public static final DataComponentType<BundleContents> BUNDLE_CONTENTS = register("bundle_contents", b -> b.networkSynchronized(BundleContents.STREAM_CODEC));
//
//	public static final DataComponentType<PotionContents> POTION_CONTENTS = register("potion_contents", b -> b.networkSynchronized(PotionContents.STREAM_CODEC));
//
//	public static final DataComponentType<Float> POTION_DURATION_SCALE = register("potion_duration_scale", b -> b.networkSynchronized(ByteBufCodecs.FLOAT));
//
//	public static final DataComponentType<SuspiciousStewEffects> SUSPICIOUS_STEW_EFFECTS = register("suspicious_stew_effects", b -> b.networkSynchronized(SuspiciousStewEffects.STREAM_CODEC));
//
//	public static final DataComponentType<WritableBookContent> WRITABLE_BOOK_CONTENT = register("writable_book_content", b -> b.networkSynchronized(WritableBookContent.STREAM_CODEC));
//
//	public static final DataComponentType<WrittenBookContent> WRITTEN_BOOK_CONTENT = register("written_book_content", b -> b.networkSynchronized(WrittenBookContent.STREAM_CODEC));
//
//	public static final DataComponentType<ArmorTrim> TRIM = register("trim", b -> b.networkSynchronized(ArmorTrim.STREAM_CODEC));
//
//	public static final DataComponentType<DebugStickState> DEBUG_STICK_STATE = register("debug_stick_state", b -> b);
//
//	public static final DataComponentType<TypedEntityData<EntityType<?>>> ENTITY_DATA = register("entity_data", b -> bEntityType.CODEC)).networkSynchronized(TypedEntityData.streamCodec(EntityType.STREAM_CODEC)));
//
//	public static final DataComponentType<CustomData> BUCKET_ENTITY_DATA = register("bucket_entity_data", b -> b.networkSynchronized(CustomData.STREAM_CODEC));
//
//	public static final DataComponentType<TypedEntityData<BlockEntityType<?>>> BLOCK_ENTITY_DATA = register("block_entity_data", b -> bBuiltInRegistries.BLOCK_ENTITY_TYPE.byNameCodec())).networkSynchronized(TypedEntityData.streamCodec(ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE))));
//
//	public static final DataComponentType<InstrumentComponent> INSTRUMENT = register("instrument", b -> b.networkSynchronized(InstrumentComponent.STREAM_CODEC));
//
//	public static final DataComponentType<ProvidesTrimMaterial> PROVIDES_TRIM_MATERIAL = register("provides_trim_material", b -> b.networkSynchronized(ProvidesTrimMaterial.STREAM_CODEC));
//
//	public static final DataComponentType<OminousBottleAmplifier> OMINOUS_BOTTLE_AMPLIFIER = register("ominous_bottle_amplifier", b -> b.networkSynchronized(OminousBottleAmplifier.STREAM_CODEC));
//
//	public static final DataComponentType<JukeboxPlayable> JUKEBOX_PLAYABLE = register("jukebox_playable", b -> b.networkSynchronized(JukeboxPlayable.STREAM_CODEC));
//
//	public static final DataComponentType<TagKey<BannerPattern>> PROVIDES_BANNER_PATTERNS = register("provides_banner_patterns", b -> bRegistries.BANNER_PATTERN)).networkSynchronized(TagKey.streamCodec(Registries.BANNER_PATTERN)));
//
//	public static final DataComponentType<List<ResourceKey<Recipe<?>>>> RECIPES = register("recipes", b -> blistOf()));
//
//	public static final DataComponentType<LodestoneTracker> LODESTONE_TRACKER = register("lodestone_tracker", b -> b.networkSynchronized(LodestoneTracker.STREAM_CODEC));
//
//	public static final DataComponentType<FireworkExplosion> FIREWORK_EXPLOSION = register("firework_explosion", b -> b.networkSynchronized(FireworkExplosion.STREAM_CODEC));
//
//	public static final DataComponentType<Fireworks> FIREWORKS = register("fireworks", b -> b.networkSynchronized(Fireworks.STREAM_CODEC));
//
//	public static final DataComponentType<ResolvableProfile> PROFILE = register("profile", b -> b.networkSynchronized(ResolvableProfile.STREAM_CODEC));

	public static final DataComponentType<Identifier> NOTE_BLOCK_SOUND = register("note_block_sound", b -> b.networkSynchronized(ModernStreamCodecs.IDENTIFIER));

//	public static final DataComponentType<BannerPatternLayers> BANNER_PATTERNS = register("banner_patterns", b -> b.networkSynchronized(BannerPatternLayers.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> BASE_COLOR = register("base_color", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<PotDecorations> POT_DECORATIONS = register("pot_decorations", b -> b.networkSynchronized(PotDecorations.STREAM_CODEC));
//
//	public static final DataComponentType<ItemContainerContents> CONTAINER = register("container", b -> b.networkSynchronized(ItemContainerContents.STREAM_CODEC));
//
//	public static final DataComponentType<BlockItemStateProperties> BLOCK_STATE = register("block_state", b -> b.networkSynchronized(BlockItemStateProperties.STREAM_CODEC));
//
//	public static final DataComponentType<Bees> BEES = register("bees", b -> b.networkSynchronized(Bees.STREAM_CODEC));
//
//	public static final DataComponentType<LockCode> LOCK = register("lock", b -> b);
//
//	public static final DataComponentType<SeededContainerLoot> CONTAINER_LOOT = register("container_loot", b -> b);
//
//	public static final DataComponentType<Holder<SoundEvent>> BREAK_SOUND = register("break_sound", b -> b.networkSynchronized(SoundEvent.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<VillagerType>> VILLAGER_VARIANT = register("villager/variant", b -> b.networkSynchronized(VillagerType.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<WolfVariant>> WOLF_VARIANT = register("wolf/variant", b -> b.networkSynchronized(WolfVariant.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<WolfSoundVariant>> WOLF_SOUND_VARIANT = register("wolf/sound_variant", b -> b.networkSynchronized(WolfSoundVariant.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> WOLF_COLLAR = register("wolf/collar", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<Fox.Variant> FOX_VARIANT = register("fox/variant", b -> b.networkSynchronized(Fox.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Salmon.Variant> SALMON_SIZE = register("salmon/size", b -> b.networkSynchronized(Salmon.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Parrot.Variant> PARROT_VARIANT = register("parrot/variant", b -> b.networkSynchronized(Parrot.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<TropicalFish.Pattern> TROPICAL_FISH_PATTERN = register("tropical_fish/pattern", b -> b.networkSynchronized(TropicalFish.Pattern.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> TROPICAL_FISH_BASE_COLOR = register("tropical_fish/base_color", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> TROPICAL_FISH_PATTERN_COLOR = register("tropical_fish/pattern_color", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<MushroomCow.Variant> MOOSHROOM_VARIANT = register("mooshroom/variant", b -> b.networkSynchronized(MushroomCow.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Rabbit.Variant> RABBIT_VARIANT = register("rabbit/variant", b -> b.networkSynchronized(Rabbit.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<PigVariant>> PIG_VARIANT = register("pig/variant", b -> b.networkSynchronized(PigVariant.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<CowVariant>> COW_VARIANT = register("cow/variant", b -> b.networkSynchronized(CowVariant.STREAM_CODEC));
//
//	public static final DataComponentType<EitherHolder<ChickenVariant>> CHICKEN_VARIANT = register("chicken/variant", b -> bRegistries.CHICKEN_VARIANT, ChickenVariant.CODEC)).networkSynchronized(EitherHolder.streamCodec(Registries.CHICKEN_VARIANT, ChickenVariant.STREAM_CODEC)));
//
//	public static final DataComponentType<EitherHolder<ZombieNautilusVariant>> ZOMBIE_NAUTILUS_VARIANT = register("zombie_nautilus/variant", b -> bRegistries.ZOMBIE_NAUTILUS_VARIANT, ZombieNautilusVariant.CODEC)).networkSynchronized(EitherHolder.streamCodec(Registries.ZOMBIE_NAUTILUS_VARIANT, ZombieNautilusVariant.STREAM_CODEC)));
//
//	public static final DataComponentType<Holder<FrogVariant>> FROG_VARIANT = register("frog/variant", b -> b.networkSynchronized(FrogVariant.STREAM_CODEC));
//
//	public static final DataComponentType<Variant> HORSE_VARIANT = register("horse/variant", b -> b.networkSynchronized(Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<PaintingVariant>> PAINTING_VARIANT = register("painting/variant", b -> b.networkSynchronized(PaintingVariant.STREAM_CODEC));
//
//	public static final DataComponentType<Llama.Variant> LLAMA_VARIANT = register("llama/variant", b -> b.networkSynchronized(Llama.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Axolotl.Variant> AXOLOTL_VARIANT = register("axolotl/variant", b -> .networkSynchronized(Axolotl.Variant.STREAM_CODEC));
//
//	public static final DataComponentType<Holder<CatVariant>> CAT_VARIANT = register("cat/variant", b -> b.networkSynchronized(CatVariant.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> CAT_COLLAR = register("cat/collar", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> SHEEP_COLOR = register("sheep/color", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));
//
//	public static final DataComponentType<DyeColor> SHULKER_COLOR = register("shulker/color", b -> b.networkSynchronized(DyeColor.STREAM_CODEC));

	private static <T> DataComponentType<T> register(final String id, final UnaryOperator<DataComponentType.Builder<T>> builder) {
		final DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
		REGISTRY.put(Identifier.defaultNamespace(id), type);
		return type;
	}

	public static DataComponentType<?> byId(final Identifier identifier) {
		return REGISTRY.getOrDefault(identifier, null);
	}

	public static DataComponentType<?> byRawId(final int id) {
		if (id == 1) {
			return MAX_STACK_SIZE;
		} else if (id == 2) {
			return MAX_DAMAGE;
		} else if (id == 3) {
			return DAMAGE;
		}

		int index = 0;
		for (final var it : REGISTRY.values()) {
			if (index == id) {
				return it;
			}

			index++;
		}

		throw new IndexOutOfBoundsException();
	}

	public static Identifier getId(final DataComponentType<?> type) {
		for (final var entry : REGISTRY.entrySet()) {
			if (type == entry.getValue()) {
				return entry.getKey();
			}
		}

		throw new IndexOutOfBoundsException();
	}

	public static int getRawId(final DataComponentType<?> type) {
		// TODO
		if (type == MAX_STACK_SIZE) {
			return 1;
		} else if (type == MAX_DAMAGE) {
			return 2;
		} else if (type == DAMAGE) {
			return 3;
		}

		int index = 0;
		for (final var it : REGISTRY.values()) {
			if (type == it) {
				return index;
			}

			index++;
		}

		throw new IndexOutOfBoundsException();
	}
}
