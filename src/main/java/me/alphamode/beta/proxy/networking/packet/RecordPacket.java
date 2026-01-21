package me.alphamode.beta.proxy.networking.packet;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.codec.StreamDecoder;
import me.alphamode.beta.proxy.util.codec.StreamMemberEncoder;

public interface RecordPacket<T> {
	T getType();

	static <B extends ByteBuf, T extends RecordPacket<?>> StreamCodec<B, T> codec(final StreamMemberEncoder<B, T> writer, final StreamDecoder<B, T> reader) {
		return StreamCodec.ofMember(writer, reader);
	}
}
