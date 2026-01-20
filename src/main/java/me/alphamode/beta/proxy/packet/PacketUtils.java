package me.alphamode.beta.proxy.packet;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketUtils {

    public static void writeString(String msg, ByteBuf data) {
        if (msg.length() > 32767) {
            throw new RuntimeException("String too big");
        } else {
            data.writeShort(msg.length());
            data.writeCharSequence(msg, StandardCharsets.UTF_16BE);
        }
    }

    public static String readString(ByteBuf data, int length) {
        int i = data.readShort();
        if (i > length) {
            throw new RuntimeException("Received string length longer than maximum allowed (" + i + " > " + length + ")");
        } else if (i < 0) {
            throw new RuntimeException("Received string length is less than zero! Weird string!");
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < i; j++) {
                stringBuilder.append(data.readChar());
            }

            return stringBuilder.toString();
        }
    }

}
