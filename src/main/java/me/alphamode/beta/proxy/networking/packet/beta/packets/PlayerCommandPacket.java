package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerCommandPacket(int id, byte action) implements RecordPacket {
	public static final StreamCodec<ByteBuf, PlayerCommandPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			PlayerCommandPacket::id,
			ByteBufCodecs.BYTE,
			PlayerCommandPacket::action,
			PlayerCommandPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_COMMAND;
	}
}
