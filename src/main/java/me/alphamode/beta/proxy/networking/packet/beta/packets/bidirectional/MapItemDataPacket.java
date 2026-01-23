package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record MapItemDataPacket(short item, short mapId, byte[] colors) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, MapItemDataPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.SHORT,
			MapItemDataPacket::item,
			BasicStreamCodecs.SHORT,
			MapItemDataPacket::mapId,
			BetaStreamCodecs.TINY_BYTE_ARRAY,
			MapItemDataPacket::colors,
			MapItemDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.MAP_ITEM_DATA;
	}
}
