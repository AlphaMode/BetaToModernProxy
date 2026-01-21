package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.entity.SynchedEntityData;

import java.util.List;

public record SetEntityDataPacket(int id, List<SynchedEntityData.DataItem<?>> packedItems) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, SetEntityDataPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			SetEntityDataPacket::id,
			SynchedEntityData.DATA_ITEMS_CODEC,
			SetEntityDataPacket::packedItems,
			SetEntityDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_ENTITY_DATA;
	}
}
