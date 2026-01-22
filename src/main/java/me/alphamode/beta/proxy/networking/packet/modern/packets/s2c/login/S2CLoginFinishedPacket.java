package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;

public record S2CLoginFinishedPacket(GameProfile profile) implements S2CLoginPacket {
	public static final StreamCodec<ByteBuf, S2CLoginFinishedPacket> CODEC = StreamCodec.composite(
			GameProfile.CODEC,
			S2CLoginFinishedPacket::profile,
			S2CLoginFinishedPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.FINISHED;
	}
}
