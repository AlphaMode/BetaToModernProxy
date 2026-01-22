package me.alphamode.beta.proxy.networking.packet.beta.packets;

import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public interface BetaRecordPacket extends RecordPacket<BetaPackets> {
	int PROTOCOL_VERSION = 14;
}
