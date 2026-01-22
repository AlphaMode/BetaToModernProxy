package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.entity.BetaSynchedEntityData;

import java.util.List;

public record AddMobPacket(int id, byte type, Vec3i position, byte yRot, byte xRot, List<BetaSynchedEntityData.DataItem<?>> dataItems) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, AddMobPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			AddMobPacket::id,
			BasicCodecs.BYTE,
			AddMobPacket::type,
			Vec3i.CODEC,
			AddMobPacket::position,
			BasicCodecs.BYTE,
			AddMobPacket::yRot,
			BasicCodecs.BYTE,
			AddMobPacket::xRot,
			BetaSynchedEntityData.DATA_ITEMS_CODEC,
			AddMobPacket::dataItems,
			AddMobPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_MOB;
	}
}
