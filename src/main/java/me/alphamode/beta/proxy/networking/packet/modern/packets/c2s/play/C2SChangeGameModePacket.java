package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.GameMode;

public record C2SChangeGameModePacket(GameMode gameMode) implements C2SPlayPacket {
    public static final StreamCodec<ByteBuf, C2SChangeGameModePacket> CODEC = StreamCodec.composite(
            GameMode.CODEC,
            C2SChangeGameModePacket::gameMode,
            C2SChangeGameModePacket::new
    );

    @Override
    public ServerboundPlayPackets getType() {
        return ServerboundPlayPackets.CHANGE_GAME_MODE;
    }
}
