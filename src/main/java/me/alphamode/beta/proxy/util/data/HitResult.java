package me.alphamode.beta.proxy.util.data;

public abstract class HitResult {
	protected final Vec3d location;

	protected HitResult(Vec3d location) {
		this.location = location;
	}

	public abstract HitResult.Type getType();

	public Vec3d getLocation() {
		return this.location;
	}

	public enum Type {
		MISS,
		BLOCK,
		ENTITY
	}
}
