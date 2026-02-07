package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2COpenScreenPacket(int containerId, WindowType windowType,
								  TextComponent title) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2COpenScreenPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2COpenScreenPacket::containerId,
			ModernStreamCodecs.javaEnum(WindowType.class),
			S2COpenScreenPacket::windowType,
			ModernStreamCodecs.TEXT_COMPONENT,
			S2COpenScreenPacket::title,
			S2COpenScreenPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.OPEN_SCREEN;
	}

	public enum WindowType {
		GENERIC_9x1,
		GENERIC_9x2,
		GENERIC_9x3,
		GENERIC_9x4,
		GENERIC_9x5,
		GENERIC_9x6,
		GENERIC_3x3,
		CRAFTER_3x3,
		ANVIL,
		BEACON,
		BLAST_FURNACE,
		BREWING_STAND,
		CRAFTING,
		ENCHANTMENT,
		FURNACE,
		GRINDSTONE,
		HOPPER,
		LECTERN,
		LOOM,
		MERCHANT,
		SHULKER_BOX,
		SMITHING,
		SMOKER,
		CARTOGRAPHY_TABLE,
		STONECUTTER;

		public static WindowType genericBySize(final int size) {
			if (size == (3 * 3)) {
				return S2COpenScreenPacket.WindowType.GENERIC_3x3;
			} else if (size == (9 * 2)) {
				return S2COpenScreenPacket.WindowType.GENERIC_9x2;
			} else if (size == (9 * 3)) {
				return S2COpenScreenPacket.WindowType.GENERIC_9x3;
			} else if (size == (9 * 4)) {
				return S2COpenScreenPacket.WindowType.GENERIC_9x4;
			} else if (size == (9 * 5)) {
				return S2COpenScreenPacket.WindowType.GENERIC_9x5;
			} else if (size == (9 * 6)) {
				return S2COpenScreenPacket.WindowType.GENERIC_9x6;
			} else {
				return null;
			}
		}
	}
}
