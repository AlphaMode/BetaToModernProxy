package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MovePlayerPacket implements Packet {
    public double x;
    public double y;
    public double z;
    public double yView;
    public float yRot;
    public float xRot;
    public boolean onGround;
    public boolean hasPos;
    public boolean hasRot;

    public MovePlayerPacket() {
    }

    public MovePlayerPacket(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.onGround = data.readByte() != 0;
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.onGround ? 1 : 0);
    }

    public static class Pos extends MovePlayerPacket {
        public Pos() {
            this.hasPos = true;
        }

        public Pos(double x, double y, double yView, double z, boolean onGround) {
            this.x = x;
            this.y = y;
            this.yView = yView;
            this.z = z;
            this.onGround = onGround;
            this.hasPos = true;
        }

        @Override
        public void read(ByteBuf data, int protocolVersion) {
            this.x = data.readDouble();
            this.y = data.readDouble();
            this.yView = data.readDouble();
            this.z = data.readDouble();
            super.read(data, protocolVersion);
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            data.writeDouble(this.x);
            data.writeDouble(this.y);
            data.writeDouble(this.yView);
            data.writeDouble(this.z);
            super.write(data, protocolVersion);
        }
    }

    public static class PosRot extends MovePlayerPacket {
        public PosRot() {
            this.hasRot = true;
            this.hasPos = true;
        }

        public PosRot(double x, double y, double yView, double z, float yRot, float xRot, boolean onGround) {
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
        public void read(ByteBuf data, int protocolVersion) {
            this.x = data.readDouble();
            this.y = data.readDouble();
            this.yView = data.readDouble();
            this.z = data.readDouble();
            this.yRot = data.readFloat();
            this.xRot = data.readFloat();
            super.read(data, protocolVersion);
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            data.writeDouble(this.x);
            data.writeDouble(this.y);
            data.writeDouble(this.yView);
            data.writeDouble(this.z);
            data.writeFloat(this.yRot);
            data.writeFloat(this.xRot);
            super.write(data, protocolVersion);
        }
    }

    public static class Rot extends MovePlayerPacket {
        public Rot() {
            this.hasRot = true;
        }

        public Rot(float yRot, float xRot, boolean onGround) {
            this.yRot = yRot;
            this.xRot = xRot;
            this.onGround = onGround;
            this.hasRot = true;
        }

        @Override
        public void read(ByteBuf data, int protocolVersion) {
            this.yRot = data.readFloat();
            this.xRot = data.readFloat();
            super.read(data, protocolVersion);
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            data.writeFloat(this.yRot);
            data.writeFloat(this.xRot);
            super.write(data, protocolVersion);
        }
    }
}
