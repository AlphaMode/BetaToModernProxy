package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class MovePlayerPacket implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, MovePlayerPacket> CODEC = RecordPacket.codec(MovePlayerPacket::write, MovePlayerPacket::new);
	public double x;
	public double y;
	public double z;
	public double yView;
	public float yRot;
	public float xRot;
	public boolean onGround;
	public boolean hasPos;
	public boolean hasRot;

	public MovePlayerPacket(ByteBuf buf) {
		this.onGround = buf.readBoolean();
	}

	public MovePlayerPacket(final boolean onGround) {
		this.onGround = onGround;
	}

	public void write(final ByteBuf buf) {
		buf.writeBoolean(this.onGround);
//		buf.writeByte(this.onGround ? 1 : 0);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.MOVE_PLAYER;
	}

	public static class Pos extends MovePlayerPacket {
		public static final StreamCodec<ByteBuf, Pos> CODEC = RecordPacket.codec(Pos::write, Pos::new);

		public Pos(final ByteBuf buf) {
			double x = buf.readDouble();
			double y = buf.readDouble();
			double yView = buf.readDouble();
			double z = buf.readDouble();
			super(buf);
			this.x = x;
			this.y = y;
			this.yView = yView;
			this.z = z;
			this.hasPos = true;
		}

		public Pos(double x, double y, double yView, double z, boolean onGround) {
			super(onGround);
			this.x = x;
			this.y = y;
			this.yView = yView;
			this.z = z;
			this.hasPos = true;
		}

		@Override
		public void write(final ByteBuf buf) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.yView);
			buf.writeDouble(this.z);
			super.write(buf);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_POS;
		}
	}

	public static class PosRot extends MovePlayerPacket {
		public static final StreamCodec<ByteBuf, PosRot> CODEC = RecordPacket.codec(PosRot::write, PosRot::new);

		public PosRot(ByteBuf buf) {
			double x = buf.readDouble();
			double y = buf.readDouble();
			double yView = buf.readDouble();
			double z = buf.readDouble();
			float yRot = buf.readFloat();
			float xRot = buf.readFloat();
			super(buf);
			this.x = x;
			this.y = y;
			this.yView = yView;
			this.z = z;
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
			this.hasPos = true;
		}

		public PosRot(double x, double y, double yView, double z, float yRot, float xRot, boolean onGround) {
			super(onGround);
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
		public void write(final ByteBuf buf) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.yView);
			buf.writeDouble(this.z);
			buf.writeFloat(this.yRot);
			buf.writeFloat(this.xRot);
			super.write(buf);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_POS_ROT;
		}
	}

	public static class Rot extends MovePlayerPacket {
		public static final StreamCodec<ByteBuf, Rot> CODEC = RecordPacket.codec(Rot::write, Rot::new);

		public Rot(ByteBuf buf) {
			float yRot = buf.readFloat();
			float xRot = buf.readFloat();
			super(buf);
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
		}

		public Rot(float yRot, float xRot, boolean onGround) {
			super(onGround);
			this.yRot = yRot;
			this.xRot = xRot;
			this.hasRot = true;
		}

		@Override
		public void write(final ByteBuf buf) {
			buf.writeFloat(this.yRot);
			buf.writeFloat(this.xRot);
			super.write(buf);
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_ROT;
		}
	}
}
