package me.alphamode.beta.proxy.networking.packet;

import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import net.lenni0451.mcstructs.text.TextComponent;

public interface PacketHandler {
	S2CCommonDisconnectPacket<?> createDisconnectPacket(final TextComponent message);

	S2CCommonKeepAlivePacket<?> getKeepAlivePacket(final long time);
}
