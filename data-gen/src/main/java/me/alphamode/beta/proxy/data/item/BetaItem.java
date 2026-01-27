package me.alphamode.beta.proxy.data.item;

public record BetaItem(int id, int maxStackSize, int maxDamage) {
	public static Builder builder(final int id) {
		return new Builder(id);
	}

	public static class Builder {
		private final int id;
		private int maxStackSize = 64;
		private int maxDamage = 0;

		public Builder(final int id) {
			this.id = id;
		}

		public void setMaxStackSize(final int maxStackSize) {
			this.maxStackSize = maxStackSize;
		}

		public void setMaxDamage(final int maxDamage) {
			this.maxDamage = maxDamage;
		}

		public BetaItem build() {
			return new BetaItem(this.id, this.maxStackSize, this.maxDamage);
		}
	}
}
