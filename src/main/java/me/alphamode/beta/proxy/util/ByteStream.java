package me.alphamode.beta.proxy.util;

public interface ByteStream {
	// Reader
	byte readByte();

	short readUnsignedByte();

	void readBytes(byte[] bytes);

	boolean readBoolean();

	char readChar();

	short readShort();

	int readInt();

	long readUnsignedInt();

	float readFloat();

	double readDouble();

	long readLong();

	int readerIndex();

	void readerIndex(int index);

	// Writer
	void writeByte(byte value);

	void writeBytes(byte[] bytes);

	void writeBoolean(boolean value);

	void writeChar(char value);

	void writeShort(short value);

	void writeInt(int value);

	void writeFloat(float value);

	void writeDouble(double value);

	long writeLong(long value);

	int writerIndex();

	void writerIndex(int index);

	// Other
	byte getByte(int index);

	int capacity();

	int readableBytes();

	void release();
}
