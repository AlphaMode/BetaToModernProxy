package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.PacketTypes;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

import java.util.List;

public class ModernPacketDecoder extends ByteToMessageDecoder {
    public static final String KEY = "modern-encoder";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final PacketRegistry packetRegistry = ctx.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
        if (packetRegistry == null) {
            out.add(in.readBytes(in.readableBytes()));
            return;
        }

        if (in.readableBytes() != 0) {
            final int packetId = PacketTypes.readVarInt(in);
            final Packet packet = packetRegistry.createPacket(packetId, in);
            out.add(packet);
        }
    }
}
