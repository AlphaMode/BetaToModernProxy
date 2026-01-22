package me.alphamode.beta.proxy.rewriter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;

import java.util.List;

public final class PacketRewriter extends MessageToMessageCodec<BetaRecordPacket, ByteBuf> {
	// C -> P
	@Override
	protected void encode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final BetaRecordPacket packet, final List<Object> out) throws Exception {
	}
}
