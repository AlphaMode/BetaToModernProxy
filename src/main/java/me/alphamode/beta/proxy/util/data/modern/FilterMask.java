package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.BitSet;

public record FilterMask(BitSet mask, Type type) {
	public static final StreamCodec<ByteBuf, FilterMask> CODEC = StreamCodec.ofMember(FilterMask::write, FilterMask::new);
	public static final FilterMask FULLY_FILTERED = new FilterMask(new BitSet(0), FilterMask.Type.FULLY_FILTERED);
	public static final FilterMask PASS_THROUGH = new FilterMask(new BitSet(0), FilterMask.Type.PASS_THROUGH);

	public FilterMask(final ByteBuf buf) {
		FilterMask.Type type = Type.CODEC.decode(buf);
		BitSet bitSet = null;
		switch (type) {
			case PASS_THROUGH, FULLY_FILTERED -> bitSet = new BitSet(0);
			case PARTIALLY_FILTERED -> bitSet = ModernStreamCodecs.BIT_SET.decode(buf);
		}

		this(bitSet, type);
	}

	public void write(final ByteBuf buf) {
		Type.CODEC.encode(buf, this.type);
		if (this.type == FilterMask.Type.PARTIALLY_FILTERED) {
			ModernStreamCodecs.BIT_SET.encode(buf, this.mask);
		}
	}

	public boolean isEmpty() {
		return this.type == FilterMask.Type.PASS_THROUGH;
	}

	public boolean isFullyFiltered() {
		return this.type == FilterMask.Type.FULLY_FILTERED;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof FilterMask(BitSet itSet, Type itType)) {
			return this.mask.equals(itSet) && this.type == itType;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return 31 * this.mask.hashCode() + this.type.hashCode();
	}

	public enum Type implements StringRepresentable {
		PASS_THROUGH("pass_through"),
		FULLY_FILTERED("fully_filtered"),
		PARTIALLY_FILTERED("partially_filtered");

		public static final StreamCodec<ByteBuf, Type> CODEC = ModernStreamCodecs.javaEnum(Type.class);
		private final String serializedName;

		Type(String serializedName) {
			this.serializedName = serializedName;
		}

		@Override
		public String getSerializedName() {
			return this.serializedName;
		}
	}
}
