package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record InteractPacket(int source, int target, byte action) implements BetaPacket {
	public static final StreamCodec<ByteBuf, InteractPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			InteractPacket::source,
			BasicStreamCodecs.INT,
			InteractPacket::target,
			BasicStreamCodecs.BYTE,
			InteractPacket::action,
			InteractPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.INTERACT;
	}
}
