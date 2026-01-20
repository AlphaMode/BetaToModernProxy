package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record UpdateStatPacket(int id, byte amount) implements RecordPacket {
	public static final StreamCodec<ByteBuf, UpdateStatPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			UpdateStatPacket::id,
			ByteBufCodecs.BYTE,
			UpdateStatPacket::amount,
			UpdateStatPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.UPDATE_STAT;
	}
}
