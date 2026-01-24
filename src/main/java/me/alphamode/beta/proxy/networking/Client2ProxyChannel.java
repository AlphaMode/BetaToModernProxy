package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Packets
public final class Client2ProxyChannel extends ChannelInitializer<Channel> {
	private static final Logger LOGGER = LogManager.getLogger(Client2ProxyChannel.class);
	public static final AttributeKey<Connection> CONNECTION_KEY = AttributeKey.newInstance("connection");
	private final MinecraftServerAddress address;

	public Client2ProxyChannel(final MinecraftServerAddress address) {
		this.address = address;
	}

	// Client -> Proxy
	@Override
	protected void initChannel(final Channel channel) {
		final Connection connection = new Connection(this.address);
		channel.attr(CONNECTION_KEY).set(connection);

		final ChannelPipeline pipeline = channel.pipeline();

		// Reads Prefixed Length & Splits Packets
		pipeline.addLast(new PacketSizer());

		// ByteBuf -> ModernPacket
		pipeline.addLast(ModernPacketReader.KEY, new ModernPacketReader(connection));

        pipeline.addLast(connection);

		// BetaPacket -> ByteBuf
		pipeline.addLast(BetaPacketWriter.KEY, new BetaPacketWriter());

		// _
        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                ctx.channel().writeAndFlush(msg);
            }
        });
	}
}
