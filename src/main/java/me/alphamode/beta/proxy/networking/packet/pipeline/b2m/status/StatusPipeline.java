package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.status;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;

public class StatusPipeline extends BetaToModernPipeline {
    @Override
    public void buildPipeline(Builder<BetaRecordPacket, ModernRecordPacket<?>> builder) {

    }

    public void handleClientIntent(final Connection connection, final C2SIntentionPacket packet) {
        switch (packet.intention()) {
            case LOGIN -> connection.setState(PacketState.LOGIN);
            case STATUS -> connection.setState(PacketState.STATUS);
            case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
        }

        connection.setProtocolVersion(packet.protocolVersion());
    }
}
