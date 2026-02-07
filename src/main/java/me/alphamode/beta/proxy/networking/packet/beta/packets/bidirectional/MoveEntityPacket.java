package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class MoveEntityPacket implements BetaPacket {
	public static final StreamCodec<ByteStream, MoveEntityPacket> CODEC = AbstractPacket.codec(MoveEntityPacket::write, MoveEntityPacket::new);
	public int entityId;
	public byte xa;
	public byte ya;
	public byte za;
	public byte yRot;
	public byte xRot;
	public boolean hasRot = false;

	public MoveEntityPacket(final ByteStream buf) {
		this.entityId = buf.readInt();
	}

	public MoveEntityPacket(int entityId) {
		this.entityId = entityId;
	}

	public void write(final ByteStream buf) {
		buf.writeInt(this.entityId);
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.MOVE_ENTITY;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[entityId=" + entityId + ", xa=" + this.xa + ", ya=" + this.ya + ", za=" + this.za + ", yRot=" + this.yRot + ", xRot=" + this.xRot + ", hasRot=" + this.hasRot + "]";
	}

	public static class Pos extends MoveEntityPacket {
		public static final StreamCodec<ByteStream, Pos> CODEC = AbstractPacket.codec(Pos::write, Pos::new);

		public Pos(final ByteStream buf) {
			super(buf);
			this.xa = buf.readByte();
			this.ya = buf.readByte();
			this.za = buf.readByte();
		}

		public Pos(int entityId, byte xa, byte ya, byte za) {
			super(entityId);
			this.xa = xa;
			this.ya = ya;
			this.za = za;
		}

		@Override
		public void write(final ByteStream buf) {
			super.write(buf);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
		}

		@Override
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_ENTITY_POS;
		}
	}

	public static class PosRot extends MoveEntityPacket {
		public static final StreamCodec<ByteStream, PosRot> CODEC = AbstractPacket.codec(PosRot::write, PosRot::new);

		public PosRot(final ByteStream buf) {
			super(buf);
			this.xa = buf.readByte();
			this.ya = buf.readByte();
			this.za = buf.readByte();
			this.yRot = buf.readByte();
			this.xRot = buf.readByte();
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
		public void write(final ByteStream buf) {
			super.write(buf);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}

		@Override
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_ENTITY_POS_ROT;
		}
	}

	public static class Rot extends MoveEntityPacket {
		public static final StreamCodec<ByteStream, Rot> CODEC = AbstractPacket.codec(Rot::write, Rot::new);

		public Rot(final ByteStream buf) {
			super(buf);
			this.yRot = buf.readByte();
			this.xRot = buf.readByte();
			this.hasRot = true;
		}

		public Rot(int entityId, byte yRot, byte xRot) {
			super(entityId);
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
		}

		@Override
		public void write(final ByteStream buf) {
			super.write(buf);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}

		@Override
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_ENTITY_ROT;
		}
	}
}
