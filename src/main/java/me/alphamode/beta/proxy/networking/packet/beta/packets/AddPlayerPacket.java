package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class AddPlayerPacket implements Packet {
	public static final int MAX_NAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> NAME_CODEC = ByteBufCodecs.stringUtf8(MAX_NAME_LENGTH);

	public int id;
	public String name;
	public Vec3i position;
	public byte yRot;
	public byte xRot;
	public int carriedItem;

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.name = NAME_CODEC.decode(buf);
		this.position = Vec3i.CODEC.decode(buf);
		this.yRot = buf.readByte();
		this.xRot = buf.readByte();
		this.carriedItem = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		NAME_CODEC.encode(buf, this.name);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeByte(this.yRot);
		buf.writeByte(this.xRot);
		buf.writeShort(this.carriedItem);
	}
}
