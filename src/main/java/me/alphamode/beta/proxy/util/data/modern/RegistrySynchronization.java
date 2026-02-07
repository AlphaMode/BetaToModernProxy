package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Optional;

public class RegistrySynchronization {
	public record PackedRegistryEntry(Identifier id, Optional<CompoundTag> data) {
		public static final StreamCodec<ByteStream, PackedRegistryEntry> CODEC = StreamCodec.composite(
				ModernStreamCodecs.IDENTIFIER,
				RegistrySynchronization.PackedRegistryEntry::id,
				ModernStreamCodecs.optional(ModernStreamCodecs.TAG),
				RegistrySynchronization.PackedRegistryEntry::data,
				RegistrySynchronization.PackedRegistryEntry::new
		);
	}
}
