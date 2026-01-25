package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SignUpdatePacket(int x, short y, int z, String[] lines) implements BetaPacket {
	public static final int MAX_SIGN_STRING_LENGTH = 15;
	public static final StreamCodec<ByteBuf, String> SIGN_STRING_CODEC = BetaStreamCodecs.stringUtf8(MAX_SIGN_STRING_LENGTH);
	public static final StreamCodec<ByteBuf, SignUpdatePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			SignUpdatePacket::x,
			BasicStreamCodecs.SHORT,
			SignUpdatePacket::y,
			BasicStreamCodecs.INT,
			SignUpdatePacket::z,
			BasicStreamCodecs.array(SIGN_STRING_CODEC, 4, String.class),
			SignUpdatePacket::lines,
			SignUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SIGN_UPDATE;
	}
}
