package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BlockPos;

public record C2SBlockEntityTagQueryPacket(int transactionId, BlockPos pos) implements C2SPlayPacket {
    public static final StreamCodec<ByteBuf, C2SBlockEntityTagQueryPacket> CODEC = StreamCodec.composite(
            ModernCodecs.VAR_INT,
            C2SBlockEntityTagQueryPacket::transactionId,
            BlockPos.CODEC,
            C2SBlockEntityTagQueryPacket::pos,
            C2SBlockEntityTagQueryPacket::new
    );

    @Override
    public ServerboundPlayPackets getType() {
        return ServerboundPlayPackets.BLOCK_ENTITY_TAG_QUERY;
    }
}
