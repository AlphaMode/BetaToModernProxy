package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.RemoteChatSession;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import net.lenni0451.mcstructs.text.TextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public record S2CPlayerInfoUpdatePacket(EnumSet<ActionType> types, List<Entry> entries) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CPlayerInfoUpdatePacket> CODEC = AbstractPacket.codec(S2CPlayerInfoUpdatePacket::write, S2CPlayerInfoUpdatePacket::read);

    public static S2CPlayerInfoUpdatePacket of(final Entry... entries) {
        EnumSet<ActionType> types = EnumSet.noneOf(ActionType.class);
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

    public static S2CPlayerInfoUpdatePacket read(final ByteBuf buf) {
        EnumSet<ActionType> types = ModernStreamCodecs.readEnumSet(buf, ActionType.class);
        var entries = ModernStreamCodecs.readList(buf, (decoder) -> {
            UUID profileId = ModernStreamCodecs.UUID.decode(decoder);

            List<Action<?>> actions = new ArrayList<>();
            for(ActionType type : types) {
                actions.add(type.codec().decode(decoder));
            }

            return new Entry(profileId, actions);
        });

        return new S2CPlayerInfoUpdatePacket(types, entries);
    }

    public void write(final ByteBuf buf) {
        ModernStreamCodecs.writeEnumSet(buf, this.types, ActionType.class);
        ModernStreamCodecs.writeCollection(buf, this.entries, (encoder, entry) -> {
            ModernStreamCodecs.UUID.encode(encoder, entry.profileId());

            for(Action action : entry.actions()) {
                action.codec().encode(encoder, action);
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
        UPDATE_HAT(UpdateHat.CODEC),;

        private final StreamCodec<ByteBuf, ? extends Action<?>> codec;

        ActionType(StreamCodec<ByteBuf, ? extends Action<?>> codec) {
            this.codec = codec;
        }

        public StreamCodec<ByteBuf, ? extends Action<?>> codec() {
            return codec;
        }
    }

	public record Entry(UUID profileId, List<Action<?>> actions) {
        public Entry(final UUID profileId, final Action<?>... actions) {
            this(profileId, List.of(actions));
        }
	}

    public sealed interface Action<T> permits AddPlayer, InitializeChat, UpdateGameMode, UpdateListed, UpdateLatency, UpdateDisplayName, UpdateListOrder, UpdateHat {
        StreamCodec<ByteBuf, T> codec();

        ActionType type();
    }

    public record AddPlayer(String name, PropertyMap properties) implements Action<AddPlayer> {
        public static final StreamCodec<ByteBuf, AddPlayer> CODEC = StreamCodec.composite(
                ModernStreamCodecs.PLAYER_NAME,
                AddPlayer::name,
                ModernStreamCodecs.GAME_PROFILE_PROPERTIES,
                AddPlayer::properties,
                AddPlayer::new
        );

        public AddPlayer(GameProfile profile) {
            this(profile.name(), profile.properties());
        }

        @Override
        public StreamCodec<ByteBuf, AddPlayer> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.ADD_PLAYER;
        }
    }

    public record InitializeChat(@Nullable RemoteChatSession.Data chatSession) implements Action<InitializeChat> {
        public static final StreamCodec<ByteBuf, InitializeChat> CODEC = ModernStreamCodecs.nullable(RemoteChatSession.Data.CODEC).map(InitializeChat::new, InitializeChat::chatSession);

        @Override
        public StreamCodec<ByteBuf, InitializeChat> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.INITIALIZE_CHAT;
        }
    }

    public record UpdateGameMode(GameMode gameMode) implements Action<UpdateGameMode> {
        public static final StreamCodec<ByteBuf, UpdateGameMode> CODEC = GameMode.CODEC.map(UpdateGameMode::new, UpdateGameMode::gameMode);

        @Override
        public StreamCodec<ByteBuf, UpdateGameMode> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_GAME_MODE;
        }
    }

    public record UpdateListed(boolean listed) implements Action<UpdateListed> {
        public static final StreamCodec<ByteBuf, UpdateListed> CODEC = BasicStreamCodecs.BOOL.map(UpdateListed::new, UpdateListed::listed);

        @Override
        public StreamCodec<ByteBuf, UpdateListed> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_LISTED;
        }
    }

    public record UpdateLatency(int latency) implements Action<UpdateLatency> {
        public static final StreamCodec<ByteBuf, UpdateLatency> CODEC = ModernStreamCodecs.VAR_INT.map(UpdateLatency::new, UpdateLatency::latency);

        @Override
        public StreamCodec<ByteBuf, UpdateLatency> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_LATENCY;
        }
    }

    public record UpdateDisplayName(TextComponent displayName) implements Action<UpdateDisplayName> {
        @Override
        public StreamCodec<ByteBuf, UpdateDisplayName> codec() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_DISPLAY_NAME;
        }
    }

    public record UpdateListOrder(int listOrder) implements Action<UpdateListOrder>  {
        public static final StreamCodec<ByteBuf, UpdateListOrder> CODEC = ModernStreamCodecs.VAR_INT.map(UpdateListOrder::new, UpdateListOrder::listOrder);

        @Override
        public StreamCodec<ByteBuf, UpdateListOrder> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_LIST_ORDER;
        }
    }

    public record UpdateHat(boolean showHat) implements Action<UpdateHat> {
        public static final StreamCodec<ByteBuf, UpdateHat> CODEC = BasicStreamCodecs.BOOL.map(UpdateHat::new, UpdateHat::showHat);

        @Override
        public StreamCodec<ByteBuf, UpdateHat> codec() {
            return CODEC;
        }

        @Override
        public ActionType type() {
            return ActionType.UPDATE_HAT;
        }
    }
}
