package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

public class UseItemPacket implements Packet {
    public int x;
    public int y;
    public int z;
    public int face;
    public BetaItemStack item;

    public UseItemPacket() {
    }

    public UseItemPacket(int x, int y, int z, int face, BetaItemStack itemStack) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
        this.item = itemStack;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readByte();
        this.z = data.readInt();
        this.face = data.readByte();
        this.item = BetaItemStack.OPTIONAL_CODEC.decode(data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeByte(this.y);
        data.writeInt(this.z);
        data.writeByte(this.face);
        BetaItemStack.OPTIONAL_CODEC.encode(data, this.item);
    }
}
