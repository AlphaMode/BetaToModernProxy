package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.entity.BetaSynchedEntityData;

import java.util.List;

public record SetEntityDataPacket(int id, List<BetaSynchedEntityData.DataItem<?>> packedItems) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, SetEntityDataPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			SetEntityDataPacket::id,
			BetaSynchedEntityData.DATA_ITEMS_CODEC,
			SetEntityDataPacket::packedItems,
			SetEntityDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_ENTITY_DATA;
	}
}
