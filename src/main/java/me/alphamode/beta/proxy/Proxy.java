package me.alphamode.beta.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.C2PChannel;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketDecoder;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.netty.connection.NetServer;
import net.raphimc.netminecraft.packet.registry.DefaultPacketRegistry;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

import java.net.InetSocketAddress;

public final class Proxy extends ChannelInitializer<Channel> {
    public static final AttributeKey<BetaPacketRegistry> PACKET_REGISTRY_ATTRIBUTE_KEY = AttributeKey.valueOf("beta_packet_registry");
	private final String serverIp;

	public Proxy(final String ip) {
		this.serverIp = ip;
	}

	static void main(String[] args) {
		final String bindAddress = args[0];
		final int bindPort = Integer.parseInt(args[1]);
		final String serverAddress = args[2];
		IO.println(String.format("Listening on %s:%d -> %s", bindAddress, bindPort, serverAddress));
		new NetServer(new Proxy(serverAddress))
				.bind(new InetSocketAddress(bindAddress, bindPort));
	}

	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
        channel.attr(MCPipeline.ENCRYPTION_ATTRIBUTE_KEY).set(null);
        channel.attr(MCPipeline.COMPRESSION_THRESHOLD_ATTRIBUTE_KEY).set(-1);
        channel.attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).set(null);
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
//		channel.pipeline().addLast(BetaPacketDecoder.KEY, new BetaPacketDecoder()); TODO: Modern packet decoder
//        channel.pipeline().addLast(MCPipeline.SIZER_HANDLER_NAME, new PacketSizer());


        channel.pipeline().addLast(MCPipeline.ENCRYPTION_HANDLER_NAME, MCPipeline.ENCRYPTION_HANDLER.get());
        channel.pipeline().addLast(MCPipeline.SIZER_HANDLER_NAME, MCPipeline.SIZER_HANDLER.get());
        channel.pipeline().addLast(MCPipeline.FLOW_CONTROL_HANDLER_NAME, MCPipeline.FLOW_CONTROL_HANDLER.get());
        channel.pipeline().addLast(MCPipeline.COMPRESSION_HANDLER_NAME, MCPipeline.COMPRESSION_HANDLER.get());
        channel.pipeline().addLast(ModernPacketDecoder.KEY, new ModernPacketDecoder());
//        channel.pipeline().addLast(MCPipeline.PACKET_CODEC_HANDLER_NAME, MCPipeline.PACKET_CODEC_HANDLER.get());
//        channel.pipeline().addLast(MCPipeline.HANDLER_HANDLER_NAME, this.handlerSupplier.get());

		channel.pipeline().addLast(new C2PChannel(this.serverIp));
	}
}
