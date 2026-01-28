package me.alphamode.beta.proxy.data.item;

public enum BetaToolMaterial {
	WOOD(0, 59, 2.0F, 0),
	STONE(1, 131, 4.0F, 1),
	IRON(2, 250, 6.0F, 2),
	DIAMOND(3, 1561, 8.0F, 3),
	GOLD(0, 32, 12.0F, 0);

	private final int harvestLevel;
	private final int maxUses;
	private final float miningSpeed;
	private final int entityDamageAmount;

	BetaToolMaterial(int harvestLevel, int maxUses, float miningSpeed, int entityDamageAmount) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.miningSpeed = miningSpeed;
		this.entityDamageAmount = entityDamageAmount;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getMiningSpeed() {
		return this.miningSpeed;
	}

	public int getEntityDamageAmount() {
		return this.entityDamageAmount;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}
}
