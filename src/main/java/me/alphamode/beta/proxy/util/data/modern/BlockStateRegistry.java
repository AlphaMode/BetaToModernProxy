package me.alphamode.beta.proxy.util.data.modern;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BlockStateRegistry implements IdMap<BlockState> {
	private final Int2ObjectMap<BlockState> idToState;

	public BlockStateRegistry(Int2ObjectMap<BlockState> idToState) {
		this.idToState = idToState;
	}

	@Override
	public int getId(BlockState thing) {
		if (idToState.containsKey(thing.networkId()))
			return thing.networkId();
		return DEFAULT;
	}

	@Override
	public BlockState byId(int id) {
		if (idToState.containsKey(id))
			return this.idToState.get(id);
		return null;
	}

	@Override
	public int size() {
		return this.idToState.size();
	}

	@Override
	public @NotNull Iterator<BlockState> iterator() {
		return this.idToState.values().iterator();
	}
}
