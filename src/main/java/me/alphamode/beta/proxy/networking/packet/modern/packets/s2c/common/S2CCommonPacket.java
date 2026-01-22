package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;

public interface S2CCommonPacket<T extends ModernClientboundPackets> extends ModernRecordPacket<T> {
    @Override
    default PacketDirection getDirection() {
        return PacketDirection.CLIENTBOUND;
    }
}
