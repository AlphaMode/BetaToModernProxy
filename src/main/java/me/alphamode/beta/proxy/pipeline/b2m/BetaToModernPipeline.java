package me.alphamode.beta.proxy.pipeline.b2m;

import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.pipeline.PacketPipeline;

public interface BetaToModernPipeline {
	static <H> PacketPipeline.Builder<H, BetaPacket, ModernPacket<?>> builder() {
		return PacketPipeline.builder();
	}
}
