package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record AddItemEntityPacket(int entityId, BetaItemStack item, Vec3i position, byte xa, byte ya,
								  byte za) implements BetaPacket {
	public static final StreamCodec<ByteBuf, AddItemEntityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddItemEntityPacket::entityId,
			BetaItemStack.CODEC,
			AddItemEntityPacket::item,
			Vec3i.CODEC,
			AddItemEntityPacket::position,
			BasicStreamCodecs.BYTE,
			AddItemEntityPacket::xa,
			BasicStreamCodecs.BYTE,
			AddItemEntityPacket::ya,
			BasicStreamCodecs.BYTE,
			AddItemEntityPacket::za,
			AddItemEntityPacket::new
	);

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	public Vec3d getMovement() {
		return new Vec3d(this.xa / 128.0, this.ya / 128.0, this.za / 128.0);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_ITEM_ENTITY;
	}
}
