package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.CommonPlayerSpawnInfo;
import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.Collection;

public record S2CLoginPacket(
		int playerId,
		boolean hardcore,
		Collection<ResourceKey<Identifier>> levels,
		int maxPlayers,
		int chunkRadius,
		int simulationDistance,
		boolean reducedDebugInfo,
		boolean showDeathScreen,
		boolean doLimitedCrafting,
		CommonPlayerSpawnInfo commonPlayerSpawnInfo,
		boolean enforcesSecureChat
) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CLoginPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			S2CLoginPacket::playerId,
			BasicStreamCodecs.BOOL,
			S2CLoginPacket::hardcore,
			ModernStreamCodecs.collection(ResourceKey.streamCodec(Registries.DIMENSION)),
			S2CLoginPacket::levels,
			BasicStreamCodecs.INT,
			S2CLoginPacket::maxPlayers,
			BasicStreamCodecs.INT,
			S2CLoginPacket::chunkRadius,
			BasicStreamCodecs.INT,
			S2CLoginPacket::simulationDistance,
			BasicStreamCodecs.BOOL,
			S2CLoginPacket::reducedDebugInfo,
			BasicStreamCodecs.BOOL,
			S2CLoginPacket::showDeathScreen,
			BasicStreamCodecs.BOOL,
			S2CLoginPacket::doLimitedCrafting,
			CommonPlayerSpawnInfo.CODEC,
			S2CLoginPacket::commonPlayerSpawnInfo,
			BasicStreamCodecs.BOOL,
			S2CLoginPacket::enforcesSecureChat,
			S2CLoginPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.LOGIN;
	}
}
