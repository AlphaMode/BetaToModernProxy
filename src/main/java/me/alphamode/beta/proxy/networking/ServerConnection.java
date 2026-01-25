package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketReader;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServerConnection extends ChannelInitializer<Channel> {
	private static final Logger LOGGER = LogManager.getLogger(ServerConnection.class);
	private final ClientConnection connection;
	private Channel serverChannel;

	ServerConnection(final ClientConnection connection) {
		this.connection = connection;
	}

	public void send(final BetaRecordPacket packet) {
		if (this.isConnected()) {
			this.serverChannel.writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead server connection!");
		}
	}

	public void disconnect() {
		if (this.serverChannel != null) {
			LOGGER.info("Disconnected Proxy #{} from real server!", this.connection.getId());
			this.serverChannel.close();
			this.serverChannel = null;
		}
	}

	public boolean isConnected() {
		return this.serverChannel != null && this.serverChannel.isActive();
	}

	// Proxy -> Client
	@Override
	protected void initChannel(final Channel channel) {
		final ChannelPipeline pipeline = channel.pipeline();

		// ByteBuf -> BetaPacket
		pipeline.addLast(BetaPacketReader.KEY, new BetaPacketReader());

		// Connection consumes BetaPacket and rewrites to a modern packet and sends it to the client
		pipeline.addLast("rewriter", new SimpleChannelInboundHandler<BetaRecordPacket>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final BetaRecordPacket msg) {
				connection.getActivePipeline().handleServer(connection, msg);
			}
		});

		// BetaPacket -> ByteBuf
		pipeline.addLast(BetaPacketWriter.KEY, new BetaPacketWriter());

		pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final ByteBuf buf) {
				context.channel().writeAndFlush(buf);
			}
		});
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		LOGGER.warn("Real Server for Proxy #{} Became Inactive, Disconnecting client...", this.connection.getId());
		this.connection.disconnect();
	}
}
