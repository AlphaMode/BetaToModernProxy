package me.alphamode.beta.proxy.util;

import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

public class NbtUtil {
    public static CompoundTag readCompressed(final Path path) throws IOException {
        return readCompressed(new FileInputStream(path.toFile()));
    }
    public static CompoundTag readCompressed(final InputStream stream) throws IOException {
        return NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(stream)), new NbtReadTracker()).getTag().asCompoundTag();
    }
}
