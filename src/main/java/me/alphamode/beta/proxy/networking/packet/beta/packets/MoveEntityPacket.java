package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class MoveEntityPacket implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, MoveEntityPacket> CODEC = RecordPacket.codec(MoveEntityPacket::write, MoveEntityPacket::new);
	public int id;
	public byte xa;
	public byte ya;
	public byte za;
	public byte yRot;
	public byte xRot;
	public boolean hasRot = false;

	public MoveEntityPacket(final ByteBuf buf) {
		this.id = buf.readInt();
	}

	public MoveEntityPacket(int entityId) {
		this.id = entityId;
	}

	public void write(final ByteBuf buf) {
		buf.writeInt(this.id);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.MOVE_ENTITY;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[entityId=" + id + ", xa=" + this.xa + ", ya=" + this.ya + ", za=" + this.za + ", yRot=" + this.yRot + ", xRot=" + this.xRot + ", hasRot=" + this.hasRot + "]";
	}

	public static class Pos extends MoveEntityPacket {
		public static final StreamCodec<ByteBuf, Pos> CODEC = RecordPacket.codec(Pos::write, Pos::new);

		public Pos(final ByteBuf buf) {
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
		public void write(final ByteBuf buf) {
			super.write(buf);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_ENTITY_POS;
		}
	}

	public static class PosRot extends MoveEntityPacket {
		public static final StreamCodec<ByteBuf, PosRot> CODEC = RecordPacket.codec(PosRot::write, PosRot::new);

		public PosRot(final ByteBuf buf) {
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
		public void write(final ByteBuf buf) {
			super.write(buf);
			buf.writeByte(this.xa);
			buf.writeByte(this.ya);
			buf.writeByte(this.za);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_ENTITY_POS_ROT;
		}
	}

	public static class Rot extends MoveEntityPacket {
		public static final StreamCodec<ByteBuf, Rot> CODEC = RecordPacket.codec(Rot::write, Rot::new);

		public Rot(final ByteBuf buf) {
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
		public void write(final ByteBuf buf) {
			super.write(buf);
			buf.writeByte(this.yRot);
			buf.writeByte(this.xRot);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_ENTITY_ROT;
		}
	}
}
