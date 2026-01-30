package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.entity.EntityPose;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.MovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.PlayerCommandPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SMovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayerPositionPacket;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.modern.PositionMoveRotation;

import java.util.Collections;

public class Player {

    private final ServerConnection serverConnection;
    private final ClientConnection clientConnection;

    private final int id;
    public float heightOffset = 1.62F;
    public float ySlideOffset = 0.0F;
    public float bbHeight = 1.8F;
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

    protected void setSize(float width, float height) {
//        this.bbWidth = width;
        this.bbHeight = height;
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
        float height = this.bbHeight;
        this.pose.y0(y - this.heightOffset + this.ySlideOffset);
        this.pose.y1(y - this.heightOffset + this.ySlideOffset + height);
    }

    public void absMoveTo(double x, double y, double z, float yRot, float xRot) {
        /*this.xo = */this.x = x;
        /*this.yo = */this.y = y;
        /*this.zo = */this.z = z;
        /*this.yRotO = */this.yRot = yRot;
        /*this.xRotO = */this.xRot = xRot;
        this.ySlideOffset = 0.0F;
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

    public void setStarted(final boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void updateFromClient(final C2SMovePlayerPacket packet) {
        double newX = this.x;
        double newY = this.y;
        double newZ = this.z;
        float newYRot = this.yRot;
        float newXRot = this.xRot;
        if (packet.hasPosition()) {
            newX = packet.x();
            newY = packet.y() + this.heightOffset;
            newZ = packet.z();
        }

        if (packet.hasRotation()) {
            newYRot = packet.yRot();
            newXRot = packet.xRot();
        }

        absMoveTo(newX, newY, newZ, newYRot, newXRot);

//        this.onGround = packet.onGround();

        // Wait for beta server to send out pos first
        if (this.started) {

            switch (packet) {
                case C2SMovePlayerPacket.Pos p ->
                        serverConnection.send(new MovePlayerPacket.Pos(newX, this.pose.y0(), newY, newZ, p.onGround()));
                case C2SMovePlayerPacket.Rot p ->
                        serverConnection.send(new MovePlayerPacket.Rot(newYRot, newXRot, p.onGround()));
                case C2SMovePlayerPacket.PosRot p ->
                        serverConnection.send(new MovePlayerPacket.PosRot(newX, this.pose.y0(), newY, newZ, newYRot, newXRot, p.onGround()));
                case C2SMovePlayerPacket.StatusOnly p ->
                        serverConnection.send(new MovePlayerPacket.StatusOnly(p.onGround()));
            }
        }
    }

    public void updateFromServer(final MovePlayerPacket packet) {
        double x = this.x;
        double y = this.y;
        double z = this.z;
        float yRot = this.yRot;
        float xRot = this.xRot;
        if (packet.hasPos()) {
            x = packet.x();
            y = packet.y();
            z = packet.z();
        }

        if (packet.hasRot()) {
            yRot = packet.yRot();
            xRot = packet.xRot();
        }

//        this.ySlideOffset = 0.0F;
        this.xd = this.yd = this.zd = 0.0;
        this.absMoveTo(x, y, z, yRot, xRot);
        this.clientConnection.send(new S2CPlayerPositionPacket(this.id, new PositionMoveRotation(new Vec3d(this.x, this.y - this.heightOffset, this.z), Vec3d.ZERO, packet.yRot(), packet.xRot()), Collections.emptySet()));
        this.serverConnection.send(new MovePlayerPacket.Pos(this.x, this.pose.y0(), this.y, this.z, packet.onGround()));
        if (!this.started) {
//            this.minecraft.player.xo = this.minecraft.player.x;
//            this.minecraft.player.yo = this.minecraft.player.y;
//            this.minecraft.player.zo = this.minecraft.player.z;
            this.started = true;
//            this.minecraft.setScreen(null);
        }
    }
}
