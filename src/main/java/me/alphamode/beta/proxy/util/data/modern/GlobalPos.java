package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;

public record GlobalPos(ResourceKey<Identifier> dimension, BlockPos blockPos) {
	public static final StreamCodec<ByteStream, GlobalPos> CODEC = StreamCodec.composite(
			ResourceKey.streamCodec(Registries.DIMENSION),
			GlobalPos::dimension,
			BlockPos.CODEC,
			GlobalPos::blockPos,
			GlobalPos::of
	);

	public static GlobalPos of(final ResourceKey<Identifier> dimension, final BlockPos blockPos) {
		return new GlobalPos(dimension, blockPos);
	}
}
