package me.alphamode.beta.proxy.util.data;

public final class BlockItem extends Item {
	public BlockItem(final int id) {
		super(256 - id);
	}
}
