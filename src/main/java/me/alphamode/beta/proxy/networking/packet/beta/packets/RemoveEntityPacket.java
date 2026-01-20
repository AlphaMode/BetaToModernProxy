package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record RemoveEntityPacket(int id) implements RecordPacket {
    public static final StreamCodec<ByteBuf, RemoveEntityPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RemoveEntityPacket::id,
            RemoveEntityPacket::new
    );

    @Override
    public BetaPackets getType() {
        return BetaPackets.REMOVE_ENTITY;
    }
}
