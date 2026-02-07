package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.entity.BetaSynchedEntityData;

import java.util.List;

public record SetEntityDataPacket(int id,
								  List<BetaSynchedEntityData.DataValue<?>> packedItems) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetEntityDataPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			SetEntityDataPacket::id,
			BetaSynchedEntityData.DATA_ITEMS_CODEC,
			SetEntityDataPacket::packedItems,
			SetEntityDataPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_ENTITY_DATA;
	}
}
