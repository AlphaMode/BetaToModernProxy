package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class LoginPacket implements Packet {
    public int clientVersion;
    public String userName;
    public long seed;
    public byte dimension;

    public LoginPacket() {
    }

    public LoginPacket(String userName, int protocol) {
        this.userName = userName;
        this.clientVersion = protocol;
    }

    public LoginPacket(String userName, int protocol, long seed, byte dimension) {
        this.userName = userName;
        this.clientVersion = protocol;
        this.seed = seed;
        this.dimension = dimension;
    }

    @Override
    public void read(ByteBuf buf, int protocolVersion) {
        this.clientVersion = buf.readInt();
        this.userName = PacketUtils.readString(buf, 16);
        this.seed = buf.readLong();
        this.dimension = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf, int protocolVersion) {
        buf.writeInt(this.clientVersion);
        PacketUtils.writeString(this.userName, buf);
        buf.writeLong(this.seed);
        buf.writeByte(this.dimension);
    }
}
