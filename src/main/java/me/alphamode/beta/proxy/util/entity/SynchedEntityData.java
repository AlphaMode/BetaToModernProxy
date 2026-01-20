package me.alphamode.beta.proxy.util.entity;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class SynchedEntityData {
    public static final StreamCodec<ByteBuf, List<DataItem<?>>> DATA_ITEMS_CODEC = StreamCodec.of(SynchedEntityData::pack, SynchedEntityData::unpack);

	private static void writeDataItem(final ByteBuf buf, final DataItem<?> dataHolder) {
		buf.writeByte((dataHolder.getType().getId() << 5 | dataHolder.getId() & 31) & 0xFF);
		dataHolder.getType().getCodec().encode(buf, dataHolder.getData());
	}

	public static List<DataItem<?>> unpack(final ByteBuf buf) {
		final List<DataItem<?>> items = new ArrayList<>();
		for (int i = buf.readByte(); i != 127; i = buf.readByte()) {
			int type = (i & 224) >> 5;
			int id = i & 31;
			DataType dataType = DataType.values()[type];
			items.add(new DataItem<>(dataType, id, dataType.getCodec().decode(buf)));
		}

		return items;
	}

	public static void pack(final ByteBuf buf, final List<DataItem<?>> dataItems) {
		for (final DataItem<?> dataItem : dataItems) {
			writeDataItem(buf, dataItem);
		}

		buf.writeByte(127);
	}

	public enum DataType {
		BYTE(0, ByteBufCodecs.BYTE),
		SHORT(1, ByteBufCodecs.SHORT),
		INT(2, ByteBufCodecs.INT),
		FLOAT(3, ByteBufCodecs.FLOAT),
		STRING(4, ByteBufCodecs.stringUtf8()),
		ITEM_STACK(5, BetaItemStack.CODEC),
		VEC3I(6, Vec3i.CODEC);

		private final int id;
		private final StreamCodec<ByteBuf, ?> codec;

		DataType(final int id, final StreamCodec<ByteBuf, ?> codec) {
			this.id = id;
			this.codec = codec;
		}

		public int getId() {
			return id;
		}

		public <T> StreamCodec<ByteBuf, T> getCodec() {
			return (StreamCodec<ByteBuf, T>) codec;
		}
	}

	public static class DataItem<T> {
		private final DataType dataType;
		private final int id;
		private T data;

		public DataItem(final DataType dataType, final int id, final T data) {
			this.id = id;
			this.data = data;
			this.dataType = dataType;
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
			return this.dataType;
		}
	}
}
