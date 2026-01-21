package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.List;

public record MapItemDataPacket(short item, short mapId, List<Byte> colors) implements RecordPacket {
	public static final StreamCodec<ByteBuf, MapItemDataPacket> CODEC = RecordPacket.codec(MapItemDataPacket::write, MapItemDataPacket::new);

	public MapItemDataPacket(final ByteBuf buf) {
		final short item = buf.readShort();
		final short mapId = buf.readShort();
		this(item, mapId, ByteBufCodecs.array(ByteBufCodecs.BYTE, buf.readByte() & 255).decode(buf));
	}

	public void write(final ByteBuf buf) {
		buf.writeShort(this.item);
		buf.writeShort(this.mapId);
		buf.writeByte(this.colors.size());

		final byte[] data = new byte[this.colors.size()];
		for (int i = 0; i < this.colors.size(); ++i) {
			data[i] = this.colors.get(i);
		}

		buf.writeBytes(data);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.MAP_ITEM_DATA;
	}
}
