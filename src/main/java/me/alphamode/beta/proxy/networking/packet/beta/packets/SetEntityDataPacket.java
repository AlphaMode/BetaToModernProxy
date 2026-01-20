package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.entity.SynchedEntityData;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class SetEntityDataPacket implements Packet {
	public int id;
	private List<SynchedEntityData.DataItem<?>> packedItems;

	public SetEntityDataPacket() {
	}

	public SetEntityDataPacket(final int id, final List<SynchedEntityData.DataItem<?>> packedItems) {
		this.id = id;
		this.packedItems = packedItems;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.packedItems = SynchedEntityData.unpack(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		SynchedEntityData.pack(buf, this.packedItems);
	}
}
