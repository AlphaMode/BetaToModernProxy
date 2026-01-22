package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddItemEntityPacket(int entityId, BetaItemStack item, Vec3i position, byte xa, byte ya,
								  byte za) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, AddItemEntityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			AddItemEntityPacket::entityId,
			BetaItemStack.CODEC,
			AddItemEntityPacket::item,
			Vec3i.CODEC,
			AddItemEntityPacket::position,
			BasicCodecs.BYTE,
			AddItemEntityPacket::xa,
			BasicCodecs.BYTE,
			AddItemEntityPacket::ya,
			BasicCodecs.BYTE,
			AddItemEntityPacket::za,
			AddItemEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_ITEM_ENTITY;
	}
}
