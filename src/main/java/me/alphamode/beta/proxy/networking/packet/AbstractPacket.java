package me.alphamode.beta.proxy.networking.packet;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.codec.StreamDecoder;
import me.alphamode.beta.proxy.util.codec.StreamMemberEncoder;

public interface AbstractPacket<T extends Packets> {
	T getType();

	static <B extends ByteStream, T extends AbstractPacket<?>> StreamCodec<B, T> codec(final StreamMemberEncoder<B, T> writer, final StreamDecoder<B, T> reader) {
		return StreamCodec.ofMember(writer, reader);
	}
}
