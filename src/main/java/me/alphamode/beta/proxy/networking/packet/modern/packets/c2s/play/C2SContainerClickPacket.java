package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.item.HashedStack;

public record C2SContainerClickPacket(int containerId, int stateId, short slot, byte button, ClickType clickType,
									  Int2ObjectMap<HashedStack> changedSlots,
									  HashedStack carriedItem) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, Int2ObjectMap<HashedStack>> SLOTS_STREAM_CODEC = ModernStreamCodecs.map(
			Int2ObjectOpenHashMap::new, BasicStreamCodecs.SHORT.map(Short::intValue, Integer::shortValue), HashedStack.CODEC, 128
	);

	public static final StreamCodec<ByteBuf, C2SContainerClickPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SContainerClickPacket::containerId,
			ModernStreamCodecs.VAR_INT,
			C2SContainerClickPacket::stateId,
			BasicStreamCodecs.SHORT,
			C2SContainerClickPacket::slot,
			BasicStreamCodecs.BYTE,
			C2SContainerClickPacket::button,
			ModernStreamCodecs.javaEnum(ClickType.class),
			C2SContainerClickPacket::clickType,
			SLOTS_STREAM_CODEC,
			C2SContainerClickPacket::changedSlots,
			HashedStack.CODEC,
			C2SContainerClickPacket::carriedItem,
			C2SContainerClickPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CONTAINER_CLICK;
	}

	public enum ClickType {
		PICKUP,
		QUICK_MOVE,
		SWAP,
		CLONE,
		THROW,
		QUICK_CRAFT,
		PICKUP_ALL
	}
}
