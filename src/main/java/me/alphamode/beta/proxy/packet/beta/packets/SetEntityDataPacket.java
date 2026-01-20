package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.entity.SynchedEntityData;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class SetEntityDataPacket implements Packet {
    public int id;
    private List<SynchedEntityData.DataItem<?>> packedItems;

    public SetEntityDataPacket() {
    }

    public SetEntityDataPacket(int id, List<SynchedEntityData.DataItem<?>> packedItems) {
        this.id = id;
        this.packedItems = packedItems;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.packedItems = SynchedEntityData.unpack(data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        SynchedEntityData.pack(data, this.packedItems);
    }
}
