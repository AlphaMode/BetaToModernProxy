package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import com.google.gson.JsonElement;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

public record S2CLoginDisconnectPacket(
		TextComponent reason) implements S2CCommonDisconnectPacket<ClientboundLoginPackets> {
	// TODO: make proper codec/cleanup (map?)
	public static final int MAX_REASON_LENGTH = 262144;
	public static final StreamCodec<ByteStream, JsonElement> REASON_CODEC = ModernStreamCodecs.lenientJson(MAX_REASON_LENGTH);
	private static final StreamCodec<ByteStream, TextComponent> REASON_COMPONENT_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final TextComponent component) {
			REASON_CODEC.encode(stream, TextComponentSerializer.LATEST.serializeJson(component));
		}

		@Override
		public TextComponent decode(final ByteStream stream) {
			return TextComponentSerializer.LATEST.deserialize(REASON_CODEC.decode(stream));
		}
	};

	public static final StreamCodec<ByteStream, S2CLoginDisconnectPacket> CODEC = StreamCodec.composite(
			REASON_COMPONENT_CODEC,
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
