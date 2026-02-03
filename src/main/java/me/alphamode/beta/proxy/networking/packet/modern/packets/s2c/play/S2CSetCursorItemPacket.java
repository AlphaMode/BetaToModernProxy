package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;

public record S2CSetCursorItemPacket(ModernItemStack stack) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CSetCursorItemPacket> CODEC = StreamCodec.composite(
			ModernItemStack.OPTIONAL_CODEC,
			S2CSetCursorItemPacket::stack,
			S2CSetCursorItemPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_CURSOR_ITEM;
	}
}
