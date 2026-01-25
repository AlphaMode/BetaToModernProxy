package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;

public class PlayPipeline extends BetaToModernPipeline {
    @Override
    public void buildPipeline(Builder<BetaRecordPacket, ModernRecordPacket<?>> builder) {
        builder.clientHandler(C2SConfigurationAcknowledgedPacket.class, this::handleC2SConfigurationAcknowledged);
    }

    public void handleC2SConfigurationAcknowledged(final Connection connection, final C2SConfigurationAcknowledgedPacket packet) {

    }
}
