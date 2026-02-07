package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Direction;
import me.alphamode.beta.proxy.util.data.modern.BlockPos;

public record C2SPlayerActionPacket(Action action, BlockPos blockPos, Direction direction,
									int sequence) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SPlayerActionPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.javaEnum(Action.class),
			C2SPlayerActionPacket::action,
			BlockPos.CODEC,
			C2SPlayerActionPacket::blockPos,
			Direction.CODEC_3D,
			C2SPlayerActionPacket::direction,
			ModernStreamCodecs.VAR_INT,
			C2SPlayerActionPacket::sequence,
			C2SPlayerActionPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PLAYER_ACTION;
	}

	public enum Action {
		START_DESTROY_BLOCK,
		ABORT_DESTROY_BLOCK,
		STOP_DESTROY_BLOCK,
		DROP_ALL_ITEMS,
		DROP_ITEM,
		RELEASE_USE_ITEM,
		SWAP_ITEM_WITH_OFFHAND,
		STAB
	}
}
