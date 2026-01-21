package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record MapItemDataPacket(short item, short mapId, byte[] colors) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, MapItemDataPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.SHORT,
			MapItemDataPacket::item,
			ByteBufCodecs.SHORT,
			MapItemDataPacket::mapId,
			ByteBufCodecs.TINY_BYTE_ARRAY,
			MapItemDataPacket::colors,
			MapItemDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.MAP_ITEM_DATA;
	}
}
