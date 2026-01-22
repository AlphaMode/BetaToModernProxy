package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetCarriedItemPacket(short slot) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, SetCarriedItemPacket> CODEC = StreamCodec.composite(
			BasicCodecs.SHORT,
			SetCarriedItemPacket::slot,
			SetCarriedItemPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_CARRIED_ITEM;
	}
}
