package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.PacketTypes;

public interface ModernCodecs {
	StreamCodec<ByteBuf, Integer> VAR_INT = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Integer value) {
			PacketTypes.writeVarInt(buf, value);
		}

		@Override
		public Integer decode(final ByteBuf buf) {
			return PacketTypes.readVarInt(buf);
		}
	};
}
