package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record MapItemDataPacket(short item, short mapId, byte[] colors) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, MapItemDataPacket> CODEC = StreamCodec.composite(
			BasicCodecs.SHORT,
			MapItemDataPacket::item,
			BasicCodecs.SHORT,
			MapItemDataPacket::mapId,
			BetaCodecs.TINY_BYTE_ARRAY,
			MapItemDataPacket::colors,
			MapItemDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.MAP_ITEM_DATA;
	}
}
