package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class ContainerSetContentPacket implements Packet {
    public int containerId;
    public BetaItemStack[] items;

    public ContainerSetContentPacket() {
    }

    public ContainerSetContentPacket(int containerId, List<BetaItemStack> items) {
        this.containerId = containerId;
        this.items = new BetaItemStack[items.size()];

        for (int i = 0; i < this.items.length; i++) {
            this.items[i] = items.get(i);
        }
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readByte();
        int size = data.readShort();
        this.items = new BetaItemStack[size];

        for (int i = 0; i < size; i++) {
            this.items[i] = BetaItemStack.OPTIONAL_CODEC.decode(data);
        }
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeShort(this.items.length);

        for (BetaItemStack item : this.items) {
            BetaItemStack.OPTIONAL_CODEC.encode(data, item);
        }
    }
}
