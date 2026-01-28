package me.alphamode.beta.proxy.data.item;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Tool;

public record BetaItem(int id, DataComponentPatch patch) {
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

		public Builder tool(final Tool tool) {
			this.component(DataComponents.TOOL, tool);
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
