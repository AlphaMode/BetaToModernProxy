package me.alphamode.beta.proxy.data.item;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;

public record BetaItem(int id, DataComponentPatch patch) {
	private static final int[] ARMOR_MAX_DAMAGE = new int[]{11, 16, 15, 13};

	public static Builder builder(final int id) {
		return new Builder(id);
	}

	public static class Builder {
		private final int id;
		private final DataComponentPatch.Builder patch = DataComponentPatch.builder();

		public Builder(final int id) {
			this.id = id;
			this.setMaxStackSize(64);
			this.damagable(0);
		}

		public Builder setMaxStackSize(final int maxStackSize) {
			this.component(DataComponents.MAX_STACK_SIZE, Math.max(1, Math.min(99, maxStackSize)));
			return this;
		}

		public Builder damagable(final int maxDamage) {
			if (maxDamage <= 0) {
				this.removeComponent(DataComponents.DAMAGE);
				this.removeComponent(DataComponents.MAX_DAMAGE);
			} else {
				this.component(DataComponents.MAX_DAMAGE, maxDamage);
			}

			return this;
		}

		public Builder armor(final int armorLevel, final int armorType) {
			// TODO: item component: attributes (damageReduceAmount)
			// TODO: item component: equippable
			this.damagable(ARMOR_MAX_DAMAGE[armorType] * 3 << armorLevel);
			this.setMaxStackSize(1);
			return this;
		}

		public Builder tool(final BetaToolMaterial material /*, final Tool tool*/) {
			// TODO: item component: attributes (mining_speed, attack_damage)
			// TODO: item component: tool
			this.setMaxStackSize(1);
			this.damagable(material.getMaxUses());
			return this;
		}

		public Builder food() {
			this.setMaxStackSize(1);
			this.component(DataComponents.CONSUMABLE, Consumable.builder()
					.animation(ItemUseAnimation.NONE)
					.consumeSeconds(0)
					.sound(BuiltInRegistries.SOUND_EVENT.get(SoundEvents.EMPTY.location()).get())
					.build());
			return this;
		}

		public <T> Builder component(final DataComponentType<T> type, T value) {
			this.patch.set(type, value);
			return this;
		}

		public <T> Builder removeComponent(final DataComponentType<T> type) {
			this.patch.remove(type);
			return this;
		}

		public BetaItem build() {
			return new BetaItem(this.id, this.patch.build());
		}
	}
}
