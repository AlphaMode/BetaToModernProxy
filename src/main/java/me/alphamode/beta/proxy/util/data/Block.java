package me.alphamode.beta.proxy.util.data;

import me.alphamode.beta.proxy.util.data.beta.BetaItems;

public class Block implements ItemContainer {
	private final int id;
	private final Item item;

	public Block(final int id) {
		this.id = id;
		this.item = BetaItems.registerItem(new Item(256 - id));
	}

	public int id() {
		return this.id;
	}

	@Override
	public Item asItem() {
		return this.item;
	}
}
