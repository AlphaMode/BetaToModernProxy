package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerOpenPacket(short containerId, MenuType type, String title, short size) implements BetaPacket {
	public static final StreamCodec<ByteStream, ContainerOpenPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::containerId,
			BetaStreamCodecs.javaEnum(MenuType.class),
			ContainerOpenPacket::type,
			BetaStreamCodecs.stringJava(),
			ContainerOpenPacket::title,
			CommonStreamCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::size,
			ContainerOpenPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_OPEN;
	}

	public enum MenuType {
		BASIC,
		CRAFTING,
		FURNACE,
		DISPENSER,
	}
}
