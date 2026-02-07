package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.item.HashedStack;

public record C2SContainerClickPacket(int containerId, int stateId, short slot, byte button, ClickType clickType,
									  Int2ObjectMap<HashedStack> changedSlots,
									  HashedStack carriedItem) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, Int2ObjectMap<HashedStack>> SLOTS_STREAM_CODEC = ModernStreamCodecs.map(
			Int2ObjectOpenHashMap::new, CommonStreamCodecs.SHORT.map(Short::intValue, Integer::shortValue), HashedStack.STREAM_CODEC, 128
	);

	public static final StreamCodec<ByteStream, C2SContainerClickPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SContainerClickPacket::containerId,
			ModernStreamCodecs.VAR_INT,
			C2SContainerClickPacket::stateId,
			CommonStreamCodecs.SHORT,
			C2SContainerClickPacket::slot,
			CommonStreamCodecs.BYTE,
			C2SContainerClickPacket::button,
			ModernStreamCodecs.javaEnum(ClickType.class),
			C2SContainerClickPacket::clickType,
			SLOTS_STREAM_CODEC,
			C2SContainerClickPacket::changedSlots,
			HashedStack.STREAM_CODEC,
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
