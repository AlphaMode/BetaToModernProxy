package me.alphamode.beta.proxy.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface NbtDataProvider extends DataProvider {
    Logger LOGGER = LogUtils.getLogger();

    static CompletableFuture<?> saveStable(final CachedOutput cache, final CompoundTag root, final Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                HashingOutputStream hashedBytes = new HashingOutputStream(Hashing.sha1(), bytes);
                NbtIo.writeCompressed(root, hashedBytes);
                cache.writeIfNeeded(path, bytes.toByteArray(), hashedBytes.hash());
            } catch (IOException var10) {
                LOGGER.error("Failed to save file to {}", path, var10);
            }
        }, Util.backgroundExecutor().forName("saveStable"));
    }
}
