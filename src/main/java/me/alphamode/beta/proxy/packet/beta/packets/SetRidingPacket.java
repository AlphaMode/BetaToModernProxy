package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetRidingPacket implements Packet {
    public int riderId;
    public int riddenId;

    public SetRidingPacket() {
    }

//    public SetRidingPacket(Entity passenger, Entity vehicle) {
//        this.riderId = passenger.id;
//        this.riddenId = vehicle != null ? vehicle.id : -1;
//    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.riderId = data.readInt();
        this.riddenId = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.riderId);
        data.writeInt(this.riddenId);
    }
}
