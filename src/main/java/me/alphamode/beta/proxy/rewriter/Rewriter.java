package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface Rewriter {
	Map<Class<? extends ModernRecordPacket<?>>, BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>> rewriters = new HashMap<>();

	void registerPackets();

	default <T extends ModernRecordPacket<?>> void registerRewriter(final Class<T> clazz, final BiFunction<Connection, T, BetaRecordPacket> wrapper) {
		rewriters.put(clazz, (BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>) wrapper);
	}
}
