package me.alphamode.beta.proxy.data.item;

public enum BetaToolMaterial {
	WOOD(0, 59, 2.0F, 0),
	STONE(1, 131, 4.0F, 1),
	IRON(2, 250, 6.0F, 2),
	DIAMOND(3, 1561, 8.0F, 3),
	GOLD(0, 32, 12.0F, 0);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final int damageVsEntity;

	BetaToolMaterial(int i2, int i3, float f, int i4) {
		this.harvestLevel = i2;
		this.maxUses = i3;
		this.efficiencyOnProperMaterial = f;
		this.damageVsEntity = i4;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getEfficiencyOnProperMaterial() {
		return this.efficiencyOnProperMaterial;
	}

	public int getDamageVsEntity() {
		return this.damageVsEntity;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}
}
