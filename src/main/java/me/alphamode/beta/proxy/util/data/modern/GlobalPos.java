package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;

public record GlobalPos(ResourceKey<Identifier> dimension, BlockPos pos) {
	public static final StreamCodec<ByteBuf, GlobalPos> CODEC = StreamCodec.composite(
			ResourceKey.streamCodec(Registries.DIMENSION),
			GlobalPos::dimension,
			BlockPos.CODEC,
			GlobalPos::pos,
			GlobalPos::of
	);

	public static GlobalPos of(ResourceKey<Identifier> dimension, BlockPos pos) {
		return new GlobalPos(dimension, pos);
	}
}
