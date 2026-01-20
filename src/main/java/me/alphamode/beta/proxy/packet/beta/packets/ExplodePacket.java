package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.StreamCodec;
import me.alphamode.beta.proxy.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

import java.util.HashSet;
import java.util.Set;

public class ExplodePacket implements Packet {
    public double x;
    public double y;
    public double z;
    public float r;
    public Set<Vec3i> toBlow;

    public ExplodePacket() {
    }

    public ExplodePacket(double x, double y, double z, float r, Set<Vec3i> toBlow) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.toBlow = new HashSet<>(toBlow);
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.r = data.readFloat();
        int size = data.readInt();
        this.toBlow = new HashSet<>();
        Vec3i origin = new Vec3i((int) this.x, (int) this.y, (int) this.z);

        for (int i = 0; i < size; i++) {
            this.toBlow.add(Vec3i.relative(origin).decode(data));
        }
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeFloat(this.r);
        data.writeInt(this.toBlow.size());
        StreamCodec<ByteBuf, Vec3i> codec = Vec3i.relative(new Vec3i((int) this.x, (int) this.y, (int) this.z));

        for (Vec3i tilePos : this.toBlow) {
            codec.encode(data, tilePos);
        }
    }
}
