package me.alphamode.beta.proxy.networking;

import io.netty.channel.*;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketReader;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServerConnection extends NetClient {
	private static final Logger LOGGER = LogManager.getLogger(ServerConnection.class);
	private final ClientConnection connection;

	ServerConnection(final MinecraftServerAddress address, final ClientConnection connection) {
		this.connection = connection;
		super(new ChannelInitializer<>() {
			@Override
			protected void initChannel(final Channel channel) {
				final ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(BetaPacketReader.KEY, new BetaPacketReader(connection));
				pipeline.addLast(BetaPacketWriter.KEY, new BetaPacketWriter(connection));
				pipeline.addLast("rewriter", new SimpleChannelInboundHandler<BetaPacket>() {
					@Override
					protected void channelRead0(final ChannelHandlerContext context, final BetaPacket msg) {
						if (connection.isConnected()) {
							connection.getActivePipeline().handleServer(connection, msg);
						}
					}
				});
			}

			@Override
			public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
				super.exceptionCaught(ctx, cause);
				cause.printStackTrace();
			}
		});

		this.connect(address).addListener(future -> {
			if (!future.isSuccess()) {
				LOGGER.info("Failed to connect proxy #{} to real server!", connection.getUniqueId());
				future.cause().printStackTrace();
				connection.disconnect();
				return;
			}

			if (!this.isConnected()) {
				LOGGER.info("Client #{} already has disconnected, closing the server connection!", connection.getUniqueId());
				this.disconnect();
				return;
			}

			LOGGER.info("Proxy #{} connected to {}", connection.getUniqueId(), address);
		});

		this.getChannel().closeFuture().addListener(_ -> {
			if (connection.isConnected()) {
				connection.kick("Server disconnected you.");
			}
		});
	}

	public void send(final BetaPacket packet) {
		if (this.isConnected()) {
			this.getChannel().writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead server connection!");
		}
	}

	public void disconnect() {
		LOGGER.info("Disconnected Proxy #{} from real server!", this.connection.getUniqueId());
		this.getChannel().close();
	}

	public boolean isConnected() {
		return this.getChannel().isActive();
	}
}
