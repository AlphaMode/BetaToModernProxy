package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record AddItemEntityPacket(int entityId, BetaItemStack item, Vec3i position, byte xa, byte ya,
								  byte za) implements BetaPacket {
	public static final StreamCodec<ByteStream, AddItemEntityPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			AddItemEntityPacket::entityId,
			BetaItemStack.CODEC,
			AddItemEntityPacket::item,
			Vec3i.CODEC,
			AddItemEntityPacket::position,
			CommonStreamCodecs.BYTE,
			AddItemEntityPacket::xa,
			CommonStreamCodecs.BYTE,
			AddItemEntityPacket::ya,
			CommonStreamCodecs.BYTE,
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
	public BetaPacketType getType() {
		return BetaPacketType.ADD_ITEM_ENTITY;
	}
}
