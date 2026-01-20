package me.alphamode.beta.proxy.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.PacketTypes;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

import java.util.List;

public class BetaPacketCodec extends ByteToMessageCodec<Packet> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        final PacketRegistry packetRegistry = ctx.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
        if (packetRegistry == null) {
            out.add(in.readBytes(in.readableBytes()));
            return;
        }

        if (in.readableBytes() != 0) {
            final int packetId = in.readByte();
            final Packet packet = packetRegistry.createPacket(packetId, in);
            out.add(packet);
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) {
        final PacketRegistry packetRegistry = ctx.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
        if (packetRegistry == null) {
            throw new IllegalStateException("Can't write Packet without a packet registry");
        }

        out.writeByte(packetRegistry.getPacketId(in));
        in.write(out, packetRegistry.getProtocolVersion());
    }
}
