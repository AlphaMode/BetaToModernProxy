package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record TeleportEntityPacket(int id, Vec3i position, byte yRot, byte xRot) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, TeleportEntityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			TeleportEntityPacket::id,
			Vec3i.CODEC,
			TeleportEntityPacket::position,
			BasicCodecs.BYTE,
			TeleportEntityPacket::yRot,
			BasicCodecs.BYTE,
			TeleportEntityPacket::xRot,
			TeleportEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TELEPORT_ENTITY;
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
