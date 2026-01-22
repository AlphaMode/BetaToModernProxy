package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record UseItemPacket(Vec3i position, byte face, BetaItemStack item) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, UseItemPacket> CODEC = StreamCodec.composite(
			Vec3i.TINY_CODEC,
			UseItemPacket::position,
			ByteBufCodecs.BYTE,
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
