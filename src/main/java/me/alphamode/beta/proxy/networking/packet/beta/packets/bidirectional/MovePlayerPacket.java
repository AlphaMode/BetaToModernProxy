package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public sealed interface MovePlayerPacket extends BetaPacket permits MovePlayerPacket.Status, MovePlayerPacket.Pos, MovePlayerPacket.PosRot, MovePlayerPacket.Rot {
	default double x() {
		return 0;
	}

	default double y() {
		return 0;
	}

	default double yView() {
		return 0;
	}

	default double z() {
		return 0;
	}

	default float yRot() {
		return 0;
	}

	default float xRot() {
		return 0;
	}

	boolean onGround();

	boolean hasPos();

	boolean hasRot();

	record Status(boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteBuf, MovePlayerPacket> CODEC = StreamCodec.composite(
				BasicStreamCodecs.BOOL, MovePlayerPacket::onGround,
				Status::new
		);

		@Override
		public boolean hasPos() {
			return false;
		}

		@Override
		public boolean hasRot() {
			return false;
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER;
		}
	}

	record Pos(double x, double y, double yView, double z, boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteBuf, Pos> CODEC = StreamCodec.composite(
				BasicStreamCodecs.DOUBLE, Pos::x,
				BasicStreamCodecs.DOUBLE, Pos::y,
				BasicStreamCodecs.DOUBLE, Pos::yView,
				BasicStreamCodecs.DOUBLE, Pos::z,
				BasicStreamCodecs.BOOL, Pos::onGround,
				Pos::new
		);

		@Override
		public boolean hasPos() {
			return true;
		}

		@Override
		public boolean hasRot() {
			return false;
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_POS;
		}
	}

	record PosRot(double x, double y, double yView, double z, float yRot, float xRot,
				  boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteBuf, PosRot> CODEC = StreamCodec.composite(
				BasicStreamCodecs.DOUBLE, PosRot::x,
				BasicStreamCodecs.DOUBLE, PosRot::y,
				BasicStreamCodecs.DOUBLE, PosRot::yView,
				BasicStreamCodecs.DOUBLE, PosRot::z,
				BasicStreamCodecs.FLOAT, PosRot::yRot,
				BasicStreamCodecs.FLOAT, PosRot::xRot,
				BasicStreamCodecs.BOOL, PosRot::onGround,
				PosRot::new
		);

		@Override
		public boolean hasPos() {
			return true;
		}

		@Override
		public boolean hasRot() {
			return true;
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_POS_ROT;
		}
	}

	record Rot(float yRot, float xRot, boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteBuf, Rot> CODEC = StreamCodec.composite(
				BasicStreamCodecs.FLOAT, Rot::yRot,
				BasicStreamCodecs.FLOAT, Rot::xRot,
				BasicStreamCodecs.BOOL, Rot::onGround,
				Rot::new
		);

		@Override
		public boolean hasPos() {
			return false;
		}

		@Override
		public boolean hasRot() {
			return true;
		}

		@Override
		public BetaPackets getType() {
			return BetaPackets.MOVE_PLAYER_ROT;
		}
	}
}
