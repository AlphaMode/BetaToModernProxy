package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CStatusResponsePacket(String status) implements S2CStatusPacket {
    public static final StreamCodec<ByteBuf, S2CStatusResponsePacket> CODEC = StreamCodec.composite(
            ModernCodecs.stringUtf8(),
            S2CStatusResponsePacket::status,
            S2CStatusResponsePacket::new
    );

    @Override
    public ClientboundStatusPackets getType() {
        return ClientboundStatusPackets.STATUS_RESPONSE;
    }
}
