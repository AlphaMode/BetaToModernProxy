package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.Map;

public class TagNetworkSerialization {
	public static final class NetworkPayload {
		public static final StreamCodec<ByteBuf, NetworkPayload> CODEC = StreamCodec.composite(
				ModernStreamCodecs.map(ModernStreamCodecs.IDENTIFIER, ModernStreamCodecs.INT_LIST),
				(payload) -> payload.tags,
				NetworkPayload::new
		);

		private final Map<Identifier, IntList> tags;

		public NetworkPayload(Map<Identifier, IntList> tags) {
			this.tags = tags;
		}

		public boolean isEmpty() {
			return this.tags.isEmpty();
		}

		public int size() {
			return this.tags.size();
		}
	}
}
