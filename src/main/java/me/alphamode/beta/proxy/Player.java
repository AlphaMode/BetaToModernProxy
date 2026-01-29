package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.entity.EntityPose;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.MovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.PlayerCommandPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SMovePlayerPacket;

public class Player {

    private final ServerConnection serverConnection;
    private final ClientConnection clientConnection;

    private final int id;
    private final EntityPose pose = new EntityPose();
    private int dimension;

    private boolean started = false;

    public double x;
    public double y;
    public double z;
    public double xd;
    public double yd;
    public double zd;
    public float yRot;
    public float xRot;
    private double xLast;
    private double yLast1;
    private double yLast2;
    private double zLast;
    private float yRotLast;
    private float xRotLast;

    private boolean onGround;
    private boolean lastOnGround = false;

    private boolean sneaking;
    private boolean lastSneaked = false;

    private int noSendTime = 0;

    public Player(final int id, final ClientConnection clientConnection) {
        this.id = id;
        this.serverConnection = clientConnection.getServerConnection();
        this.clientConnection = clientConnection;
    }

    public void setDimension(final int dimension) {
        this.dimension = dimension;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void tick() {
        sendPosition();
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

        final double deltaX = this.x - this.xLast;
        final double deltaY = this.pose.y0() - this.yLast1;
        final double deltaYView = this.y - this.yLast2;
        final double deltaZ = this.z - this.zLast;
        final double deltaYRot = this.yRot - this.yRotLast;
        final double deltaXRot = this.xRot - this.xRotLast;
        final boolean moved = deltaY != 0.0 || deltaYView != 0.0 || deltaX != 0.0 || deltaZ != 0.0;
        final boolean rotated = deltaYRot != 0.0 || deltaXRot != 0.0;
        // TODO: riding checks
        if (moved && rotated) {
            this.serverConnection.send(new MovePlayerPacket.PosRot(this.x, this.pose.y0(), this.y, this.z, this.yRot, this.xRot, this.onGround));
            this.noSendTime = 0;
        } else if (moved) {
            this.serverConnection.send(new MovePlayerPacket.Pos(this.x, this.pose.y0(), this.y, this.z, this.onGround));
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
            this.xLast = this.x;
            this.yLast1 = this.pose.y0();
            this.yLast2 = this.y;
            this.zLast = this.z;
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
        this.x = x;
        this.y = y;
        this.z = z;
//        float f = this.bbWidth / 2.0F;
//        float g = this.bbHeight;
//        this.bb.set(x - f, y - this.heightOffset + this.ySlideOffset, z - f, x + f, y - this.heightOffset + this.ySlideOffset + g, z + f);
    }

    public void absMoveTo(double x, double y, double z, float yRot, float xRot) {
        /*this.xo = */this.x = x;
        /*this.yo = */this.y = y;
        /*this.zo = */this.z = z;
        /*this.yRotO = */this.yRot = yRot;
        /*this.xRotO = */this.xRot = xRot;
//        this.ySlideOffset = 0.0F;
//        double d = this.yRotO - yRot;
//        if (d < -180.0) {
//            this.yRotO += 360.0F;
//        }
//
//        if (d >= 180.0) {
//            this.yRotO -= 360.0F;
//        }

        this.setPos(this.x, this.y, this.z);
        this.setRot(yRot, xRot);
    }

    public void updateFromClient(final C2SMovePlayerPacket packet) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        float newYRot = this.yRot;
        float newXRot = this.xRot;
        if (packet.hasPosition()) {
            newX = packet.x();
            newY = packet.y();
            newZ = packet.z();
        }

        if (packet.hasRotation()) {
            newYRot = packet.yRot();
            newXRot = packet.xRot();
        }

        absMoveTo(newX, newY, newZ, newYRot, newXRot);

//        this.onGround = packet.onGround();
        if (!this.started) {
            this.started = true;
        }
    }
}
