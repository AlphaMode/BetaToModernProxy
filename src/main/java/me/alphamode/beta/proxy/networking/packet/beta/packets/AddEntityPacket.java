package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class AddEntityPacket implements Packet {
	public int id;
	public Vec3i position;
	public int xd;
	public int yd;
	public int zd;
	public int entityId;
	public int data;

	public AddEntityPacket() {
	}

//    public AddEntityPacket(Entity entity, int entityId) {
//        this(entity, entityId, 0);
//    }
//
//    public AddEntityPacket(Entity entity, int entityId, int data) {
//        this.id = entity.id;
//        this.x = Mth.floor(entity.x * 32.0);
//        this.y = Mth.floor(entity.y * 32.0);
//        this.z = Mth.floor(entity.z * 32.0);
//        this.entityId = entityId;
//        this.data = data;
//        if (data > 0) {
//            double d = entity.xd;
//            double e = entity.yd;
//            double f = entity.zd;
//            double g = 3.9;
//            if (d < -g) {
//                d = -g;
//            }
//
//            if (e < -g) {
//                e = -g;
//            }
//
//            if (f < -g) {
//                f = -g;
//            }
//
//            if (d > g) {
//                d = g;
//            }
//
//            if (e > g) {
//                e = g;
//            }
//
//            if (f > g) {
//                f = g;
//            }
//
//            this.xd = (int)(d * 8000.0);
//            this.yd = (int)(e * 8000.0);
//            this.zd = (int)(f * 8000.0);
//        }
//    }

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.entityId = buf.readByte();
		this.position = Vec3i.CODEC.decode(buf);
		this.data = buf.readInt();
		if (this.data > 0) {
			this.xd = buf.readShort();
			this.yd = buf.readShort();
			this.zd = buf.readShort();
		}
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.entityId);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeInt(this.data);
		if (this.data > 0) {
			buf.writeShort(this.xd);
			buf.writeShort(this.yd);
			buf.writeShort(this.zd);
		}
	}
}
