package me.alphamode.beta.proxy.entity;

import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.MovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.PlayerCommandPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SMovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayerPositionPacket;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.modern.PositionMoveRotation;

import java.util.Collections;
import java.util.Map;

public class Player {
	private static final EntityDimensions STANDING_DIMENSIONS = new EntityDimensions(1.62F, 1.2F, 1.8F);
	private static final Map<Pose, EntityDimensions> PLAYER_DIMENSION_MAP = Map.of(
			Pose.STANDING, STANDING_DIMENSIONS,
			Pose.CROUCHING, STANDING_DIMENSIONS.withEyeHeight(1.54F),
			Pose.SLEEPING, STANDING_DIMENSIONS // TODO
	);

	private final ServerConnection serverConnection;
	private final ClientConnection clientConnection;

	private final int id;
	public float ySlideOffset = 0.0F;
	private Box box = new Box(0, 0, 0, 0, 0, 0);
	private EntityDimensions dimensions = STANDING_DIMENSIONS;
	private int dimension;

	private boolean started = false;

	private Vec3d position = Vec3d.ZERO;
	private Vec3d deltaMovement = Vec3d.ZERO;
	public float yRot;
	public float xRot;
	private Vec3d lastPosition = Vec3d.ZERO;
	private double lastEyeHeight;
	private float yRotLast;
	private float xRotLast;

	private boolean onGround;
	private boolean lastOnGround = false;

	private Pose pose;
	private boolean lastSneaked = false;

	private int noSendTime = 0;

	public Player(final int id, final ClientConnection clientConnection) {
		this.id = id;
		this.serverConnection = clientConnection.getServerConnection();
		this.clientConnection = clientConnection;
		this.setPose(Pose.STANDING);
		this.setPos(0, 0, 0);
	}

	public int getId() {
		return this.id;
	}

	public void setDimension(final int dimension) {
		this.dimension = dimension;
	}

	public void setPose(final Pose pose) {
		this.pose = pose;
		this.dimensions = PLAYER_DIMENSION_MAP.get(pose);
	}

	public void setSneaking(boolean sneaking) {
		this.setPose(sneaking ? Pose.CROUCHING : Pose.STANDING);
	}

	public boolean isSneaking() {
		return this.pose == Pose.CROUCHING;
	}

	public void tick() {
		this.sendPosition();
	}

	public void sendPosition() {
		final boolean isSneaking = this.isSneaking();
		if (isSneaking != this.lastSneaked) {
			if (isSneaking) {
				this.serverConnection.send(new PlayerCommandPacket(this.id, PlayerCommandPacket.Action.CROUCH));
			} else {
				this.serverConnection.send(new PlayerCommandPacket(this.id, PlayerCommandPacket.Action.UNCROUCH));
			}

			this.lastSneaked = isSneaking;
		}

		final double deltaX = this.position.x() - this.lastPosition.x();
		final double deltaY = this.box.minY() - this.lastPosition.y();
		final double deltaYView = this.position.y() - this.lastEyeHeight;
		final double deltaZ = this.position.z() - this.lastPosition.z();
		final double deltaYRot = this.yRot - this.yRotLast;
		final double deltaXRot = this.xRot - this.xRotLast;
		final boolean moved = deltaY != 0.0 || deltaYView != 0.0 || deltaX != 0.0 || deltaZ != 0.0;
		final boolean rotated = deltaYRot != 0.0 || deltaXRot != 0.0;
		// TODO: riding checks
		if (moved && rotated) {
			this.serverConnection.send(new MovePlayerPacket.PosRot(this.position.x(), this.box.minY(), this.position.y(), this.position.z(), this.yRot, this.xRot, this.onGround));
			this.noSendTime = 0;
		} else if (moved) {
			this.serverConnection.send(new MovePlayerPacket.Pos(this.position.x(), this.box.minY(), this.position.y(), this.position.z(), this.onGround));
			this.noSendTime = 0;
		} else if (rotated) {
			this.serverConnection.send(new MovePlayerPacket.Rot(this.yRot, this.xRot, this.onGround));
			this.noSendTime = 0;
		} else {
			this.serverConnection.send(new MovePlayerPacket.StatusOnly(this.onGround));
			if (this.lastOnGround == this.onGround && this.noSendTime <= 200) {
				this.noSendTime++;
			} else {
				this.noSendTime = 0;
			}
		}

		this.lastOnGround = this.onGround;
		if (moved) {
			this.lastPosition = new Vec3d(this.position.x(), this.box.minY(), this.position.z());
			this.lastEyeHeight = this.position.y();
		}

		if (rotated) {
			this.yRotLast = this.yRot;
			this.xRotLast = this.xRot;
		}
	}

