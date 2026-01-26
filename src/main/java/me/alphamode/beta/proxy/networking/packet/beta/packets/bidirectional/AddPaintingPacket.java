package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPaintingPacket(int id, String motive, Vec3i position, int direction) implements BetaPacket {
	public static final int MAX_PAINTING_MOTIVE = 13; // 13 is Painting.Motive.length
	public static final StreamCodec<ByteBuf, String> MOTIVE_CODEC = BetaStreamCodecs.stringUtf8(MAX_PAINTING_MOTIVE);
	public static final StreamCodec<ByteBuf, AddPaintingPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddPaintingPacket::id,
			MOTIVE_CODEC,
			AddPaintingPacket::motive,
			Vec3i.CODEC,
			AddPaintingPacket::position,
			BasicStreamCodecs.INT,
			AddPaintingPacket::direction,
			AddPaintingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PAINTING;
	}
}
