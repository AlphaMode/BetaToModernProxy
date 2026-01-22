package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface Rewriter {
	Map<Class<? extends ModernRecordPacket<?>>, BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>> serverboundRewriters = new HashMap<>();
	Map<Class<? extends BetaRecordPacket>, BiFunction<Connection, BetaRecordPacket, ModernRecordPacket<?>>> clientboundRewriters = new HashMap<>();

	void registerPackets();

	default <T extends ModernRecordPacket<?>> void registerServerboundRewriter(final Class<T> clazz, final BiFunction<Connection, T, BetaRecordPacket> wrapper) {
		serverboundRewriters.put(clazz, (BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>) wrapper);
	}

	default <T extends BetaRecordPacket> void registerClientboundRewriter(final Class<T> clazz, final BiFunction<Connection, BetaRecordPacket, T> wrapper) {
		serverboundRewriters.put(clazz, (BiFunction<Connection, BetaRecordPacket, ModernRecordPacket<?>>) wrapper);
	}

	default <T extends RecordPacket<?>, S extends RecordPacket<?>> void registerRewriter(final Class<T> clazz, final PacketDirection direction, BiFunction<Connection, T, S> consumer) {
		if (direction == PacketDirection.SERVERBOUND) {
			registerServerboundRewriter((BetaRecordPacket) clazz, consumer);
		} else {
			registerClientboundRewriter((ModernRecordPacket<?>) clazz, consumer);
		}
	}
}
