package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Optional;

public class RegistrySynchronization {
	public record PackedRegistryEntry(Identifier id, Optional<CompoundTag> data) {
		public static final StreamCodec<ByteBuf, PackedRegistryEntry> CODEC = StreamCodec.composite(
				ModernCodecs.IDENTIFIER,
				RegistrySynchronization.PackedRegistryEntry::id,
				ModernCodecs.optional(ModernCodecs.TAG),
				RegistrySynchronization.PackedRegistryEntry::data,
				RegistrySynchronization.PackedRegistryEntry::new
		);
	}
}
