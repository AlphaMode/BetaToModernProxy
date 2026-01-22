package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record InteractPacket(int source, int target, byte action) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, InteractPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			InteractPacket::source,
			BasicCodecs.INT,
			InteractPacket::target,
			BasicCodecs.BYTE,
			InteractPacket::action,
			InteractPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.INTERACT;
	}
}
