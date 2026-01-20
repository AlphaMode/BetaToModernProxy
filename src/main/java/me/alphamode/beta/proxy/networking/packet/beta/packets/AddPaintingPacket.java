package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPaintingPacket(int id, String motive, Vec3i position, int direction) implements RecordPacket {
	public static final int MAX_PAINTING_MOTIVE = 13; // 13 is Painting.Motive.length
	public static final StreamCodec<ByteBuf, String> MOTIVE_CODEC = ByteBufCodecs.stringUtf8(MAX_PAINTING_MOTIVE);
	public static final StreamCodec<ByteBuf, AddPaintingPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AddPaintingPacket::id,
			MOTIVE_CODEC,
			AddPaintingPacket::motive,
			Vec3i.CODEC,
			AddPaintingPacket::position,
			ByteBufCodecs.INT,
			AddPaintingPacket::direction,
			AddPaintingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PAINTING;
	}
}
