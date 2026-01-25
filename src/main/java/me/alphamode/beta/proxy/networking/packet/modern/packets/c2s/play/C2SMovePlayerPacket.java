package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SMovePlayerPacket implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SMovePlayerPacket> CODEC = null; // TODO
	protected final double x;
	protected final double y;
	protected final double z;
	protected final float yRot;
	protected final float xRot;
	protected final boolean onGround;
	protected final boolean horizontalCollision;
	protected final boolean hasPos;
	protected final boolean hasRot;

	public C2SMovePlayerPacket(
			final double x,
			final double y,
			final double z,
			final float yRot,
			final float xRot,
			final boolean onGround,
			final boolean horizontalCollision,
			final boolean hasPos,
			final boolean hasRot
	) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yRot = yRot;
		this.xRot = xRot;
		this.onGround = onGround;
		this.horizontalCollision = horizontalCollision;
		this.hasPos = hasPos;
		this.hasRot = hasRot;
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.MOVE_PLAYER_POS;
	}

	public static class Pos extends C2SMovePlayerPacket {
		public Pos(final double x, final double y, final double z, final boolean onGround, final boolean horizontalCollision) {
			super(x, y, z, 0.0F, 0.0F, onGround, horizontalCollision, true, false);
		}
	}

	public static class Rot extends C2SMovePlayerPacket {
		public Rot(final float yRot, final float xRot, final boolean onGround, final boolean horizontalCollision) {
			super(0.0F, 0.0F, 0.0F, yRot, xRot, onGround, horizontalCollision, false, true);
		}
	}

	public static class PosRot extends C2SMovePlayerPacket {
		public PosRot(final double x, final double y, final double z, final float yRot, final float xRot, final boolean onGround, final boolean horizontalCollision) {
			super(x, y, z, yRot, xRot, onGround, horizontalCollision, true, true);
		}
	}

	public static class StatusOnly extends C2SMovePlayerPacket {
		public StatusOnly(final boolean onGround, final boolean horizontalCollision) {
			super(0.0, 0.0, 0.0, 0.0F, 0.0F, onGround, horizontalCollision, false, false);
		}
	}
}