	protected void setRot(float yRot, float xRot) {
		this.yRot = yRot % 360.0F;
		this.xRot = xRot % 360.0F;
	}

	public void setPos(double x, double y, double z) {
		this.position = new Vec3d(x, y, z);
		final float width = this.dimensions.width() / 2.0F;
		final float height = this.dimensions.height();
		final double y0 = y - this.dimensions.eyeHeight() + this.ySlideOffset;
		final double y1 = y - this.dimensions.eyeHeight() + this.ySlideOffset + height;
		this.box = new Box(x - width, y0, z - width, x + width, y1, z + width);
	}

	public void absMoveTo(double x, double y, double z, float yRot, float xRot) {
		/*this.yRotO = */
		this.yRot = yRot;
		/*this.xRotO = */
		this.xRot = xRot;
		this.ySlideOffset = 0.0F;
//        final double dyRot = this.yRotO - yRot;
//        if (dyRot < -180.0) {
//            this.yRotO += 360.0F;
//        }
//
//        if (dyRot >= 180.0) {
//            this.yRotO -= 360.0F;
//        }

		this.setPos(x, y, z);
		this.setRot(yRot, xRot);
	}

	public void setStarted(final boolean started) {
		this.started = started;
	}

	public boolean isStarted() {
		return this.started;
	}

	public void updateFromClient(final C2SMovePlayerPacket packet) {
		Vec3d newPosition = this.position;
		float newYRot = this.yRot;
		float newXRot = this.xRot;
		if (packet.hasPosition()) {
			newPosition = new Vec3d(packet.x(), packet.y() + this.dimensions.eyeHeight(), packet.z());
		}

		if (packet.hasRotation()) {
			newYRot = packet.yRot();
			newXRot = packet.xRot();
		}

		this.absMoveTo(newPosition.x(), newPosition.y(), newPosition.z(), newYRot, newXRot);

		this.onGround = packet.onGround();
		if (this.started) {
			// Wait for beta server to send out pos first
			switch (packet) {
				case C2SMovePlayerPacket.Pos p ->
						serverConnection.send(new MovePlayerPacket.Pos(newPosition.x(), this.box.minY(), newPosition.y(), newPosition.z(), p.onGround()));
				case C2SMovePlayerPacket.Rot p ->
						serverConnection.send(new MovePlayerPacket.Rot(newYRot, newXRot, p.onGround()));
				case C2SMovePlayerPacket.PosRot p ->
						serverConnection.send(new MovePlayerPacket.PosRot(newPosition.x(), this.box.minY(), newPosition.y(), newPosition.z(), newYRot, newXRot, p.onGround()));
				case C2SMovePlayerPacket.StatusOnly p ->
						serverConnection.send(new MovePlayerPacket.StatusOnly(p.onGround()));
			}
		}
	}

	public void updateFromServer(final MovePlayerPacket packet) {
		Vec3d position = this.position;
		float yRot = this.yRot;
		float xRot = this.xRot;
		if (packet.hasPos()) {
			position = new Vec3d(packet.x(), packet.y(), packet.z());
		}

		if (packet.hasRot()) {
			yRot = packet.yRot();
			xRot = packet.xRot();
		}

		this.ySlideOffset = 0.0F;
		this.deltaMovement = Vec3d.ZERO;
		this.absMoveTo(position.x(), position.y(), position.z(), yRot, xRot);
		this.clientConnection.send(new S2CPlayerPositionPacket(this.id, new PositionMoveRotation(new Vec3d(this.position.x(), this.position.y() - this.dimensions.eyeHeight(), this.position.z()), Vec3d.ZERO, packet.yRot(), packet.xRot()), Collections.emptySet()));
		this.serverConnection.send(new MovePlayerPacket.Pos(this.position.x(), this.box.minY(), this.position.y(), this.position.z(), packet.onGround()));
		if (!this.started) {
			this.started = true;
		}
	}
}
