package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.BetaEntityType;
import me.alphamode.beta.proxy.util.data.beta.BetaSynchedEntityData;

import java.util.List;

public record AddMobPacket(int entityId, BetaEntityType type, Vec3i position, byte yRot, byte xRot,
						   List<BetaSynchedEntityData.DataItem<?>> dataItems) implements BetaPacket {
	public static final StreamCodec<ByteBuf, AddMobPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddMobPacket::entityId,
			BetaEntityType.CODEC,
			AddMobPacket::type,
			Vec3i.CODEC,
			AddMobPacket::position,
			BasicStreamCodecs.BYTE,
			AddMobPacket::yRot,
			BasicStreamCodecs.BYTE,
			AddMobPacket::xRot,
			BetaSynchedEntityData.DATA_ITEMS_CODEC,
			AddMobPacket::dataItems,
			AddMobPacket::new
	);

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_MOB;
	}
}
