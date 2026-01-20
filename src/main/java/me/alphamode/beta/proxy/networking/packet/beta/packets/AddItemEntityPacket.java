package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class AddItemEntityPacket implements Packet {
	public int entity;
	public Vec3i position;
	public byte xa;
	public byte ya;
	public byte za;
	public BetaItemStack item;

	public AddItemEntityPacket() {
	}

//    public AddItemEntityPacket(ItemEntity item) {
//        this.entity = item.id;
//        this.item = item.item.id;
//        this.count = item.item.count;
//        this.aux = item.item.getAuxValue();
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
		this.item = BetaItemStack.CODEC.decode(buf);
		this.position = Vec3i.CODEC.decode(buf);
		this.xa = buf.readByte();
		this.ya = buf.readByte();
		this.za = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.entity);
		BetaItemStack.CODEC.encode(buf, this.item);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeByte(this.xa);
		buf.writeByte(this.ya);
		buf.writeByte(this.za);
	}
}
