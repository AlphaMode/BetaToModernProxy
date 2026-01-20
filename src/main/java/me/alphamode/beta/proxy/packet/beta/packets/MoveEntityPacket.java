package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class MoveEntityPacket implements Packet {
    public int id;
    public byte xa;
    public byte ya;
    public byte za;
    public byte yRot;
    public byte xRot;
    public boolean hasRot = false;

    public MoveEntityPacket() {
    }

    public MoveEntityPacket(int entityId) {
        this.id = entityId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
    }

    public static class Pos extends MoveEntityPacket {
        public Pos() {
        }

        public Pos(int entityId, byte xa, byte ya, byte za) {
            super(entityId);
            this.xa = xa;
            this.ya = ya;
            this.za = za;
        }

        @Override
        public void read(ByteBuf data, int protocolVersion) {
            super.read(data, protocolVersion);
            this.xa = data.readByte();
            this.ya = data.readByte();
            this.za = data.readByte();
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            super.write(data, protocolVersion);
            data.writeByte(this.xa);
            data.writeByte(this.ya);
            data.writeByte(this.za);
        }
    }

    public static class PosRot extends MoveEntityPacket {
        public PosRot() {
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
        public void read(ByteBuf data, int protocolVersion) {
            super.read(data, protocolVersion);
            this.xa = data.readByte();
            this.ya = data.readByte();
            this.za = data.readByte();
            this.yRot = data.readByte();
            this.xRot = data.readByte();
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            super.write(data, protocolVersion);
            data.writeByte(this.xa);
            data.writeByte(this.ya);
            data.writeByte(this.za);
            data.writeByte(this.yRot);
            data.writeByte(this.xRot);
        }
    }

    public static class Rot extends MoveEntityPacket {
        public Rot() {
            this.hasRot = true;
        }

        public Rot(int entityId, byte yRot, byte xRot) {
            super(entityId);
            this.yRot = yRot;
            this.xRot = xRot;
            this.hasRot = true;
        }

        @Override
        public void read(ByteBuf data, int protocolVersion) {
            super.read(data, protocolVersion);
            this.yRot = data.readByte();
            this.xRot = data.readByte();
        }

        @Override
        public void write(ByteBuf data, int protocolVersion) {
            super.write(data, protocolVersion);
            data.writeByte(this.yRot);
            data.writeByte(this.xRot);
        }
    }
}
