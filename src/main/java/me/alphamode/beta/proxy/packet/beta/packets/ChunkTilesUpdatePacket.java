package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ChunkTilesUpdatePacket implements Packet {
    public int xc;
    public int zc;
    public short[] positions;
    public byte[] blocks;
    public byte[] data;
    public int changes;

    public ChunkTilesUpdatePacket() {
//        this.shouldDelay = true;
    }

//    public ChunkTilesUpdatePacket(int xChunk, int zChunk, short[] blockData, int changes, Level level) {
//        this.shouldDelay = true;
//        this.xc = xChunk;
//        this.zc = zChunk;
//        this.changes = changes;
//        this.positions = new short[changes];
//        this.blocks = new byte[changes];
//        this.data = new byte[changes];
//        LevelChunk levelChunk = level.getChunk(xChunk, zChunk);
//
//        for (int i = 0; i < changes; i++) {
//            int j = blockData[i] >> 12 & 15;
//            int k = blockData[i] >> 8 & 15;
//            int l = blockData[i] & 255;
//            this.positions[i] = blockData[i];
//            this.blocks[i] = (byte)levelChunk.getTile(j, l, k);
//            this.data[i] = (byte)levelChunk.getData(j, l, k);
//        }
//    }


    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.xc = data.readInt();
        this.zc = data.readInt();
        this.changes = data.readShort() & 255;
        this.positions = new short[this.changes];
        this.blocks = new byte[this.changes];
        this.data = new byte[this.changes];

        for (int i = 0; i < this.changes; i++) {
            this.positions[i] = data.readShort();
        }

        data.readBytes(this.blocks);
        data.readBytes(this.data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.xc);
        data.writeInt(this.zc);
        data.writeShort((short)this.changes);

        for (int i = 0; i < this.changes; i++) {
            data.writeShort(this.positions[i]);
        }

        data.writeBytes(this.blocks);
        data.writeBytes(this.data);
    }
}
