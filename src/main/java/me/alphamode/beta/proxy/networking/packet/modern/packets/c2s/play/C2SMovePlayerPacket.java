package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public sealed interface C2SMovePlayerPacket extends C2SPlayPacket permits C2SMovePlayerPacket.Pos, C2SMovePlayerPacket.PosRot, C2SMovePlayerPacket.Rot, C2SMovePlayerPacket.StatusOnly {
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

	default double x() {
		return 0;
	}

	default double y() {
		return 0;
	}

	default double z() {
		return 0;
	}

	default float yRot() {
		return 0;
	}

	default float xRot() {
		return 0;
	}

	boolean onGround();

	boolean horizontalCollision();

	boolean hasPosition();

	boolean hasRotation();

	record Pos(double x, double y, double z, float yRot, float xRot, boolean onGround,
			   boolean horizontalCollision) implements C2SMovePlayerPacket {
		public static final StreamCodec<ByteStream, Pos> CODEC = StreamCodec.composite(
				CommonStreamCodecs.DOUBLE,
				Pos::x,
				CommonStreamCodecs.DOUBLE,
				Pos::y,
				CommonStreamCodecs.DOUBLE,
				Pos::z,
				CommonStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
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
		public static final StreamCodec<ByteStream, PosRot> CODEC = StreamCodec.composite(
				CommonStreamCodecs.DOUBLE, PosRot::x,
				CommonStreamCodecs.DOUBLE, PosRot::y,
				CommonStreamCodecs.DOUBLE, PosRot::z,
				CommonStreamCodecs.FLOAT, PosRot::yRot,
				CommonStreamCodecs.FLOAT, PosRot::xRot,
				CommonStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
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
		public static final StreamCodec<ByteStream, Rot> CODEC = StreamCodec.composite(
				CommonStreamCodecs.FLOAT, Rot::yRot,
				CommonStreamCodecs.FLOAT, Rot::xRot,
				CommonStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				Rot::new
		);

		public Rot(final float yRot, final float xRot, final byte flags) {
			this(yRot, xRot, C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
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
		public static final StreamCodec<ByteStream, StatusOnly> CODEC = StreamCodec.composite(
				CommonStreamCodecs.BYTE, C2SMovePlayerPacket::packFlags,
				StatusOnly::new
		);

		public StatusOnly(final byte flags) {
			this(C2SMovePlayerPacket.unpackOnGround(flags), C2SMovePlayerPacket.unpackHorizontalCollision(flags));
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
