package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SignUpdatePacket(int x, short y, int z, String[] lines) implements BetaPacket {
	public static final int MAX_SIGN_STRING_LENGTH = 15;
	public static final StreamCodec<ByteStream, String> SIGN_STRING_CODEC = BetaStreamCodecs.stringUtf8(MAX_SIGN_STRING_LENGTH);
	public static final StreamCodec<ByteStream, SignUpdatePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			SignUpdatePacket::x,
			CommonStreamCodecs.SHORT,
			SignUpdatePacket::y,
			CommonStreamCodecs.INT,
			SignUpdatePacket::z,
			CommonStreamCodecs.array(SIGN_STRING_CODEC, 4, String.class),
			SignUpdatePacket::lines,
			SignUpdatePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SIGN_UPDATE;
	}
}
