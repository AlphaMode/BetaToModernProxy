package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public record MessageSignature(byte[] values) {
	public static final StreamCodec<ByteStream, MessageSignature> CODEC = StreamCodec.composite(
			ModernStreamCodecs.sizedByteArray(256),
			MessageSignature::values,
			MessageSignature::new
	);

	public MessageSignature {
		if (values.length != 256) {
			throw new IllegalArgumentException("Invalid message signature size");
		}
	}

	public record Packed(int id, @Nullable MessageSignature fullSignature) {
		public static final StreamCodec<ByteStream, Packed> CODEC = StreamCodec.ofMember(Packed::write, Packed::new);

		public Packed(final MessageSignature signature) {
			this(-1, signature);
		}

		public Packed(final int id) {
			this(id, null);
		}

		public Packed(final ByteStream buf) {
			final int id = ModernStreamCodecs.VAR_INT.decode(buf) - 1;

			MessageSignature signature = null;
			if (id == -1) {
				signature = MessageSignature.CODEC.decode(buf);
			}

			this(id, signature);
		}

		public void write(final ByteStream buf) {
			ModernStreamCodecs.VAR_INT.encode(buf, this.id() + 1);
			if (this.fullSignature() != null) {
				MessageSignature.CODEC.encode(buf, this.fullSignature());
			}
		}
	}
}
