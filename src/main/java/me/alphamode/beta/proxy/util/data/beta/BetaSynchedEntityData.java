package me.alphamode.beta.proxy.util.data.beta;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

import java.util.ArrayList;
import java.util.List;

public class BetaSynchedEntityData {
	public static final StreamCodec<ByteBuf, List<DataItem<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final List<DataItem<?>> dataItems) {
			for (final DataItem<?> dataItem : dataItems) {
				dataItem.write(buf);
			}

			buf.writeByte(127);
		}

		@Override
		public List<DataItem<?>> decode(final ByteBuf buf) {
			final List<DataItem<?>> items = new ArrayList<>();
			for (int i = buf.readByte(); i != 127; i = buf.readByte()) {
				final DataType type = DataType.values()[(i & 224) >> 5];
				items.add(new DataItem<>(type, i & 31, type.getCodec().decode(buf)));
			}

			return items;
		}
	};

	public enum DataType {
		BYTE(0, BasicStreamCodecs.BYTE),
		SHORT(1, BasicStreamCodecs.SHORT),
		INT(2, BasicStreamCodecs.INT),
		FLOAT(3, BasicStreamCodecs.FLOAT),
		STRING(4, BetaStreamCodecs.stringUtf8()),
		ITEM_STACK(5, BetaItemStack.CODEC),
		VEC3I(6, Vec3i.CODEC);

		private final int id;
		private final StreamCodec<ByteBuf, ?> codec;

		DataType(final int id, final StreamCodec<ByteBuf, ?> codec) {
			this.id = id;
			this.codec = codec;
		}

		public int getId() {
			return this.id;
		}

		public <T> StreamCodec<ByteBuf, T> getCodec() {
			return (StreamCodec<ByteBuf, T>) this.codec;
		}
	}

	public static class DataItem<T> {
		private final DataType type;
		private final int id;
		private T data;

		public DataItem(final DataType type, final int id, final T data) {
			this.id = id;
			this.data = data;
			this.type = type;
		}

		public int getId() {
			return this.id;
		}

		public void setData(T data) {
			this.data = data;
		}

		public T getData() {
			return this.data;
		}

		public DataType getType() {
			return this.type;
		}

		public void write(final ByteBuf buf) {
			buf.writeByte((this.type.getId() << 5 | this.id & 31) & 0xFF);
			this.type.getCodec().encode(buf, this.data);
		}
	}
}
