package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public sealed interface MovePlayerPacket extends BetaPacket permits MovePlayerPacket.StatusOnly, MovePlayerPacket.Pos, MovePlayerPacket.PosRot, MovePlayerPacket.Rot {
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

	record StatusOnly(boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteStream, MovePlayerPacket> CODEC = StreamCodec.composite(
				CommonStreamCodecs.BOOL, MovePlayerPacket::onGround,
				StatusOnly::new
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
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_PLAYER;
		}
	}

	record Pos(double x, double y, double yView, double z, boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteStream, Pos> CODEC = StreamCodec.composite(
				CommonStreamCodecs.DOUBLE, Pos::x,
				CommonStreamCodecs.DOUBLE, Pos::y,
				CommonStreamCodecs.DOUBLE, Pos::yView,
				CommonStreamCodecs.DOUBLE, Pos::z,
				CommonStreamCodecs.BOOL, Pos::onGround,
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
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_PLAYER_POS;
		}
	}

	record PosRot(double x, double y, double yView, double z, float yRot, float xRot,
				  boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteStream, PosRot> CODEC = StreamCodec.composite(
				CommonStreamCodecs.DOUBLE, PosRot::x,
				CommonStreamCodecs.DOUBLE, PosRot::y,
				CommonStreamCodecs.DOUBLE, PosRot::yView,
				CommonStreamCodecs.DOUBLE, PosRot::z,
				CommonStreamCodecs.FLOAT, PosRot::yRot,
				CommonStreamCodecs.FLOAT, PosRot::xRot,
				CommonStreamCodecs.BOOL, PosRot::onGround,
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
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_PLAYER_POS_ROT;
		}
	}

	record Rot(float yRot, float xRot, boolean onGround) implements MovePlayerPacket {
		public static final StreamCodec<ByteStream, Rot> CODEC = StreamCodec.composite(
				CommonStreamCodecs.FLOAT, Rot::yRot,
				CommonStreamCodecs.FLOAT, Rot::xRot,
				CommonStreamCodecs.BOOL, Rot::onGround,
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
		public BetaPacketType getType() {
			return BetaPacketType.MOVE_PLAYER_ROT;
		}
	}
}
