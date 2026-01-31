package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;

public record S2COpenScreenPacket(int containerId, WindowType windowType,
								  TextComponent title) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2COpenScreenPacket> CODEC = StreamCodec.composite(
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
		STONECUTTER
	}
}
