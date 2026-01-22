package me.alphamode.beta.proxy.rewriter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;

import java.util.List;

public final class PacketRewriter extends MessageToMessageCodec<ModernRecordPacket<ModernPackets>, ByteBuf> {
	// C -> P
	@Override
	protected void encode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		IO.println(buf);
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		IO.println(packet);
	}
}
