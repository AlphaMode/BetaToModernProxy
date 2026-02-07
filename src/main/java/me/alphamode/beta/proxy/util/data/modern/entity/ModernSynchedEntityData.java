package me.alphamode.beta.proxy.util.data.modern.entity;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.EntityDataValue;

import java.util.ArrayList;
import java.util.List;

public class ModernSynchedEntityData {
	public static final StreamCodec<ByteStream, List<DataValue<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final List<DataValue<?>> dataItems) {
			for (final EntityDataValue<?> dataItem : dataItems) {
				dataItem.write(buf);
			}

			buf.writeByte((byte) 255);
		}

		@Override
		public List<DataValue<?>> decode(final ByteStream buf) {
			final List<DataValue<?>> items = new ArrayList<>();

			int id;
			while ((id = buf.readUnsignedByte()) != 255) {
				items.add(DataValue.read(buf, id));
			}

			return items;
		}
	};

	public record DataValue<T>(int id, StreamCodec<ByteStream, T> codec, T data) implements EntityDataValue<T> {
		public static <T> DataValue<T> read(final ByteStream buf, final int id) {
			final StreamCodec<ByteStream, T> codec = (StreamCodec<ByteStream, T>) ModernEntityData.ID_CODEC.decode(buf);
			return new DataValue<>((byte) id, codec, codec.decode(buf));
		}

		@Override
		public void write(final ByteStream buf) {
			CommonStreamCodecs.BYTE.encode(buf, (byte) id);
			ModernEntityData.ID_CODEC.encode(buf, codec);
			codec.encode(buf, data);
		}
	}
}
