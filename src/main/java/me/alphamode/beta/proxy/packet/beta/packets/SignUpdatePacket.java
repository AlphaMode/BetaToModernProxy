package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class SignUpdatePacket implements Packet {
    public int x;
    public int y;
    public int z;
    public String[] lines;

    public SignUpdatePacket() {
//        this.shouldDelay = true;
    }

    public SignUpdatePacket(int x, int y, int z, String[] lines) {
//        this.shouldDelay = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lines = lines;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readShort();
        this.z = data.readInt();
        this.lines = new String[4];

        for (int i = 0; i < 4; i++) {
            this.lines[i] = PacketUtils.readString(data, 15);
        }
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeShort(this.y);
        data.writeInt(this.z);

        for (int i = 0; i < 4; i++) {
            PacketUtils.writeString(this.lines[i], data);
        }
    }
}
