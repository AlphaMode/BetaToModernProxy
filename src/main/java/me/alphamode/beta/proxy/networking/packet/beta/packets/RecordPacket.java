package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public interface RecordPacket {
    StreamCodec<ByteBuf, ? extends RecordPacket> codec();
}
