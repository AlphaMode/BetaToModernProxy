package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.Input;

public record C2SPlayerInputPacket(Input input) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SPlayerInputPacket> CODEC = StreamCodec.composite(
			Input.CODEC,
			C2SPlayerInputPacket::input,
			C2SPlayerInputPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PLAYER_INPUT;
	}
}
