package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record PlayerActionPacket(byte action, Vec3i pos, byte face) implements RecordPacket {
    public static final StreamCodec<ByteBuf, PlayerActionPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            PlayerActionPacket::action,
            Vec3i.TINY_CODEC,
            PlayerActionPacket::pos,
            ByteBufCodecs.BYTE,
            PlayerActionPacket::face,
            PlayerActionPacket::new
    );

    public PlayerActionPacket(int action, int x, int y, int z, int face) {
        this((byte) action, new Vec3i(x, y, z), (byte) face);
    }

    @Override
    public BetaPackets getType() {
        return BetaPackets.PLAYER_ACTION;
    }
}
