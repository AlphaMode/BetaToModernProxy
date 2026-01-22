package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SSelectBundleItemPacket(int slotId, int selectedItemIndex) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SSelectBundleItemPacket> CODEC = StreamCodec.composite(
			ModernCodecs.VAR_INT,
			C2SSelectBundleItemPacket::slotId,
			ModernCodecs.VAR_INT,
			C2SSelectBundleItemPacket::selectedItemIndex,
			C2SSelectBundleItemPacket::new
	);

	public C2SSelectBundleItemPacket {
		if (selectedItemIndex < 0 && selectedItemIndex != -1) {
			throw new IllegalArgumentException("Invalid selectedItemIndex: " + selectedItemIndex);
		}
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.BUNDLE_ITEM_SELECTED;
	}
}
