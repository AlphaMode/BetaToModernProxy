package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetCarriedItemPacket(short slot) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, SetCarriedItemPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.SHORT,
			SetCarriedItemPacket::slot,
			SetCarriedItemPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_CARRIED_ITEM;
	}
}
