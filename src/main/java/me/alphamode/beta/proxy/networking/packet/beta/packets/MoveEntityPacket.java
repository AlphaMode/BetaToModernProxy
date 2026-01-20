package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MoveEntityPacket implements Packet {
	public int id;
	public byte xa;
	public byte ya;
	public byte za;
	public byte yRot;
	public byte xRot;
	public boolean hasRot = false;

	public MoveEntityPacket() {
	}

	public MoveEntityPacket(int entityId) {
		this.id = entityId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
	}

	public static class Pos extends MoveEntityPacket {
		public Pos() {
		}

		public Pos(int entityId, byte xa, byte ya, byte za) {
			super(entityId);
			this.xa = xa;
			this.ya = ya;
			this.za = za;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			super.read(buf, protocolVersion);
			this.xa = buf.readByte();
			this.ya = buf.readByte();
			this.za = buf.readByte();
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			super.write(buf, protocolVersion);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
		}
	}

	public static class PosRot extends MoveEntityPacket {
		public PosRot() {
			this.hasRot = true;
		}

		public PosRot(int entityId, byte xa, byte ya, byte za, byte yRot, byte xRot) {
			super(entityId);
			this.xa = xa;
			this.ya = ya;
			this.za = za;
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			super.read(buf, protocolVersion);
			this.xa = buf.readByte();
			this.ya = buf.readByte();
			this.za = buf.readByte();
			this.yRot = buf.readByte();
			this.xRot = buf.readByte();
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			super.write(buf, protocolVersion);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}
	}

	public static class Rot extends MoveEntityPacket {
		public Rot() {
			this.hasRot = true;
		}

		public Rot(int entityId, byte yRot, byte xRot) {
			super(entityId);
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
		}

		@Override
		public void read(final ByteBuf buf, final int protocolVersion) {
			super.read(buf, protocolVersion);
			this.yRot = buf.readByte();
			this.xRot = buf.readByte();
		}

		@Override
		public void write(final ByteBuf buf, final int protocolVersion) {
			super.write(buf, protocolVersion);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}
	}
}
