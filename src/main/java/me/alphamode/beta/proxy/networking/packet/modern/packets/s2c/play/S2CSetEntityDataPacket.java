package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ModernSynchedEntityData;

import java.util.List;

public record S2CSetEntityDataPacket(int id,
									 List<ModernSynchedEntityData.DataItem<?>> packedItems) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CSetEntityDataPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CSetEntityDataPacket::id,
			ModernSynchedEntityData.DATA_ITEMS_CODEC,
			S2CSetEntityDataPacket::packedItems,
			S2CSetEntityDataPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_ENTITY_DATA;
	}
}
