package me.alphamode.beta.proxy.entity;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.ByteBufCodecs;
import me.alphamode.beta.proxy.StreamCodec;
import me.alphamode.beta.proxy.data.BetaItemStack;
import me.alphamode.beta.proxy.data.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class SynchedEntityData {
    private static void writeDataItem(ByteBuf data, DataItem<?> dataHolder) {
        int id = (dataHolder.getType().getId() << 5 | dataHolder.getId() & 31) & 0xFF;
        data.writeByte(id);
        dataHolder.getType().getCodec().encode(data, dataHolder.getData());
    }

    public static List<DataItem<?>> unpack(ByteBuf data) {
        List<DataItem<?>> items = new ArrayList<>();

        for (int i = data.readByte(); i != 127; i = data.readByte()) {
            int type = (i & 224) >> 5;
            int id = i & 31;
            DataType dataType = DataType.values()[type];
            items.add(new DataItem<>(dataType, id, dataType.getCodec().decode(data)));
        }

        return items;
    }

    public static void pack(ByteBuf data, List<DataItem<?>> dataItems) {
        for (DataItem<?> dataItem : dataItems) {
            writeDataItem(data, dataItem);
        }

        data.writeByte(127);
    }

    public enum DataType {
        BYTE(0, ByteBufCodecs.BYTE),
        SHORT(1, ByteBufCodecs.SHORT),
        INT(2, ByteBufCodecs.INT),
        FLOAT(3, ByteBufCodecs.FLOAT),
        STRING(4, ByteBufCodecs.stringUtf8(32767)),
        ITEM_STACK(5, BetaItemStack.CODEC),
        VEC3I(6, Vec3i.CODEC);

        private int id;
        private StreamCodec<ByteBuf, ?> codec;

        DataType(int id, StreamCodec<ByteBuf, ?> codec) {
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
        private boolean dirty;

        public DataItem(DataType dataType, int id, T data) {
            this.id = id;
            this.data = data;
            this.dataType = dataType;
            this.dirty = true;
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

        public boolean isDirty() {
            return this.dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }
    }
}
