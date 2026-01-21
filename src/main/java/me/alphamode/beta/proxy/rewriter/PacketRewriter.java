package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;

import java.util.List;

public final class PacketRewriter extends MessageToMessageCodec<RecordPacket<?>, RecordPacket<?>> {
	private final PacketDirection direction;

	public PacketRewriter(final PacketDirection direction) {
		this.direction = direction;
	}

	// C -> P
	@Override
	protected void encode(final ChannelHandlerContext ctx, final RecordPacket<?> packet, final List<Object> out) throws Exception {
		out.add(packet);
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext ctx, final RecordPacket<?> packet, final List<Object> out) throws Exception {
		out.add(packet);
	}

	public PacketDirection getDirection() {
		return direction;
	}
}
