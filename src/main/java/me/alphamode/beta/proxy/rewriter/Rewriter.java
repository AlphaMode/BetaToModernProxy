package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;

import java.util.HashMap;
import java.util.Map;

public abstract class Rewriter<S extends RecordPacket<?>> {
	private final Map<Class<? extends ModernRecordPacket<?>>, RewriterFactory<ModernRecordPacket<?>>> b2mRewriters = new HashMap<>();
	private final Map<Class<? extends BetaRecordPacket>, RewriterFactory<BetaRecordPacket>> m2bRewriters = new HashMap<>();

	public abstract void registerPackets();

	public abstract void rewrite(final Connection connection, final S packet);

	public <V extends ModernRecordPacket<?>> void registerServerboundRewriter(final Class<V> clazz, final RewriterFactory<V> factory) {
		this.b2mRewriters.put(clazz, (RewriterFactory<ModernRecordPacket<?>>) factory);
	}

	public <V extends BetaRecordPacket> void registerClientboundRewriter(final Class<V> clazz, final RewriterFactory<V> factory) {
		this.m2bRewriters.put(clazz, (RewriterFactory<BetaRecordPacket>) factory);
	}

	public <T extends ModernRecordPacket<?>> RewriterFactory<ModernRecordPacket<?>> getServerboundRewriter(final Class<T> clazz) {
		for (final Class<?> rewriterClazz : this.b2mRewriters.keySet()) {
			if (rewriterClazz.isAssignableFrom(clazz)) {
				return this.b2mRewriters.get(rewriterClazz);
			}
		}

		return null;
	}

	public <T extends BetaRecordPacket> RewriterFactory<BetaRecordPacket> getClientboundRewriter(final Class<T> clazz) {
		for (final Class<?> rewriterClazz : this.m2bRewriters.keySet()) {
			if (rewriterClazz.isAssignableFrom(clazz)) {
				return this.m2bRewriters.get(rewriterClazz);
			}
		}

		return null;
	}

	@FunctionalInterface
	public interface RewriterFactory<T extends RecordPacket<? extends Packets>> {
		void rewrite(Connection connection, T packet);
	}
}
