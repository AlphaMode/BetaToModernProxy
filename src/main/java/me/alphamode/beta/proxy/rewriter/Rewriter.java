package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class Rewriter {
	protected final Map<Class<? extends ModernRecordPacket<?>>, BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>> serverboundRewriters = new HashMap<>();
	protected final Map<Class<? extends BetaRecordPacket>, BiFunction<Connection, BetaRecordPacket, ModernRecordPacket<?>>> clientboundRewriters = new HashMap<>();

	public abstract void registerPackets();

	public <T extends ModernRecordPacket<?>> void registerServerboundRewriter(final Class<T> clazz, final BiFunction<Connection, T, BetaRecordPacket> wrapper) {
		serverboundRewriters.put(clazz, (BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>) wrapper);
	}

	public <T extends BetaRecordPacket> void registerClientboundRewriter(final Class<T> clazz, final BiFunction<Connection, T, ModernRecordPacket<?>> wrapper) {
		clientboundRewriters.put(clazz, (BiFunction<Connection, BetaRecordPacket, ModernRecordPacket<?>>) wrapper);
	}

	public <T extends RecordPacket<?>, S extends RecordPacket<?>> void registerRewriter(final Class<T> clazz, final PacketDirection direction, BiFunction<Connection, T, S> consumer) {
		if (direction == PacketDirection.SERVERBOUND) {
			registerServerboundRewriter((Class<ModernRecordPacket<?>>) clazz, (BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>) consumer);
		} else {
			registerClientboundRewriter((Class<BetaRecordPacket>) clazz, (BiFunction<Connection, BetaRecordPacket, ModernRecordPacket<?>>) consumer);
		}
	}
}
