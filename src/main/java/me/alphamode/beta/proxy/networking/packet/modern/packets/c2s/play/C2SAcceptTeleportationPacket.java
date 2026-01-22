package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SAcceptTeleportationPacket(int id) implements ModernPacket<ServerboundPlayPackets> {
    public static final StreamCodec<ByteBuf, C2SAcceptTeleportationPacket> CODEC = StreamCodec.composite(
            ModernCodecs.VAR_INT,
            C2SAcceptTeleportationPacket::id,
            C2SAcceptTeleportationPacket::new
    );

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.SERVERBOUND;
    }

    @Override
    public PacketState getState() {
        return PacketState.PLAY;
    }
}
