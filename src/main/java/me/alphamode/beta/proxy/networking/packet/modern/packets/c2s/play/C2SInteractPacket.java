package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;

public record C2SInteractPacket(int entityId, Action<?> action,
								boolean usingSecondaryAction) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SInteractPacket> CODEC = AbstractPacket.codec(C2SInteractPacket::write, C2SInteractPacket::new);

	private C2SInteractPacket(final ByteBuf buf) {
		final int entityId = ModernStreamCodecs.VAR_INT.decode(buf);
		final ActionType type = ActionType.CODEC.decode(buf);
		final Action<?> action = type.codec().decode(buf);
		final boolean usingSecondaryAction = buf.readBoolean();
		this(entityId, action, usingSecondaryAction);
	}

	private void write(final ByteBuf buf) {
		ModernStreamCodecs.VAR_INT.encode(buf, this.entityId);
		ActionType.CODEC.encode(buf, this.action.type());
		((StreamCodec<ByteBuf, Action<?>>) this.action.codec()).encode(buf, this.action);
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.INTERACT;
	}

	public enum ActionType {
		INTERACT(InteractionAction.CODEC),
		ATTACK(AttackAction.CODEC),
		INTERACT_AT(InteractionAtLocationAction.CODEC);

		public static final StreamCodec<ByteBuf, ActionType> CODEC = ModernStreamCodecs.javaEnum(ActionType.class);
		private final StreamCodec<ByteBuf, ? extends Action<?>> codec;

		ActionType(final StreamCodec<ByteBuf, ? extends Action<?>> codec) {
			this.codec = codec;
		}

		public StreamCodec<ByteBuf, ? extends Action<?>> codec() {
			return this.codec;
		}
	}

	private sealed interface Action<T> permits InteractionAction, AttackAction, InteractionAtLocationAction {
		StreamCodec<ByteBuf, T> codec();

		ActionType type();
	}

	public record InteractionAction(InteractionHand hand) implements Action<InteractionAction> {
		public static final StreamCodec<ByteBuf, InteractionAction> CODEC = InteractionHand.CODEC.map(InteractionAction::new, InteractionAction::hand);

		@Override
		public StreamCodec<ByteBuf, InteractionAction> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.INTERACT;
		}
	}

	public record AttackAction() implements Action<AttackAction> {
		public static final AttackAction INSTANCE = new AttackAction();
		public static final StreamCodec<ByteBuf, AttackAction> CODEC = StreamCodec.unit(INSTANCE);

		@Override
		public StreamCodec<ByteBuf, AttackAction> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.ATTACK;
		}
	}

	public record InteractionAtLocationAction(InteractionHand hand,
											  Vec3d location) implements Action<InteractionAtLocationAction> {
		private static final StreamCodec<ByteBuf, Vec3d> FLOAT_VEC3D = new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Vec3d value) {
				buf.writeFloat((float) value.x());
				buf.writeFloat((float) value.y());
				buf.writeFloat((float) value.z());
			}

			@Override
			public Vec3d decode(final ByteBuf buf) {
				return new Vec3d(buf.readFloat(), buf.readFloat(), buf.readFloat());
			}
		};

		public static final StreamCodec<ByteBuf, InteractionAtLocationAction> CODEC = StreamCodec.composite(
				InteractionHand.CODEC,
				InteractionAtLocationAction::hand,
				FLOAT_VEC3D,
				InteractionAtLocationAction::location,
				InteractionAtLocationAction::new
		);

		@Override
		public StreamCodec<ByteBuf, InteractionAtLocationAction> codec() {
			return CODEC;
		}

		@Override
		public ActionType type() {
			return ActionType.INTERACT_AT;
		}
	}
}
