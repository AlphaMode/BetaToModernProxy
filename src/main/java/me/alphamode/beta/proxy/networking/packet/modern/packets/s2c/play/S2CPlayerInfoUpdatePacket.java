package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.RemoteChatSession;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import net.lenni0451.mcstructs.text.TextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public record S2CPlayerInfoUpdatePacket(EnumSet<ActionType> types, List<Entry> entries) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CPlayerInfoUpdatePacket> CODEC = AbstractPacket.codec(S2CPlayerInfoUpdatePacket::write, S2CPlayerInfoUpdatePacket::read);

	public static S2CPlayerInfoUpdatePacket of(final Entry... entries) {
		final EnumSet<ActionType> types = EnumSet.noneOf(ActionType.class);
		for (Entry entry : entries) {
			for (Action<?> action : entry.actions) {
				types.add(action.type());
			}
		}

		return new S2CPlayerInfoUpdatePacket(types, List.of(entries));
	}

	public static S2CPlayerInfoUpdatePacket addPlayer(GameProfile profile) {
		return of(new Entry(profile.id(), new AddPlayer(profile)));
	}

	public static S2CPlayerInfoUpdatePacket read(final ByteStream buf) {
		final EnumSet<ActionType> types = ModernStreamCodecs.readEnumSet(buf, ActionType.class);
		return new S2CPlayerInfoUpdatePacket(types, ModernStreamCodecs.readList(buf, (decoder) -> {
			final UUID profileId = ModernStreamCodecs.UUID.decode(decoder);

			final List<Action<?>> actions = new ArrayList<>();
			for (final ActionType type : types) {
				actions.add(type.codec().decode(decoder));
			}

			return new Entry(profileId, actions);
		}));
	}

	public void write(final ByteStream buf) {
		ModernStreamCodecs.writeEnumSet(buf, this.types, ActionType.class);
		ModernStreamCodecs.writeCollection(buf, this.entries, (encoder, entry) -> {
			ModernStreamCodecs.UUID.encode(encoder, entry.profileId());

			for (final Action<?> action : entry.actions()) {
				((StreamCodec<ByteStream, Action<?>>) action.codec()).encode(encoder, action);
			}
		});
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.PLAYER_INFO_UPDATE;
	}

	public enum ActionType {
		ADD_PLAYER(AddPlayer.CODEC),
		INITIALIZE_CHAT(InitializeChat.CODEC),
		UPDATE_GAME_MODE(UpdateGameMode.CODEC),
		UPDATE_LISTED(UpdateListed.CODEC),
		UPDATE_LATENCY(UpdateLatency.CODEC),
		UPDATE_DISPLAY_NAME(null),
		UPDATE_LIST_ORDER(UpdateListOrder.CODEC),
		UPDATE_HAT(UpdateHat.CODEC);

		private final StreamCodec<ByteStream, ? extends Action<?>> codec;

		ActionType(final StreamCodec<ByteStream, ? extends Action<?>> codec) {
			this.codec = codec;
		}

		public StreamCodec<ByteStream, ? extends Action<?>> codec() {
			return this.codec;
		}
	}

	public record Entry(UUID profileId, List<Action<?>> actions) {
		public Entry(final UUID profileId, final Action<?>... actions) {
			this(profileId, List.of(actions));
		}
	}

	public sealed interface Action<T> permits AddPlayer, InitializeChat, UpdateGameMode, UpdateListed, UpdateLatency, UpdateDisplayName, UpdateListOrder, UpdateHat {
		StreamCodec<ByteStream, T> codec();

		ActionType type();
	}

	public record AddPlayer(String name, PropertyMap properties) implements Action<AddPlayer> {
		public static final int MAX_NAME_LENGTH = 16;
		public static final StreamCodec<ByteStream, String> NAME_CODEC = ModernStreamCodecs.stringUtf8(MAX_NAME_LENGTH);
		public static final StreamCodec<ByteStream, AddPlayer> CODEC = StreamCodec.composite(
				NAME_CODEC,
				AddPlayer::name,
				ModernStreamCodecs.GAME_PROFILE_PROPERTIES,
				AddPlayer::properties,
				AddPlayer::new
		);

		public AddPlayer(final GameProfile profile) {
			this(profile.name(), profile.properties());
		}

		@Override
		public StreamCodec<ByteStream, AddPlayer> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.ADD_PLAYER;
		}
	}

	public record InitializeChat(@Nullable RemoteChatSession.Data chatSession) implements Action<InitializeChat> {
		public static final StreamCodec<ByteStream, InitializeChat> CODEC = ModernStreamCodecs.nullable(RemoteChatSession.Data.CODEC).map(InitializeChat::new, InitializeChat::chatSession);

		@Override
		public StreamCodec<ByteStream, InitializeChat> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.INITIALIZE_CHAT;
		}
	}

	public record UpdateGameMode(GameMode gameMode) implements Action<UpdateGameMode> {
		public static final StreamCodec<ByteStream, UpdateGameMode> CODEC = GameMode.CODEC.map(UpdateGameMode::new, UpdateGameMode::gameMode);

		@Override
		public StreamCodec<ByteStream, UpdateGameMode> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_GAME_MODE;
		}
	}

	public record UpdateListed(boolean listed) implements Action<UpdateListed> {
		public static final StreamCodec<ByteStream, UpdateListed> CODEC = CommonStreamCodecs.BOOL.map(UpdateListed::new, UpdateListed::listed);

		@Override
		public StreamCodec<ByteStream, UpdateListed> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_LISTED;
		}
	}

	public record UpdateLatency(int latency) implements Action<UpdateLatency> {
		public static final StreamCodec<ByteStream, UpdateLatency> CODEC = ModernStreamCodecs.VAR_INT.map(UpdateLatency::new, UpdateLatency::latency);

		@Override
		public StreamCodec<ByteStream, UpdateLatency> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_LATENCY;
		}
	}

	public record UpdateDisplayName(TextComponent displayName) implements Action<UpdateDisplayName> {
		@Override
		public StreamCodec<ByteStream, UpdateDisplayName> codec() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_DISPLAY_NAME;
		}
	}

	public record UpdateListOrder(int listOrder) implements Action<UpdateListOrder> {
		public static final StreamCodec<ByteStream, UpdateListOrder> CODEC = ModernStreamCodecs.VAR_INT.map(UpdateListOrder::new, UpdateListOrder::listOrder);

		@Override
		public StreamCodec<ByteStream, UpdateListOrder> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_LIST_ORDER;
		}
	}

	public record UpdateHat(boolean showHat) implements Action<UpdateHat> {
		public static final StreamCodec<ByteStream, UpdateHat> CODEC = CommonStreamCodecs.BOOL.map(UpdateHat::new, UpdateHat::showHat);

		@Override
		public StreamCodec<ByteStream, UpdateHat> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.UPDATE_HAT;
		}
	}
}
