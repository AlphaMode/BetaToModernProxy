package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class AddPaintingPacket implements Packet {
	public static final int MAX_PAINTING_MOTIVE = 13; // 13 is Painting.Motive.length
	public static final StreamCodec<ByteBuf, String> MOTIVE_CODEC = ByteBufCodecs.stringUtf8(MAX_PAINTING_MOTIVE);

	public int id;
	public Vec3i position;
	public int dir;
	public String motive;

	public AddPaintingPacket() {
	}

//    public AddPaintingPacket(Painting painting) {
//        this.id = painting.id;
//        this.x = painting.xTile;
//        this.y = painting.yTile;
//        this.z = painting.zTile;
//        this.dir = painting.direction;
//        this.motive = painting.motive.name;
//    }

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.motive = MOTIVE_CODEC.decode(buf);
		this.position = Vec3i.CODEC.decode(buf);
		this.dir = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		MOTIVE_CODEC.encode(buf, this.motive);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeInt(this.dir);
	}
}
