package me.alphamode.beta.proxy.util.data.beta.entity;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.EntityDataValue;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;
import me.alphamode.beta.proxy.util.data.EntityDataSerializers;

import java.util.ArrayList;
import java.util.List;

public class BetaSynchedEntityData {
	public static final StreamCodec<ByteBuf, List<DataValue<?>>> DATA_ITEMS_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final List<DataValue<?>> dataItems) {
			for (final DataValue<?> dataItem : dataItems) {
				dataItem.write(buf);
			}

			buf.writeByte(127);
		}

		@Override
		public List<DataValue<?>> decode(final ByteBuf buf) {
			final List<DataValue<?>> items = new ArrayList<>();
			for (int i = buf.readByte(); i != 127; i = buf.readByte()) {
                final int id = (i & 224) >> 5;
                final var codec = BetaEntityData.SERIALIZERS.getSerializer(id);
                if (codec == null) {
                    throw new DecoderException("Unknown serializer type " + id);
                }
				items.add(new DataValue<>(i & 31, codec, codec.decode(buf)));
			}

			return items;
		}
	};

	public record DataValue<T>(int id, StreamCodec<ByteBuf, T> codec, T data) implements EntityDataValue<T> {
        @Override
		public void write(final ByteBuf buf) {
			buf.writeByte((BetaEntityData.SERIALIZERS.getSerializedId(codec) << 5 | this.id & 31) & 0xFF);
			codec.encode(buf, this.data);
		}
	}
}
