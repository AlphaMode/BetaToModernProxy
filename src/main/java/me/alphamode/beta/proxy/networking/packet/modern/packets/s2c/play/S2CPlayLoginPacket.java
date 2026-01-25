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

public record S2CPlayLoginPacket(
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
	public static final StreamCodec<ByteBuf, S2CPlayLoginPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			S2CPlayLoginPacket::playerId,
			BasicStreamCodecs.BOOL,
			S2CPlayLoginPacket::hardcore,
			ModernStreamCodecs.collection(ResourceKey.streamCodec(Registries.DIMENSION)),
			S2CPlayLoginPacket::levels,
			ModernStreamCodecs.VAR_INT,
			S2CPlayLoginPacket::maxPlayers,
			ModernStreamCodecs.VAR_INT,
			S2CPlayLoginPacket::chunkRadius,
			ModernStreamCodecs.VAR_INT,
			S2CPlayLoginPacket::simulationDistance,
			BasicStreamCodecs.BOOL,
			S2CPlayLoginPacket::reducedDebugInfo,
			BasicStreamCodecs.BOOL,
			S2CPlayLoginPacket::showDeathScreen,
			BasicStreamCodecs.BOOL,
			S2CPlayLoginPacket::doLimitedCrafting,
			CommonPlayerSpawnInfo.CODEC,
			S2CPlayLoginPacket::commonPlayerSpawnInfo,
			BasicStreamCodecs.BOOL,
			S2CPlayLoginPacket::enforcesSecureChat,
			S2CPlayLoginPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.LOGIN;
	}
}
