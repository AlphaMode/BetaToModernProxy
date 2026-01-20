package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

import java.util.HashSet;
import java.util.Set;

public class ExplodePacket implements Packet {
	public double x;
	public double y;
	public double z;
	public float radius;
	public Set<Vec3i> toBlow;

	public ExplodePacket() {
	}

	public ExplodePacket(final double x, final double y, final double z, final float radius, Set<Vec3i> toBlow) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.toBlow = new HashSet<>(toBlow);
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
		this.radius = buf.readFloat();

		final int size = buf.readInt();
		this.toBlow = new HashSet<>();

		final Vec3i origin = new Vec3i((int) this.x, (int) this.y, (int) this.z);
		for (int i = 0; i < size; i++) {
			this.toBlow.add(Vec3i.relative(origin).decode(buf));
		}
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeFloat(this.radius);
		buf.writeInt(this.toBlow.size());

		final StreamCodec<ByteBuf, Vec3i> codec = Vec3i.relative(new Vec3i((int) this.x, (int) this.y, (int) this.z));
		for (final Vec3i tilePos : this.toBlow) {
			codec.encode(buf, tilePos);
		}
	}
}
