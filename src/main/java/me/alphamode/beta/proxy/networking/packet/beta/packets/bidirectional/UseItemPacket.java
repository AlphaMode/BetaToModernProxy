package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record UseItemPacket(Vec3i position, byte face, BetaItemStack item) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, UseItemPacket> CODEC = StreamCodec.composite(
			Vec3i.TINY_CODEC,
			UseItemPacket::position,
			BasicCodecs.BYTE,
			UseItemPacket::face,
			BetaItemStack.OPTIONAL_CODEC,
			UseItemPacket::item,
			UseItemPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.USE_ITEM;
	}
}
