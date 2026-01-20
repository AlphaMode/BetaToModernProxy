package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class AddGlobalEntityPacket implements Packet {
	public int id;
	public Vec3i position;
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
		this.position = Vec3i.CODEC.decode(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.type);
		Vec3i.CODEC.encode(buf, this.position);
	}
}
