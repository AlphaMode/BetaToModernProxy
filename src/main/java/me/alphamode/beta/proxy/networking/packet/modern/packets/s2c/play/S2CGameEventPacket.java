package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class S2CGameEventPacket implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CGameEventPacket> CODEC = AbstractPacket.codec(S2CGameEventPacket::write, S2CGameEventPacket::new);
	public static final S2CGameEventPacket.Type NO_RESPAWN_BLOCK_AVAILABLE = new S2CGameEventPacket.Type(0);
	public static final S2CGameEventPacket.Type START_RAINING = new S2CGameEventPacket.Type(1);
	public static final S2CGameEventPacket.Type STOP_RAINING = new S2CGameEventPacket.Type(2);
	public static final S2CGameEventPacket.Type CHANGE_GAME_MODE = new S2CGameEventPacket.Type(3);
	public static final S2CGameEventPacket.Type WIN_GAME = new S2CGameEventPacket.Type(4);
	public static final S2CGameEventPacket.Type DEMO_EVENT = new S2CGameEventPacket.Type(5);
	public static final S2CGameEventPacket.Type PLAY_ARROW_HIT_SOUND = new S2CGameEventPacket.Type(6);
	public static final S2CGameEventPacket.Type RAIN_LEVEL_CHANGE = new S2CGameEventPacket.Type(7);
	public static final S2CGameEventPacket.Type THUNDER_LEVEL_CHANGE = new S2CGameEventPacket.Type(8);
	public static final S2CGameEventPacket.Type PUFFER_FISH_STING = new S2CGameEventPacket.Type(9);
	public static final S2CGameEventPacket.Type GUARDIAN_ELDER_EFFECT = new S2CGameEventPacket.Type(10);
	public static final S2CGameEventPacket.Type IMMEDIATE_RESPAWN = new S2CGameEventPacket.Type(11);
	public static final S2CGameEventPacket.Type LIMITED_CRAFTING = new S2CGameEventPacket.Type(12);
	public static final S2CGameEventPacket.Type LEVEL_CHUNKS_LOAD_START = new S2CGameEventPacket.Type(13);

	private final S2CGameEventPacket.Type event;
	private final float value;

	public S2CGameEventPacket(final S2CGameEventPacket.Type event, final float value) {
		this.event = event;
		this.value = value;
	}

	private S2CGameEventPacket(final ByteBuf input) {
		this.event = Type.TYPES.get(input.readUnsignedByte());
		this.value = input.readFloat();
	}

	private void write(final ByteBuf output) {
		output.writeByte(this.event.id);
		output.writeFloat(this.value);
	}

	public float getValue() {
		return this.value;
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.GAME_EVENT;
	}

	public static class Type {
		private static final Int2ObjectMap<Type> TYPES = new Int2ObjectOpenHashMap<>();
		private final int id;

		public Type(int id) {
			this.id = id;
			TYPES.put(id, this);
		}
	}
}
