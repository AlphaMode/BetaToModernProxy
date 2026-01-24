package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class Rewriter {
	public final Map<Class<? extends ModernRecordPacket<?>>, RewriterFactory<ModernRecordPacket<?>>> serverboundRewriters = new HashMap<>();
	public final Map<Class<? extends BetaRecordPacket>, RewriterFactory<BetaRecordPacket>> clientboundRewriters = new HashMap<>();

	public abstract void registerPackets();

	public <T extends ModernRecordPacket<?>> void registerServerboundRewriter(final Class<T> clazz, final RewriterFactory<T> factory) {
		serverboundRewriters.put(clazz, (RewriterFactory<ModernRecordPacket<?>>) factory);
	}

	public <T extends BetaRecordPacket> void registerClientboundRewriter(final Class<T> clazz, final RewriterFactory<T> factory) {
		clientboundRewriters.put(clazz, (RewriterFactory<BetaRecordPacket>) factory);
	}

    @FunctionalInterface
    public interface RewriterFactory<T extends RecordPacket<? extends Packets>> {
        void rewrite(Connection connection, T packet);
    }
}
