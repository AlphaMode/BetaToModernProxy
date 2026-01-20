package me.alphamode.beta.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.packet.PacketUtils;
import me.alphamode.beta.proxy.packet.beta.BetaPacketCodec;
import me.alphamode.beta.proxy.packet.beta.BetaPacketRegistry;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.netty.connection.NetServer;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

import java.net.InetSocketAddress;

public class Proxy extends ChannelInitializer<Channel> {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer();
        PacketUtils.writeString("gjSGJGOKSJGg gh", buf);
        IO.println(PacketUtils.readString(buf, 32));
        Proxy proxy = new Proxy();
        NetServer netServer = new NetServer(proxy);
        netServer.bind(new InetSocketAddress(args[0], 25566));
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
        ch.pipeline()
                .addLast(MCPipeline.PACKET_CODEC_HANDLER_NAME, new BetaPacketCodec())
                .addLast(new SimpleChannelInboundHandler<Packet>() {
            private Channel otherChannel;
            private final NetClient realServer = new NetClient(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel channel) {
                    channel.attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
                    channel.pipeline().addLast(MCPipeline.PACKET_CODEC_HANDLER_NAME, new BetaPacketCodec()).addLast(new SimpleChannelInboundHandler<Packet>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) {
                            otherChannel.writeAndFlush(packet);
//                            otherChannel.writeAndFlush(byteBuf.retain());
                        }
                    });
                }
            });

            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                this.otherChannel = ctx.channel();
                this.realServer.connect(MinecraftServerAddress.ofResolved("localhost")).syncUninterruptibly();
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet byteBuf) {
                this.realServer.getChannel().writeAndFlush(byteBuf);
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) {
                this.realServer.getChannel().close().syncUninterruptibly();
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                super.exceptionCaught(ctx, cause);
            }
        });
    }
}
