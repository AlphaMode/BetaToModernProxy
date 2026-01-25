package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public interface C2SMovePlayerPacket extends C2SPlayPacket {
	int FLAG_ON_GROUND = 1;
	int FLAG_HORIZONTAL_COLLISION = 2;

	static byte packFlags(final C2SMovePlayerPacket packet) {
		return packFlags(packet.onGround(), packet.horizontalCollision());
	}

	private static byte packFlags(final boolean onGround, final boolean horizontalCollision) {
		byte flags = 0;
		if (onGround) {
			flags |= FLAG_ON_GROUND;
		}

		if (horizontalCollision) {
			flags |= FLAG_HORIZONTAL_COLLISION;
		}

		return flags;
	}

	private static boolean unpackOnGround(final int flags) {
		return (flags & FLAG_ON_GROUND) != 0;
	}

	private static boolean unpackHorizontalCollision(final int flags) {
		return (flags & FLAG_HORIZONTAL_COLLISION) != 0;
	}

	default double getX(final double fallback) {
		return this.hasPosition() ? this.x() : fallback;
	}

	default double getY(final double fallback) {
		return this.hasPosition() ? this.y() : fallback;
	}

	default double getZ(final double fallback) {
		return this.hasPosition() ? this.z() : fallback;
	}

	default float getYRot(final float fallback) {
		return this.hasRotation() ? this.yRot() : fallback;
	}

	default float getXRot(final float fallback) {
		return this.hasRotation() ? this.xRot() : fallback;
	}

	double x();

	double y();

	double z();

	float yRot();

	float xRot();

	boolean onGround();

	boolean horizontalCollision();

	boolean hasPosition();

	boolean hasRotation();

	record Pos(double x, double y, double z, float yRot, float xRot, boolean onGround,
			   boolean horizontalCollision) implements C2SMovePlayerPacket {
		public static final StreamCodec<ByteBuf, Pos> CODEC = StreamCodec.composite(
				BasicStreamCodecs.DOUBLE, Pos::x,
				BasicStreamCodecs.DOUBLE, Pos::y,
				BasicStreamCodecs.DOUBLE, Pos::z,
				BasicStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				Pos::new
		);

		public Pos(final double x, final double y, final double z, final byte flags) {
			this(x, y, z, 0.0F, 0.0F, C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
		}

		@Override
		public boolean hasPosition() {
			return true;
		}

		@Override
		public boolean hasRotation() {
			return false;
		}

		@Override
		public ServerboundPlayPackets getType() {
			return ServerboundPlayPackets.MOVE_PLAYER_POS;
		}
	}

	record PosRot(double x, double y, double z, float yRot, float xRot, boolean onGround,
				  boolean horizontalCollision) implements C2SMovePlayerPacket {
		public static final StreamCodec<ByteBuf, PosRot> CODEC = StreamCodec.composite(
				BasicStreamCodecs.DOUBLE, PosRot::x,
				BasicStreamCodecs.DOUBLE, PosRot::y,
				BasicStreamCodecs.DOUBLE, PosRot::z,
				BasicStreamCodecs.FLOAT, PosRot::yRot,
				BasicStreamCodecs.FLOAT, PosRot::xRot,
				BasicStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				PosRot::new
		);

		public PosRot(final double x, final double y, final double z, final float yRot, final float xRot, final byte flags) {
			this(x, y, z, yRot, xRot, C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
		}

		@Override
		public boolean hasPosition() {
			return true;
		}

		@Override
		public boolean hasRotation() {
			return true;
		}

		@Override
		public ServerboundPlayPackets getType() {
			return ServerboundPlayPackets.MOVE_PLAYER_POS_ROT;
		}
	}

	record Rot(float yRot, float xRot, boolean onGround, boolean horizontalCollision) implements C2SMovePlayerPacket {
		public static final StreamCodec<ByteBuf, Rot> CODEC = StreamCodec.composite(
				BasicStreamCodecs.FLOAT, Rot::yRot,
				BasicStreamCodecs.FLOAT, Rot::xRot,
				BasicStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				Rot::new
		);

		public Rot(final float yRot, final float xRot, final byte flags) {
			this(yRot, xRot, C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
		}

		@Override
		public double x() {
			return 0;
		}

		@Override
		public double y() {
			return 0;
		}

		@Override
		public double z() {
			return 0;
		}

		@Override
		public boolean hasPosition() {
			return false;
		}

		@Override
		public boolean hasRotation() {
			return true;
		}

		@Override
		public ServerboundPlayPackets getType() {
			return ServerboundPlayPackets.MOVE_PLAYER_ROT;
		}
	}

	record StatusOnly(boolean onGround, boolean horizontalCollision) implements C2SMovePlayerPacket {
		public static final StreamCodec<ByteBuf, StatusOnly> CODEC = StreamCodec.composite(
				BasicStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				StatusOnly::new
		);

		public StatusOnly(final byte flags) {
			this(C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
		}

		@Override
		public double x() {
			return 0;
		}

		@Override
		public double y() {
			return 0;
		}

		@Override
		public double z() {
			return 0;
		}

		@Override
		public float yRot() {
			return 0;
		}

		@Override
		public float xRot() {
			return 0;
		}

		@Override
		public boolean hasPosition() {
			return false;
		}

		@Override
		public boolean hasRotation() {
			return false;
		}

		@Override
		public ServerboundPlayPackets getType() {
			return ServerboundPlayPackets.MOVE_PLAYER_STATUS_ONLY;
		}
	}
}
