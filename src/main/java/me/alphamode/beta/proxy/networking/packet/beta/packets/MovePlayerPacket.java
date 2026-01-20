package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MovePlayerPacket implements Packet {
	public double x;
	public double y;
	public double z;
	public double yView;
	public float yRot;
	public float xRot;
	public boolean onGround;
	public boolean hasPos;
	public boolean hasRot;

	public MovePlayerPacket() {
	}

	public MovePlayerPacket(final boolean onGround) {
		this.onGround = onGround;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.onGround = buf.readByte() != 0;
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.onGround ? 1 : 0);
	}

	public static class Pos extends MovePlayerPacket {
		public Pos() {
			this.hasPos = true;
		}

		public Pos(double x, double y, double yView, double z, boolean onGround) {
			this.x = x;
			this.y = y;
			this.yView = yView;
			this.z = z;
			this.onGround = onGround;
			this.hasPos = true;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			this.x = buf.readDouble();
			this.y = buf.readDouble();
			this.yView = buf.readDouble();
			this.z = buf.readDouble();
			super.read(buf, protocolVersion);
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.yView);
			buf.writeDouble(this.z);
			super.write(buf, protocolVersion);
		}
	}

	public static class PosRot extends MovePlayerPacket {
		public PosRot() {
			this.hasRot = true;
			this.hasPos = true;
		}

		public PosRot(double x, double y, double yView, double z, float yRot, float xRot, boolean onGround) {
			this.x = x;
			this.y = y;
			this.yView = yView;
			this.z = z;
			this.yRot = yRot;
			this.xRot = xRot;
			this.onGround = onGround;
			this.hasRot = true;
			this.hasPos = true;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			this.x = buf.readDouble();
			this.y = buf.readDouble();
			this.yView = buf.readDouble();
			this.z = buf.readDouble();
			this.yRot = buf.readFloat();
			this.xRot = buf.readFloat();
			super.read(buf, protocolVersion);
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.yView);
			buf.writeDouble(this.z);
			buf.writeFloat(this.yRot);
			buf.writeFloat(this.xRot);
			super.write(buf, protocolVersion);
		}
	}

	public static class Rot extends MovePlayerPacket {
		public Rot() {
			this.hasRot = true;
		}

		public Rot(float yRot, float xRot, boolean onGround) {
			this.yRot = yRot;
			this.xRot = xRot;
			this.onGround = onGround;
			this.hasRot = true;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			this.yRot = buf.readFloat();
			this.xRot = buf.readFloat();
			super.read(buf, protocolVersion);
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			buf.writeFloat(this.yRot);
			buf.writeFloat(this.xRot);
			super.write(buf, protocolVersion);
		}
	}
}
