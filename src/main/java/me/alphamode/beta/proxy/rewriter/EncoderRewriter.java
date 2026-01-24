package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CSystemChatPacket;
import net.lenni0451.mcstructs.text.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EncoderRewriter extends Rewriter<BetaRecordPacket> {
	private static final Logger LOGGER = LogManager.getLogger(EncoderRewriter.class);

	@Override
	public void registerPackets() {
		this.registerClientboundRewriter(KeepAlivePacket.class, (connection, _) -> {
			final long lastMs = connection.getLastKeepAliveMS();
			connection.setLastKeepAliveMS(System.currentTimeMillis());
			connection.sendToClient(connection.createKeepAlivePacket(lastMs));
		});

		this.registerClientboundRewriter(LoginPacket.class, (_, _) -> {
		});

		this.registerClientboundRewriter(HandshakePacket.class, (connection, packet) -> {
			// connection.sendToClient(new S2CHelloPacket("", new byte[0], new byte[0], false));
			if (packet.username().equals("-")) {
				connection.sendToServer(new LoginPacket(BetaRecordPacket.PROTOCOL_VERSION, connection.getUsername()));
			} else {
				connection.kick("Online mode isn't supported!");
			}
		});

		this.registerClientboundRewriter(ChatPacket.class, (connection, packet) -> {
			// connection.sendToClient(new S2CPlayerChatPacket());
			LOGGER.info("{}", packet.message());
			connection.sendToClient(new S2CSystemChatPacket(TextComponent.of(packet.message()), false));
		});

		this.registerClientboundRewriter(SetTimePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetEquippedItemPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetSpawnPositionPacket.class, (_, packet) -> {
		});

		this.registerClientboundRewriter(InteractPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetHealthPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(PlayerChangeDimensionPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MovePlayerPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MovePlayerPacket.Pos.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MovePlayerPacket.Rot.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MovePlayerPacket.PosRot.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(PlayerActionPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(UseItemPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetCarriedItemPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(InteractionPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AnimatePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(PlayerCommandPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddPlayerPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddItemEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(TakeItemEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddMobPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddPaintingPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(PlayerInputPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetEntityMotionPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(RemoveEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MoveEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MoveEntityPacket.Pos.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MoveEntityPacket.Rot.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MoveEntityPacket.PosRot.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(TeleportEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(EntityEventPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetRidingPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SetEntityDataPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ChunkVisibilityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(BlockRegionUpdatePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ChunkTilesUpdatePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(TileUpdatePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(TileEventPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ExplodePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(LevelEventPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(GameEventPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(AddGlobalEntityPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerOpenPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerClosePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerClickPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerSetSlotPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerSetContentPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerSetDataPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(ContainerAckPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(SignUpdatePacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(MapItemDataPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(UpdateStatPacket.class, (connection, packet) -> {
		});

		this.registerClientboundRewriter(DisconnectPacket.class, (connection, packet) -> {
			connection.kick(packet.reason());
		});
	}

	@Override
	public void rewrite(final Connection connection, final BetaRecordPacket packet) {
		final RewriterFactory<BetaRecordPacket> rewriter = this.getClientboundRewriter(packet.getClass());
		if (rewriter != null) {
			rewriter.rewrite(connection, packet);
		}
	}
}
