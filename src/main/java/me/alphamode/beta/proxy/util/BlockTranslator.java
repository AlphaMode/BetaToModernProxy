package me.alphamode.beta.proxy.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.alphamode.beta.proxy.util.data.modern.BlockStateRegistry;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

public class BlockTranslator {
    private final Int2ObjectMap<BlockState> beta2ModernMapper;
    private final BlockStateRegistry stateRegistry;
    private final BlockState fallback;

    public BlockTranslator(final CompoundTag translations) {
        this.beta2ModernMapper = loadTranslations(translations);
        this.fallback = translate(1, 0);
        Int2ObjectMap<BlockState> idToState = new Int2ObjectOpenHashMap<>();
        beta2ModernMapper.values().forEach((blockState) -> {
            idToState.computeIfAbsent(blockState.networkId(), BlockState::new);
        });
        this.stateRegistry = new BlockStateRegistry(idToState);
    }

    public static int packIdAndData(int id, int data) {
        return data << 28 | id; // Pack data in the last 4 bits
    }

    public BlockState translate(int tile, int data) {
        int betaState = packIdAndData(tile, data);
        if (beta2ModernMapper.containsKey(betaState)) {
            return beta2ModernMapper.get(betaState);
        }

        return fallback;
    }

    private static Int2ObjectMap<BlockState> loadTranslations(final CompoundTag translations) {
        Int2ObjectMap<BlockState> modernMapper = new Int2ObjectOpenHashMap<>();
        translations.forEach(entry -> {
            int id = Integer.parseInt(entry.getKey());
            CompoundTag dataToState = entry.getValue().asCompoundTag();
            dataToState.forEach(translateKey -> {
                int data = Integer.parseInt(translateKey.getKey());
                modernMapper.put(packIdAndData(id, data), new BlockState(translateKey.getValue().asIntTag().getValue()));
            });
        });
        return modernMapper;
    }

    public BlockStateRegistry getBlockStateRegistry() {
        return stateRegistry;
    }
}
