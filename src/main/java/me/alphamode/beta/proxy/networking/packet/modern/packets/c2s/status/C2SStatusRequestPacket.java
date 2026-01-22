package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundStatusPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SStatusRequestPacket implements C2SStatusPacket {
    public static final C2SStatusRequestPacket INSTANCE = new C2SStatusRequestPacket();
    public static final StreamCodec<ByteBuf, C2SStatusRequestPacket> CODEC = StreamCodec.unit(INSTANCE);

    private C2SStatusRequestPacket() {}

    @Override
    public ServerboundStatusPackets getType() {
        return ServerboundStatusPackets.STATUS_REQUEST;
    }
}
