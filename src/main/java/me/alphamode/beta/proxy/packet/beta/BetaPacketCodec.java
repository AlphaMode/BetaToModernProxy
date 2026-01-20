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

        int packetsRead = 0;
        while (in.readableBytes() != 0) {
            packetsRead++;
            if (packetsRead > 1) {
                in.markReaderIndex();
                printBuf(in);
                in.resetReaderIndex();
            }
            final int packetId = in.readByte();
            final Packet packet = packetRegistry.createPacket(packetId, in);
            out.add(packet);
        }
        if (packetsRead > 1) {
            StringBuilder packets = new StringBuilder();
            for (Object obj : out) {
                if (obj instanceof Packet packet) {
                    packets.append(packet.getClass().getSimpleName()).append(" ");
                }
            }
            IO.println(packetsRead + " packets read " + packets);
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

    private static void printBuf(final ByteBuf byteBuffer) {
        final StringBuilder builder = new StringBuilder();
        builder.append('[');

        for (int i = 0; i < byteBuffer.capacity(); ++i) {
            builder.append(byteBuffer.getByte(i));
            if (i < byteBuffer.capacity() - 1) {
                builder.append(", ");
            }
        }

        builder.append(']');
        IO.println(builder.toString());
    }
}
