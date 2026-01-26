package me.alphamode.beta.proxy.util.data;

import me.alphamode.beta.proxy.util.data.beta.BetaItems;

public final class Block implements ItemContainer {
	private final int id;
	private final BlockItem item;

	public Block(final int id) {
		this.id = id;
		this.item = (BlockItem) BetaItems.registerItem(new BlockItem(id));
	}

	public int id() {
		return this.id;
	}

	@Override
	public BlockItem asItem() {
		return this.item;
	}
}
