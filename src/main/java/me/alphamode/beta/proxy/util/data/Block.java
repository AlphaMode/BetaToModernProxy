package me.alphamode.beta.proxy.util.data;

public class Block implements ItemContainer {
	private Item item = null;

	public void setItem(final Item item) {
		this.item = item;
	}

	@Override
	public Item asItem() {
		return this.item;
	}
}
