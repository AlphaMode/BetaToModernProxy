package me.alphamode.beta.proxy.util.data.beta.entity;

import io.netty.handler.codec.DecoderException;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.EntityDataValue;

import java.util.ArrayList;
import java.util.List;

public class BetaSynchedEntityData {
	public static final StreamCodec<ByteStream, List<DataValue<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final List<DataValue<?>> dataItems) {
			for (final DataValue<?> dataItem : dataItems) {
				dataItem.write(stream);
			}

			stream.writeByte((byte) 127);
		}

		@Override
		public List<DataValue<?>> decode(final ByteStream stream) {
			final List<DataValue<?>> items = new ArrayList<>();
			for (int i = stream.readByte(); i != 127; i = stream.readByte()) {
				final int id = (i & 224) >> 5;
				final var codec = BetaEntityData.SERIALIZERS.getSerializer(id);
				if (codec == null) {
					throw new DecoderException("Unknown serializer type " + id);
				} else {
					items.add(new DataValue<>(i & 31, codec, codec.decode(stream)));
				}
			}

			return items;
		}
	};

	public record DataValue<T>(int id, StreamCodec<ByteStream, T> codec, T data) implements EntityDataValue<T> {
		@Override
		public void write(final ByteStream buf) {
			buf.writeByte((byte) ((BetaEntityData.SERIALIZERS.getSerializedId(codec) << 5 | this.id & 31) & 0xFF));
			codec.encode(buf, this.data);
		}
	}
}
