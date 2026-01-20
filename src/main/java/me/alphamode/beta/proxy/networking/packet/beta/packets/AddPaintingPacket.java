package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public class AddPaintingPacket implements Packet {
	public static final int MAX_PAINTING_MOTIVE = 13; // 13 is Painting.Motive.length
	public static final StreamCodec<ByteBuf, String> MOTIVE_CODEC = ByteBufCodecs.stringUtf8(MAX_PAINTING_MOTIVE);

	public int id;
	public int x;
	public int y;
	public int z;
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
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.dir = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		MOTIVE_CODEC.encode(buf, this.motive); // 13 is Painting.Motive.length
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.dir);
	}
}
