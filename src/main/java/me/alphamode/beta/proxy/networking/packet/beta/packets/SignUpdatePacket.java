package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SignUpdatePacket(int x, short y, int z, String[] lines) implements RecordPacket<BetaPackets> {
	public static final int MAX_SIGN_STRING_LENGTH = 15;
	public static final StreamCodec<ByteBuf, String> SIGN_STRING_CODEC = ByteBufCodecs.stringUtf8(MAX_SIGN_STRING_LENGTH);
	public static final StreamCodec<ByteBuf, SignUpdatePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			SignUpdatePacket::x,
			ByteBufCodecs.SHORT,
			SignUpdatePacket::y,
			ByteBufCodecs.INT,
			SignUpdatePacket::z,
			ByteBufCodecs.array(SIGN_STRING_CODEC, 4, String.class),
			SignUpdatePacket::lines,
			SignUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SIGN_UPDATE;
	}
}
