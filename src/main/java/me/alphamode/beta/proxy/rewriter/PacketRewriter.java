package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import me.alphamode.beta.proxy.networking.packet.beta.packets.RecordPacket;

import java.util.List;

public final class PacketRewriter extends MessageToMessageCodec<RecordPacket<?>, RecordPacket<?>> {
	private final Direction direction;

	public PacketRewriter(final Direction direction) {
		this.direction = direction;
	}

	// P -> S
	// P -> C
	@Override
	protected void encode(final ChannelHandlerContext ctx, final RecordPacket<?> packet, final List<Object> out) throws Exception {
		out.add(packet);
	}

	// S -> P
	// C -> P
	@Override
	protected void decode(final ChannelHandlerContext ctx, final RecordPacket<?> packet, final List<Object> out) throws Exception {
		out.add(packet);
	}

	public Direction getDirection() {
		return direction;
	}

	public enum Direction {
		SERVERBOUND,
		CLIENTBOUND
	}
}
