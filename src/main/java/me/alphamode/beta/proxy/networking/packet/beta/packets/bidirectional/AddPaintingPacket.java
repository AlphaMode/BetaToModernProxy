package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPaintingPacket(int id, Motive motive, Vec3i position, int direction) implements BetaPacket {
	public static final StreamCodec<ByteBuf, AddPaintingPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddPaintingPacket::id,
			Motive.STRING_CODEC,
			AddPaintingPacket::motive,
			Vec3i.CODEC,
			AddPaintingPacket::position,
			BasicStreamCodecs.INT,
			AddPaintingPacket::direction,
			AddPaintingPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PAINTING;
	}

	public enum Motive {
		KEBAB("Kebab"),
		AZTEC("Aztec"),
		ALBAN("Alban"),
		AZTEC2("Aztec2"),
		BOMB("Bomb"),
		PLANT("Plant"),
		WASTELAND("Wasteland"),
		POOL("Pool"),
		COURBET("Courbet"),
		SEA("Sea"),
		SUNSET("Sunset"),
		CREEBET("Creebet"),
		WANDERER("Wanderer"),
		GRAHAM("Graham"),
		MATCH("Match"),
		BUST("Bust"),
		STAGE("Stage"),
		VOID("Void"),
		SKULL_AND_ROSES("SkullAndRoses"),
		FIGHTERS("Fighters"),
		POINTER("Pointer"),
		PIGSCENE("Pigscene"),
		BURNING_SKULL("BurningSkull"),
		SKELETON("Skeleton"),
		DONKEY_KONG("DonkeyKong");

		public static final int MAX_PAINTING_MOTIVE_NAME_LENGTH = 13;
		public static final StreamCodec<ByteBuf, Motive> STRING_CODEC = BetaStreamCodecs.stringUtf8(MAX_PAINTING_MOTIVE_NAME_LENGTH).map(Motive::byName, Motive::getName);

		private final String name;

		Motive(final String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static Motive byName(final String name) {
			for (final Motive motive : values()) {
				if (name.equals(motive.name)) {
					return motive;
				}
			}

			throw new IndexOutOfBoundsException();
		}
	}
}
