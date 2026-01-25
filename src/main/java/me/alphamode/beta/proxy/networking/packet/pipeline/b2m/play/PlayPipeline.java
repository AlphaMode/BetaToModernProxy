package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;

public class PlayPipeline {
	public static final PacketPipeline<PlayPipeline, BetaRecordPacket, ModernRecordPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.build();

	public void handleC2SConfigurationAcknowledged(final Connection connection, final C2SConfigurationAcknowledgedPacket packet) {

	}
}
