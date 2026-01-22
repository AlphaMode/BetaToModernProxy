package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;

public interface S2CPlayPacket extends ModernRecordPacket<ClientboundPlayPackets> {
    @Override
    default PacketDirection getDirection() {
        return PacketDirection.CLIENTBOUND;
    }

    @Override
    default PacketState getState() {
        return PacketState.PLAY;
    }
}
