package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import com.mojang.authlib.GameProfile;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CLoginFinishedPacket(GameProfile profile) implements S2CLoginPacket {
	public static final StreamCodec<ByteStream, S2CLoginFinishedPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.GAME_PROFILE,
			S2CLoginFinishedPacket::profile,
			S2CLoginFinishedPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.FINISHED;
	}
}
