package me.alphamode.beta.proxy.util.data.modern.entity;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public class ModernSynchedEntityData {
	public static final StreamCodec<ByteBuf, List<DataValue<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final List<DataValue<?>> dataItems) {
			for (final DataValue<?> dataItem : dataItems) {
				dataItem.write(buf);
			}

			buf.writeByte(255);
		}

		@Override
		public List<DataValue<?>> decode(final ByteBuf buf) {
			final List<DataValue<?>> items = new ArrayList<>();

			int id;
			while ((id = buf.readUnsignedByte()) != 255) {
				items.add(DataValue.read(buf, id));
			}

			return items;
		}
	};

    public record DataValue<T>(byte id, StreamCodec<ByteBuf, T> codec, T value) {
        public static <T> DataValue<T> read(final ByteBuf buf, final int id) {
            final StreamCodec<ByteBuf, T> codec = (StreamCodec<ByteBuf, T>) EntityDataSerializers.ID_CODEC.decode(buf);
            return new DataValue<>((byte) id, codec, codec.decode(buf));
        }

        public void write(final ByteBuf buf) {
            BasicStreamCodecs.BYTE.encode(buf, id);
            EntityDataSerializers.ID_CODEC.encode(buf, codec);
            codec.encode(buf, value);
        }
    }
}
