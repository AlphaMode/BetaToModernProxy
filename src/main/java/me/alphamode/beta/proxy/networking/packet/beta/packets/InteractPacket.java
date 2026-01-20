package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record InteractPacket(int source, int target, byte action) implements RecordPacket {
	public static final StreamCodec<ByteBuf, InteractPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			InteractPacket::source,
			ByteBufCodecs.INT,
			InteractPacket::target,
			ByteBufCodecs.BYTE,
			InteractPacket::action,
			InteractPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.INTERACT;
	}
}
