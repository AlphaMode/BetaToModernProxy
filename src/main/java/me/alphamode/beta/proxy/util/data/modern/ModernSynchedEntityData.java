package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public class ModernSynchedEntityData {
	public static final StreamCodec<ByteBuf, List<ModernSynchedEntityData.DataItem<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final List<DataItem<?>> dataItems) {
			for (final DataItem<?> dataItem : dataItems) {
				dataItem.write(buf);
			}

			buf.writeByte(255);
		}

		@Override
		public List<DataItem<?>> decode(final ByteBuf buf) {
			final List<DataItem<?>> items = new ArrayList<>();

			int id;
			while ((id = buf.readUnsignedByte()) != 255) {
				items.add(DataItem.read(buf, id));
			}

			return items;
		}
	};

	// TODO
	public enum DataType {}

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

		public static DataItem<?> read(final ByteBuf buf, final int id) {
			final int type = ModernStreamCodecs.VAR_INT.decode(buf);
			// TODO
			return null;
		}

		public void write(final ByteBuf buf) {
			final int serializerId = -1; // TODO: EntityDataSerializers.getSerializedId(this.serializer);
			if (serializerId < 0) {
				throw new RuntimeException("TODO");
			} else {
				buf.writeByte(this.id);
				ModernStreamCodecs.VAR_INT.encode(buf, serializerId);
				// TODO: this.type.getCodec().encode(buf, this.data);
			}
		}
	}
}
