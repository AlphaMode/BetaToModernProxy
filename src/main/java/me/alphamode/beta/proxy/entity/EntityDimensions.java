package me.alphamode.beta.proxy.entity;

public record EntityDimensions(float eyeHeight, float width, float height) {
	public EntityDimensions withEyeHeight(final float eyeHeight) {
		return new EntityDimensions(eyeHeight, this.width, this.height);
	}

	public EntityDimensions withSize(final float width, final float height) {
		return new EntityDimensions(this.eyeHeight, width, height);
	}
}
