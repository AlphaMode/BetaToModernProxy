package me.alphamode.beta.proxy.networking.packet.beta.packets;

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
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.xc = buf.readInt();
		this.zc = buf.readInt();
		this.changes = buf.readShort() & 255;
		this.positions = new short[this.changes];
		this.blocks = new byte[this.changes];
		this.data = new byte[this.changes];
		for (int i = 0; i < this.changes; i++) {
			this.positions[i] = buf.readShort();
		}

		buf.readBytes(this.blocks);
		buf.readBytes(this.data);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.xc);
		buf.writeInt(this.zc);
		buf.writeShort((short) this.changes);
		for (int i = 0; i < this.changes; i++) {
			buf.writeShort(this.positions[i]);
		}

		buf.writeBytes(this.blocks);
		buf.writeBytes(this.data);
	}
}
