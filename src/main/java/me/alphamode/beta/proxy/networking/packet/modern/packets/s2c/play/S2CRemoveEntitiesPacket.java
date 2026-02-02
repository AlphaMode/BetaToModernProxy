package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CRemoveEntitiesPacket(IntList entityIds) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CRemoveEntitiesPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.INT_LIST,
			S2CRemoveEntitiesPacket::entityIds,
			S2CRemoveEntitiesPacket::new
	);

	public S2CRemoveEntitiesPacket(int... entityIds) {
		this(IntList.of(entityIds));
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.REMOVE_ENTITIES;
	}
}
