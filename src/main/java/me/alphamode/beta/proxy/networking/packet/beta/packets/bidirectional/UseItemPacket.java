package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record UseItemPacket(Vec3i position, byte face, BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteBuf, UseItemPacket> CODEC = StreamCodec.composite(
			Vec3i.TINY_CODEC,
			UseItemPacket::position,
			BasicStreamCodecs.BYTE,
			UseItemPacket::face,
			BetaItemStack.OPTIONAL_CODEC,
			UseItemPacket::item,
			UseItemPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.USE_ITEM;
	}
}
