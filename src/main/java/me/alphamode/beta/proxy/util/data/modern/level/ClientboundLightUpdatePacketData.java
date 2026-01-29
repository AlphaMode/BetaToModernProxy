package me.alphamode.beta.proxy.util.data.modern.level;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.BitSet;
import java.util.List;

public class ClientboundLightUpdatePacketData {
	private static final StreamCodec<ByteBuf, byte[]> DATA_LAYER_STREAM_CODEC = ModernStreamCodecs.byteArray(2048);
	public static final StreamCodec<ByteBuf, ClientboundLightUpdatePacketData> CODEC = StreamCodec.ofMember(ClientboundLightUpdatePacketData::write, ClientboundLightUpdatePacketData::new);

	private final BitSet skyYMask;
	private final BitSet blockYMask;
	private final BitSet emptySkyYMask;
	private final BitSet emptyBlockYMask;
	private final List<byte[]> skyUpdates;
	private final List<byte[]> blockUpdates;

	public ClientboundLightUpdatePacketData(
			final BitSet skyYMask,
			final BitSet blockYMask,
			final BitSet emptySkyYMask,
			final BitSet emptyBlockYMask,
			final List<byte[]> skyUpdates,
			final List<byte[]> blockUpdates
	) {
		this.skyYMask = skyYMask;
		this.blockYMask = blockYMask;
		this.emptySkyYMask = emptySkyYMask;
		this.emptyBlockYMask = emptyBlockYMask;
		this.skyUpdates = skyUpdates;
		this.blockUpdates = blockUpdates;
	}

	public ClientboundLightUpdatePacketData(final ByteBuf buf) {
		this.skyYMask = ModernStreamCodecs.BIT_SET.decode(buf);
		this.blockYMask = ModernStreamCodecs.BIT_SET.decode(buf);
		this.emptySkyYMask = ModernStreamCodecs.BIT_SET.decode(buf);
		this.emptyBlockYMask = ModernStreamCodecs.BIT_SET.decode(buf);
		this.skyUpdates = ModernStreamCodecs.<byte[], List<byte[]>>collection(DATA_LAYER_STREAM_CODEC).decode(buf);
		this.blockUpdates = ModernStreamCodecs.<byte[], List<byte[]>>collection(DATA_LAYER_STREAM_CODEC).decode(buf);
	}

	public void write(final ByteBuf buf) {
		ModernStreamCodecs.BIT_SET.encode(buf, this.skyYMask);
		ModernStreamCodecs.BIT_SET.encode(buf, this.blockYMask);
		ModernStreamCodecs.BIT_SET.encode(buf, this.emptySkyYMask);
		ModernStreamCodecs.BIT_SET.encode(buf, this.emptyBlockYMask);
		ModernStreamCodecs.collection(DATA_LAYER_STREAM_CODEC).encode(buf, this.skyUpdates);
		ModernStreamCodecs.collection(DATA_LAYER_STREAM_CODEC).encode(buf, this.blockUpdates);
	}
}
