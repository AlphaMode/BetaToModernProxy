package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record TeleportEntityPacket(int entityId, Vec3i position, byte packedYRot,
								   byte packedXRot) implements BetaPacket {
	public static final StreamCodec<ByteBuf, TeleportEntityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			TeleportEntityPacket::entityId,
			Vec3i.CODEC,
			TeleportEntityPacket::position,
			BasicStreamCodecs.BYTE,
			TeleportEntityPacket::packedYRot,
			BasicStreamCodecs.BYTE,
			TeleportEntityPacket::packedXRot,
			TeleportEntityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.TELEPORT_ENTITY;
	}

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	public float getYRot() {
		return Mth.unpackDegrees(this.packedYRot);
	}

	public float getXRot() {
		return Mth.unpackDegrees(this.packedXRot);
	}

//    public TeleportEntityPacket(Entity entity) {
//        this.entityId = entity.entityId;
//        this.x = Mth.floor(entity.x * 32.0);
//        this.y = Mth.floor(entity.y * 32.0);
//        this.z = Mth.floor(entity.z * 32.0);
//        this.yRot = (byte)(entity.yRot * 256.0F / 360.0F);
//        this.xRot = (byte)(entity.xRot * 256.0F / 360.0F);
//    }
}
