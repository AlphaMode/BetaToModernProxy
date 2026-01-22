package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking;

import me.alphamode.beta.proxy.networking.packet.modern.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernServerboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;

public record C2SIntentionPacket(int protocolVersion) implements ModernPacket<ModernServerboundPackets> {

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.SERVERBOUND;
    }

    @Override
    public PacketState getState() {
        return PacketState.HANDSHAKING;
    }
}
