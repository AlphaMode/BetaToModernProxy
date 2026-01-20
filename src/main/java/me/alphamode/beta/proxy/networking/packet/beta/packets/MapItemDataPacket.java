package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MapItemDataPacket implements Packet {
	public short item;
	public short mapId;
	public byte[] mapColors;

	public MapItemDataPacket() {
//        this.shouldDelay = true;
	}

	public MapItemDataPacket(final short item, final short mapId, final byte[] colors) {
//        this.shouldDelay = true;
		this.item = item;
		this.mapId = mapId;
		this.mapColors = colors;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.item = buf.readShort();
		this.mapId = buf.readShort();
		this.mapColors = new byte[buf.readUnsignedByte() & 255];
		buf.readBytes(this.mapColors);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeShort(this.item);
		buf.writeShort(this.mapId);
		buf.writeByte(this.mapColors.length);
		buf.writeBytes(this.mapColors);
	}
}
