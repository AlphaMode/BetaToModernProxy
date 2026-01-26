package me.alphamode.beta.proxy.util.data;

public class Item {
	private final int id;

	public Item(final int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[id=" + this.id + "]";
	}
}
