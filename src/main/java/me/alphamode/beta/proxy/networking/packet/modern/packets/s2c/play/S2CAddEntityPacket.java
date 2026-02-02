package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;

import java.util.UUID;

public record S2CAddEntityPacket(
        int entityId,
        UUID uuid,
        int type,
        Vec3d position,
        Vec3d movement,
        byte xRot,
        byte yRot,
        byte yHeadRot,
        int data
) implements S2CPlayPacket {
    public static final StreamCodec<ByteBuf, S2CAddEntityPacket> CODEC = StreamCodec.composite(
            ModernStreamCodecs.VAR_INT,
            S2CAddEntityPacket::entityId,
            ModernStreamCodecs.UUID,
            S2CAddEntityPacket::uuid,
            ModernStreamCodecs.VAR_INT,
            S2CAddEntityPacket::type,
            Vec3d.CODEC,
            S2CAddEntityPacket::position,
            Vec3d.LERP_CODEC,
            S2CAddEntityPacket::movement,
            BasicStreamCodecs.BYTE,
            S2CAddEntityPacket::xRot,
            BasicStreamCodecs.BYTE,
            S2CAddEntityPacket::yRot,
            BasicStreamCodecs.BYTE,
            S2CAddEntityPacket::yHeadRot,
            ModernStreamCodecs.VAR_INT,
            S2CAddEntityPacket::data,
            S2CAddEntityPacket::new
    );

    public S2CAddEntityPacket(
            final int id,
            final UUID uuid,
            final int type,
            final Vec3d position,
            final Vec3d movement,
            final float xRot,
            final float yRot,
            final double yHeadRot,
            final int data
    ) {
        this(id, uuid, type, position, movement, Mth.packDegrees(xRot), Mth.packDegrees(yRot), Mth.packDegrees((float) yHeadRot), data);
    }

    @Override
    public ClientboundPlayPackets getType() {
        return ClientboundPlayPackets.ADD_ENTITY;
    }
}
