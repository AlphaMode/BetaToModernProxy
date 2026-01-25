package me.alphamode.beta.proxy.networking.packet.pipeline.b2m;

import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;

public interface BetaToModernPipeline {
    static <H> PacketPipeline.Builder<H, BetaRecordPacket, ModernRecordPacket<?>> builder() {
        return PacketPipeline.builder();
    }
}
