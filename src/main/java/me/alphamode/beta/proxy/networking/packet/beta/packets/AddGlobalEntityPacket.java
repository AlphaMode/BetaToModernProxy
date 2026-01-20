package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AddGlobalEntityPacket implements Packet {
	public int id;
	public int x;
	public int y;
	public int z;
	public int type;

	public AddGlobalEntityPacket() {
	}

//    public AddGlobalEntityPacket(final Entity entitu) {
//        this.id = entitu.id;
//        this.x = Mth.floor(entitu.x * 32.0);
//        this.y = Mth.floor(entitu.y * 32.0);
//        this.z = Mth.floor(entitu.z * 32.0);
//        if (entitu instanceof LightningBolt) {
//            this.type = 1;
//        }
//    }

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.type = buf.readByte();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.type);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}
}
