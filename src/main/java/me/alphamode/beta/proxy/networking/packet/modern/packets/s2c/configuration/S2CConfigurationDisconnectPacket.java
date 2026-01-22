package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2CConfigurationDisconnectPacket(TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundConfigurationPackets> {
    public static final StreamCodec<ByteBuf, S2CConfigurationDisconnectPacket> CODEC = StreamCodec.composite(
            ModernCodecs.COMPONENT,
            S2CConfigurationDisconnectPacket::reason,
            S2CConfigurationDisconnectPacket::new
    );

    @Override
    public TextComponent getReason() {
        return this.reason;
    }

    @Override
    public ClientboundConfigurationPackets getType() {
        return ClientboundConfigurationPackets.DISCONNECT;
    }

    @Override
    public PacketState getState() {
        return PacketState.CONFIGURATION;
    }
}
