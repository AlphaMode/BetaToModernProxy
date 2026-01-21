package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddItemEntityPacket(int entityId, BetaItemStack item, Vec3i position, byte xa, byte ya,
								  byte za) implements RecordPacket {
	public static final StreamCodec<ByteBuf, AddItemEntityPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AddItemEntityPacket::entityId,
			BetaItemStack.CODEC,
			AddItemEntityPacket::item,
			Vec3i.CODEC,
			AddItemEntityPacket::position,
			ByteBufCodecs.BYTE,
			AddItemEntityPacket::xa,
			ByteBufCodecs.BYTE,
			AddItemEntityPacket::ya,
			ByteBufCodecs.BYTE,
			AddItemEntityPacket::za,
			AddItemEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_ITEM_ENTITY;
	}
}
