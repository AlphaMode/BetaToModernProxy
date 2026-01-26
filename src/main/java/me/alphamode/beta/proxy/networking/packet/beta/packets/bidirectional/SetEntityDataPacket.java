package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaSynchedEntityData;

import java.util.List;

public record SetEntityDataPacket(int id,
								  List<BetaSynchedEntityData.DataItem<?>> packedItems) implements BetaPacket {
	public static final StreamCodec<ByteBuf, SetEntityDataPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
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
