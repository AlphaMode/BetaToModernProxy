package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

// TODO/FIX
public record S2CPlayerInfoUpdatePacket(EnumSet<Action> actions, List<Entry> entries) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CPlayerInfoUpdatePacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.enumSet(Action.class),
			S2CPlayerInfoUpdatePacket::actions,
			ModernStreamCodecs.collection(Entry.CODEC),
			S2CPlayerInfoUpdatePacket::entries,
			S2CPlayerInfoUpdatePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.PLAYER_INFO_UPDATE;
	}

	public record Entry(UUID uuid, List<Action> actions) {
		public static final StreamCodec<ByteBuf, Entry> CODEC = StreamCodec.composite(
				ModernStreamCodecs.UUID,
				Entry::uuid,
				ModernStreamCodecs.collection(ModernStreamCodecs.javaEnum(Action.class)),
				Entry::actions,
				Entry::new
		);
	}

	public enum Action {
		ADD_PLAYER,
		INITIALIZE_CHAT,
		UPDATE_GAME_MODE,
		UPDATE_LISTED,
		UPDATE_LATENCY,
		UPDATE_DISPLAY_NAME,
		UPDATE_LIST_ORDER,
		UPDATE_HAT
	}
}
