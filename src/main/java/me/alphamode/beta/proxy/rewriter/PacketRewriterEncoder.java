package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;

import java.util.List;

// Proxy -> Client
public final class PacketRewriterEncoder extends MessageToMessageEncoder<BetaRecordPacket> {
	@Override
	protected void encode(final ChannelHandlerContext ctx, final BetaRecordPacket msg, final List<Object> out) throws Exception {
		// TODO
	}
}
