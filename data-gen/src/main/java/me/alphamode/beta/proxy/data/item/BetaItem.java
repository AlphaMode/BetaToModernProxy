package me.alphamode.beta.proxy.data.item;

public class BetaItem {
	private final int id;
	private final int maxStackSize;
	private final int maxDamage;

	public BetaItem(final int id, final int maxStackSize, final int maxDamage) {
		this.id = id;
		this.maxStackSize = maxStackSize;
		this.maxDamage = maxDamage;
	}

	public int id() {
		return this.id;
	}

	public int maxStackSize() {
		return this.maxStackSize;
	}

	public int maxDamage() {
		return this.maxDamage;
	}

	public static Builder builder(final int id) {
		return new Builder(id);
	}

	public static class Builder {
		private final int id;
		private int maxStackSize = 64;
		private int maxDamage = 0;
		private boolean food = false;

		public Builder(final int id) {
			this.id = id;
		}

		public Builder setMaxStackSize(final int maxStackSize) {
			this.maxStackSize = maxStackSize;
			return this;
		}

		public Builder setMaxDamage(final int maxDamage) {
			this.maxDamage = maxDamage;
			return this;
		}

		public Builder food() {
			this.food = true;
			return this;
		}

		public BetaItem build() {
			if (this.food) {
				return new BetaFood(this.id, this.maxDamage);
			} else {
				return new BetaItem(this.id, this.maxStackSize, this.maxDamage);
			}
		}
	}
}
