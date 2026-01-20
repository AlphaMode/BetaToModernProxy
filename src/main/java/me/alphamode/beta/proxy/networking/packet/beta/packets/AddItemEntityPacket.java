package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AddItemEntityPacket implements Packet {
	public int entity;
	public int x;
	public int y;
	public int z;
	public byte xa;
	public byte ya;
	public byte za;
	public int item;
	public int count;
	public int auxValue;

	public AddItemEntityPacket() {
	}

//    public AddItemEntityPacket(ItemEntity item) {
//        this.entity = item.id;
//        this.item = item.item.id;
//        this.count = item.item.count;
//        this.auxValue = item.item.getAuxValue();
//        this.x = Mth.floor(item.x * 32.0);
//        this.y = Mth.floor(item.y * 32.0);
//        this.z = Mth.floor(item.z * 32.0);
//        this.xa = (byte)(item.xd * 128.0);
//        this.ya = (byte)(item.yd * 128.0);
//        this.za = (byte)(item.zd * 128.0);
//    }

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.entity = buf.readInt();
		this.item = buf.readShort();
		this.count = buf.readByte();
		this.auxValue = buf.readShort();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.xa = buf.readByte();
		this.ya = buf.readByte();
		this.za = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.entity);
		buf.writeShort(this.item);
		buf.writeByte(this.count);
		buf.writeShort(this.auxValue);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeByte(this.xa);
		buf.writeByte(this.ya);
		buf.writeByte(this.za);
	}
}
