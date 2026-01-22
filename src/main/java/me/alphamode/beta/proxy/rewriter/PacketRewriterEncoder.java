package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;

import java.util.List;

// Proxy -> Client
public class PacketRewriterEncoder extends MessageToMessageEncoder<BetaRecordPacket> {
	private final Connection connection;

	public PacketRewriterEncoder(final Connection connection) {
		this.connection = connection;
	}

	@Override
	protected void encode(final ChannelHandlerContext ctx, final BetaRecordPacket msg, final List<Object> out) throws Exception {
		IO.println("encoding to client, beta -> modern packet");
//		out.add(msg);
	}
}
