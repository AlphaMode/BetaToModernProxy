package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerCommandPacket(int id, byte action) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, PlayerCommandPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			PlayerCommandPacket::id,
			BasicCodecs.BYTE,
			PlayerCommandPacket::action,
			PlayerCommandPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_COMMAND;
	}
}
