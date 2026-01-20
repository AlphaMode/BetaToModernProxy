package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class SetSpawnPositionPacket implements Packet {
	public Vec3i position;

	public SetSpawnPositionPacket() {
	}

	public SetSpawnPositionPacket(final Vec3i position) {
		this.position = position;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.position = Vec3i.CODEC.decode(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		Vec3i.CODEC.encode(buf, this.position);
	}
}
