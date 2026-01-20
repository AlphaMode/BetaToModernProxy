package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.raphimc.netminecraft.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ContainerOpenPacket implements Packet {
	public int containerId;
	public int type;
	public String title;
	public int size;

	public ContainerOpenPacket() {
	}

	public ContainerOpenPacket(final int containerId, final int id, final String name, final int size) {
		this.containerId = containerId;
		this.type = id;
		this.title = name;
		this.size = size;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readUnsignedByte();
		this.type = buf.readUnsignedByte();
		try (final DataInputStream out = new DataInputStream(new ByteBufInputStream(buf))) {
			this.title = out.readUTF();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		this.size = buf.readUnsignedByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeByte(this.type);
		try (final DataOutputStream out = new DataOutputStream(new ByteBufOutputStream(buf))) {
			out.writeUTF(this.title);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		buf.writeByte(this.size);
	}
}
