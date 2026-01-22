package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SignUpdatePacket(int x, short y, int z, String[] lines) implements BetaRecordPacket {
	public static final int MAX_SIGN_STRING_LENGTH = 15;
	public static final StreamCodec<ByteBuf, String> SIGN_STRING_CODEC = BetaCodecs.stringUtf8(MAX_SIGN_STRING_LENGTH);
	public static final StreamCodec<ByteBuf, SignUpdatePacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			SignUpdatePacket::x,
			BasicCodecs.SHORT,
			SignUpdatePacket::y,
			BasicCodecs.INT,
			SignUpdatePacket::z,
			BasicCodecs.array(SIGN_STRING_CODEC, 4, String.class),
			SignUpdatePacket::lines,
			SignUpdatePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SIGN_UPDATE;
	}
}
