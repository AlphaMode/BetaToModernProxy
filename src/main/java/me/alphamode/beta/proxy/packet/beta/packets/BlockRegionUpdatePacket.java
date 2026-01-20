package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class BlockRegionUpdatePacket implements Packet {
    public int x;
    public int y;
    public int z;
    public int xs;
    public int ys;
    public int zs;
    public byte[] buffer;
    private int size;

    public BlockRegionUpdatePacket() {
//        this.shouldDelay = true;
    }

    public BlockRegionUpdatePacket(int x, int y, int z, int xs, int ys, int zs, byte[] data) {
//        this.shouldDelay = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
        this.buffer = data;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readShort();
        this.z = data.readInt();
        this.xs = data.readByte() + 1;
        this.ys = data.readByte() + 1;
        this.zs = data.readByte() + 1;
        this.size = data.readInt();
        byte[] bs = new byte[this.size];
        data.readBytes(bs);
//        data.readBytes(bs, this.size);
        this.buffer = bs;
//        this.buffer = new byte[this.xs * this.ys * this.zs * 5 / 2];
//
//        try(Inflater inflater = new Inflater()) {
//            inflater.setInput(bs);
//            inflater.inflate(this.buffer);
//        } catch (DataFormatException e) {
//            throw new RuntimeException("Bad compressed data format");
//        }
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeShort(this.y);
        data.writeInt(this.z);
        data.writeByte(this.xs - 1);
        data.writeByte(this.ys - 1);
        data.writeByte(this.zs - 1);
        data.writeInt(this.size);
        data.writeBytes(this.buffer);
//        data.writeInt(this.size);
//        data.writeBytes(this.buffer, 0, this.size);

//        try(Deflater deflater = new Deflater(-1)) {
//            deflater.setInput(this.buffer);
//            deflater.finish();
//            byte[] outBuffer = new byte[xs * ys * zs * 5 / 2];
//            int size = deflater.deflate(this.buffer);
//            data.writeInt(size);
//            data.writeBytes(outBuffer, 0, size);
//        }
    }
}
