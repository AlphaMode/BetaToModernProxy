package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MapItemDataPacket implements Packet {
    public short item;
    public short mapId;
    public byte[] mapColors;

    public MapItemDataPacket() {
//        this.shouldDelay = true;
    }

    public MapItemDataPacket(short item, short mapId, byte[] colors) {
//        this.shouldDelay = true;
        this.item = item;
        this.mapId = mapId;
        this.mapColors = colors;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.item = data.readShort();
        this.mapId = data.readShort();
        this.mapColors = new byte[data.readUnsignedByte() & 255];
        data.readBytes(this.mapColors);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeShort(this.item);
        data.writeShort(this.mapId);
        data.writeByte(this.mapColors.length);
        data.writeBytes(this.mapColors);
    }
}
