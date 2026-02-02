package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public sealed interface S2CMoveEntityPacket extends S2CPlayPacket permits S2CMoveEntityPacket.Pos, S2CMoveEntityPacket.PosRot, S2CMoveEntityPacket.Rot {
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

	default int entityId() {
		return 0;
	}

	default short x() {
		return 0;
	}

	default short y() {
		return 0;
	}

	default short z() {
		return 0;
	}

	default byte yRot() {
		return 0;
	}

	default byte xRot() {
		return 0;
	}

	boolean onGround();

	boolean hasPosition();

	boolean hasRotation();

	record Pos(int entityId, short x, short y, short z, byte yRot, byte xRot,
			   boolean onGround) implements S2CMoveEntityPacket {
		public static final StreamCodec<ByteBuf, Pos> CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT, Pos::entityId,
				BasicStreamCodecs.SHORT, Pos::x,
				BasicStreamCodecs.SHORT, Pos::y,
				BasicStreamCodecs.SHORT, Pos::z,
				BasicStreamCodecs.BOOL, Pos::onGround,
				Pos::new
		);

		public Pos(final int entityId, final short x, final short y, final short z, final boolean onGround) {
			this(entityId, x, y, z, (byte) 0, (byte) 0, onGround);
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
		public ClientboundPlayPackets getType() {
			return ClientboundPlayPackets.MOVE_ENTITY_POS;
		}
	}

	record PosRot(int entityId, short x, short y, short z, byte yRot, byte xRot,
				  boolean onGround) implements S2CMoveEntityPacket {
		public static final StreamCodec<ByteBuf, PosRot> CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT, PosRot::entityId,
				BasicStreamCodecs.SHORT, PosRot::x,
				BasicStreamCodecs.SHORT, PosRot::y,
				BasicStreamCodecs.SHORT, PosRot::z,
				BasicStreamCodecs.BYTE, PosRot::yRot,
				BasicStreamCodecs.BYTE, PosRot::xRot,
				BasicStreamCodecs.BOOL, PosRot::onGround,
				PosRot::new
		);

		@Override
		public boolean hasPosition() {
			return true;
		}

		@Override
		public boolean hasRotation() {
			return true;
		}

		@Override
		public ClientboundPlayPackets getType() {
			return ClientboundPlayPackets.MOVE_ENTITY_POS_ROT;
		}
	}

	record Rot(int entityId, byte yRot, byte xRot, boolean onGround) implements S2CMoveEntityPacket {
		public static final StreamCodec<ByteBuf, Rot> CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT, Rot::entityId,
				BasicStreamCodecs.BYTE, Rot::yRot,
				BasicStreamCodecs.BYTE, Rot::xRot,
				BasicStreamCodecs.BOOL, Rot::onGround,
				Rot::new
		);

		@Override
		public boolean hasPosition() {
			return false;
		}

		@Override
		public boolean hasRotation() {
			return true;
		}

		@Override
		public ClientboundPlayPackets getType() {
			return ClientboundPlayPackets.MOVE_ENTITY_ROT;
		}
	}
}
