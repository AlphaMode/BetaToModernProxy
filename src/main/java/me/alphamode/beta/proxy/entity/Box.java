package me.alphamode.beta.proxy.entity;

public record Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
	public Box(final double x, final double y, final double z) {
		this(x, y, z, x, y, z);
	}

	public Box move(final double x, final double y, final double z) {
		return new Box(this.minX - x, this.minY + y, this.minZ + z);
	}
}
