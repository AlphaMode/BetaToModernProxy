package me.alphamode.beta.proxy.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public record NettyByteStream(ByteBuf buf) implements ByteStream {
	public static NettyByteStream of() {
		return new NettyByteStream(ByteBufAllocator.DEFAULT.buffer());
	}

	public static NettyByteStream of(final ByteBuf buf) {
		return new NettyByteStream(buf);
	}

	public static ByteBuf unwrap(final ByteStream stream) {
		return stream instanceof NettyByteStream(final ByteBuf buf) ? buf : null;
	}

	@Override
	public byte readByte() {
		return this.buf.readByte();
	}

	@Override
	public short readUnsignedByte() {
		return this.buf.readUnsignedByte();
	}

	@Override
	public void readBytes(final byte[] bytes) {
		this.buf.readBytes(bytes);
	}

	@Override
	public boolean readBoolean() {
		return this.buf.readBoolean();
	}

	@Override
	public char readChar() {
		return this.buf.readChar();
	}

	@Override
	public short readShort() {
		return this.buf.readShort();
	}

	@Override
	public int readInt() {
		return this.buf.readInt();
	}

	@Override
	public long readUnsignedInt() {
		return this.buf.readUnsignedInt();
	}

	@Override
	public float readFloat() {
		return this.buf.readFloat();
	}

	@Override
	public double readDouble() {
		return this.buf.readDouble();
	}

	@Override
	public long readLong() {
		return this.buf.readLong();
	}

	@Override
	public int readerIndex() {
		return this.buf.readerIndex();
	}

	@Override
	public void readerIndex(final int index) {
		this.buf.readerIndex(index);
	}

	@Override
	public void writeByte(final byte value) {
		this.buf.writeByte(value);
	}

	@Override
	public void writeBytes(final byte[] bytes) {
		this.buf.writeBytes(bytes);
	}

	@Override
	public void writeBoolean(final boolean value) {
		this.buf.writeBoolean(value);
	}

	@Override
	public void writeChar(final char value) {
		this.buf.writeChar(value);
	}

	@Override
	public void writeShort(final short value) {
		this.buf.writeShort(value);
	}

	@Override
	public void writeInt(final int value) {
		this.buf.writeInt(value);
	}

	@Override
	public void writeFloat(final float value) {
		this.buf.writeFloat(value);
	}

	@Override
	public void writeDouble(final double value) {
		this.buf.writeDouble(value);
	}

	@Override
	public void writeLong(final long value) {
		this.buf.writeLong(value);
	}

	@Override
	public int writerIndex() {
		return this.buf.writerIndex();
	}

	@Override
	public void writerIndex(final int index) {
		this.buf.writerIndex(index);
	}

	@Override
	public byte getByte(final int index) {
		return this.buf.getByte(index);
	}

	@Override
	public int capacity() {
		return this.buf.capacity();
	}

	@Override
	public int size() {
		return this.buf.readableBytes();
	}

	@Override
	public void close() {
		this.buf.release();
	}
}
