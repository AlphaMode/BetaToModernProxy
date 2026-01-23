package me.alphamode.beta.proxy.util;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.BrodernProxy;

public interface DebugUtil {
	static void printBuf(final ByteBuf buf) {
		final StringBuilder builder = new StringBuilder();
		builder.append('[');

		final int offset = buf.readerIndex();
		for (int i = 0; i < buf.capacity() - offset; ++i) {
			builder.append(buf.getByte(i + offset));
			if (i < buf.capacity() - offset - 1) {
				builder.append(", ");
			}
		}

		builder.append(']');
		BrodernProxy.LOGGER.info(builder.toString());
	}
}
