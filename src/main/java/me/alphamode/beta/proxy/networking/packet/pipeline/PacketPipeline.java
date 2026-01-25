package me.alphamode.beta.proxy.networking.packet.pipeline;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;

public abstract class PacketPipeline<S, C> {

    public abstract void buildPipeline(Builder<S, C> builder);

    public abstract class Serverbound {
        public abstract void handle(final Connection connection, final ModernRecordPacket<?> packet);
    }

    public abstract class Clientbound {
        public abstract void handle(final Connection connection, final BetaRecordPacket packet);
    }

    public static class Builder<S, C> {
        public <P> Builder<S, C> clientHandler(Class<P> clazz, PipelineHandler<P> handler) {
            return this;
        }

        public <P> Builder<S, C> serverHandler(Class<P> clazz, PipelineHandler<P> handler) {
            return this;
        }

        public Builder<S, C> unhandledClient(PipelineHandler<C> handler) {
            return this;
        }

        public Builder<S, C> unhandledServer(PipelineHandler<S> handler) {
            return this;
        }
    }

    @FunctionalInterface
    public interface PipelineHandler<T> {
        void handle(final Connection connection, final T packet);
    }
}
