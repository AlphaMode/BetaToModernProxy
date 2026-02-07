package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record MapItemDataPacket(short item, short mapId, byte[] colors) implements BetaPacket {
	public static final StreamCodec<ByteStream, MapItemDataPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.SHORT,
			MapItemDataPacket::item,
			CommonStreamCodecs.SHORT,
			MapItemDataPacket::mapId,
			BetaStreamCodecs.TINY_BYTE_ARRAY,
			MapItemDataPacket::colors,
			MapItemDataPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.MAP_ITEM_DATA;
	}
}
