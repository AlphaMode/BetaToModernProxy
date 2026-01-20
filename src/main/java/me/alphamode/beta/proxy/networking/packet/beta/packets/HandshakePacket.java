package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public record HandshakePacket(String username) implements RecordPacket {
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = ByteBufCodecs.stringUtf8(MAX_USERNAME_LENGTH);
    public static final StreamCodec<ByteBuf, HandshakePacket> CODEC = StreamCodec.composite(
            USERNAME_CODEC,
            HandshakePacket::username,
            HandshakePacket::new
    );

    @Override
    public BetaPackets getType() {
        return BetaPackets.HANDSHAKE;
    }
}
