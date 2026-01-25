package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

public record S2CLoginDisconnectPacket(
		TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundLoginPackets> {
	// TODO: make proper codec/cleanup
	private static final StreamCodec<ByteBuf, TextComponent> TEXT_COMPONENT_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final TextComponent component) {
			ModernStreamCodecs.lenientJson(262144).encode(buf, TextComponentSerializer.LATEST.serializeJson(component));
		}

		@Override
		public TextComponent decode(final ByteBuf buf) {
			return TextComponentSerializer.LATEST.deserialize(ModernStreamCodecs.lenientJson(262144).decode(buf));
		}
	};

	public static final StreamCodec<ByteBuf, S2CLoginDisconnectPacket> CODEC = StreamCodec.composite(
			TEXT_COMPONENT_CODEC,
			S2CLoginDisconnectPacket::reason,
			S2CLoginDisconnectPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.DISCONNECT;
	}

	@Override
	public PacketState getState() {
		return PacketState.LOGIN;
	}

	@Override
	public TextComponent getReason() {
		return this.reason;
	}
}
