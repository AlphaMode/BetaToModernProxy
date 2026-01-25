package me.alphamode.beta.proxy.networking.packet.pipeline;

import me.alphamode.beta.proxy.networking.Connection;

import java.util.HashMap;
import java.util.Map;

public record PacketPipeline<H, S, C>(Map<Class<? extends S>, PipelineHandler<H, ? extends S>> serverboundPipelines,
                                      Map<Class<? extends C>, PipelineHandler<H, ? extends C>> clientboundPipelines,
                                      PipelineHandler<H, ? extends S> unhandledServerHandler,
                                      PipelineHandler<H, ? extends C> unhandledClientHandler) {
    public static <H, S, C> Builder<H, S, C> builder() {
        return new Builder<>();
    }

    public static class Builder<H, S, C> {
        private final Map<Class<? extends S>, PipelineHandler<H, ? extends S>> serverPipelines = new HashMap<>();
        private final Map<Class<? extends C>, PipelineHandler<H, ? extends C>> clientPipelines = new HashMap<>();
        private PipelineHandler<H, ? extends S> unhandledServerHandler;
        private PipelineHandler<H, ? extends C> unhandledClientHandler;

        public <P extends C> Builder<H, S, C> clientHandler(Class<P> clazz, PipelineHandler<H, P> handler) {
            this.clientPipelines.put(clazz, handler);
            return this;
        }

        public <P extends S> Builder<H, S, C> serverHandler(Class<P> clazz, PipelineHandler<H, P> handler) {
            this.serverPipelines.put(clazz, handler);
            return this;
        }

        public Builder<H, S, C> unhandledClient(PipelineHandler<H, C> handler) {
            this.unhandledClientHandler = handler;
            return this;
        }

        public Builder<H, S, C> unhandledServer(PipelineHandler<H, S> handler) {
            this.unhandledServerHandler = handler;
            return this;
        }

        public PacketPipeline<H, S, C> build() {
            if (unhandledServerHandler == null || unhandledClientHandler == null) {
                throw new IllegalStateException("Unhandled pipelines must be specified");
            }
            return new PacketPipeline<>(Map.copyOf(this.serverPipelines), Map.copyOf(this.clientPipelines), this.unhandledServerHandler, this.unhandledClientHandler);
        }
    }

    @FunctionalInterface
    public interface PipelineHandler<H, T> {
        void handle(H handler, final Connection connection, final T packet);
    }
}
