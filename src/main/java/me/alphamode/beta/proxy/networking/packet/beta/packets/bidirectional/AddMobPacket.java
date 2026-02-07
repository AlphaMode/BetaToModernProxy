package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.BetaEntityTypes;
import me.alphamode.beta.proxy.util.data.beta.entity.BetaSynchedEntityData;

import java.util.List;

public record AddMobPacket(int entityId, BetaEntityTypes type, Vec3i position, byte yRot, byte xRot,
						   List<BetaSynchedEntityData.DataValue<?>> dataItems) implements BetaPacket {
	public static final StreamCodec<ByteStream, AddMobPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			AddMobPacket::entityId,
			BetaEntityTypes.CODEC,
			AddMobPacket::type,
			Vec3i.CODEC,
			AddMobPacket::position,
			CommonStreamCodecs.BYTE,
			AddMobPacket::yRot,
			CommonStreamCodecs.BYTE,
			AddMobPacket::xRot,
			BetaSynchedEntityData.DATA_ITEMS_CODEC,
			AddMobPacket::dataItems,
			AddMobPacket::new
	);

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.ADD_MOB;
	}
}
