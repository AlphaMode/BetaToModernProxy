package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
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
    public void read(final ByteBuf buf, final int protocolVersion) {
        this.x = buf.readInt();
        this.y = buf.readByte();
        this.z = buf.readInt();
        this.face = buf.readByte();
        this.item = BetaItemStack.OPTIONAL_CODEC.decode(buf);
    }

    @Override
    public void write(final ByteBuf buf, final int protocolVersion) {
        buf.writeInt(this.x);
        buf.writeByte(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.face);
        BetaItemStack.OPTIONAL_CODEC.encode(buf, this.item);
    }
}
