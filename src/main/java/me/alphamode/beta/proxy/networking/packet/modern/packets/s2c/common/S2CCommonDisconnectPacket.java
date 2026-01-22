package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import net.lenni0451.mcstructs.text.TextComponent;

public interface S2CCommonDisconnectPacket<T extends ModernClientboundPackets> extends S2CCommonPacket<T> {
    TextComponent getReason();
}
