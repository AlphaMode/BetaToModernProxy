package me.alphamode.beta.proxy.networking.packet.beta;

import me.alphamode.beta.proxy.networking.packet.Packets;

public enum BetaPackets implements Packets {
	KEEP_ALIVE(0),
	LOGIN(1),
	HANDSHAKE(2),
	CHAT(3),
	SET_TIME(4),
	SET_EQUIPPED_ITEM(5),
	SET_SPAWN_POSITION(6),
	INTERACT(7),
	SET_HEALTH(8),
	PLAYER_CHANGE_DIMENSION(9),
	MOVE_PLAYER(10),
	MOVE_PLAYER_POS(11),
	MOVE_PLAYER_ROT(12),
	MOVE_PLAYER_POS_ROT(13),
	PLAYER_ACTION(14),
	USE_ITEM(15),
	SET_CARRIED_ITEM(16),
	INTERACTION(17),
	ANIMATE(18),
	PLAYER_COMMAND(19),
	ADD_PLAYER(20),
	ADD_ITEM_ENTITY(21),
	TAKE_ITEM_ENTITY(22),
	ADD_ENTITY(23),
	ADD_MOB(24),
	ADD_PAINTING(25),
	PLAYER_INPUT(27),
	SET_ENTITY_MOTION(28),
	REMOVE_ENTITY(29),
	MOVE_ENTITY(30),
	MOVE_ENTITY_POS(31),
	MOVE_ENTITY_ROT(32),
	MOVE_ENTITY_POS_ROT(33),
	TELEPORT_ENTITY(34),
	ENTITY_EVENT(38),
	SET_RIDING(39),
	SET_ENTITY_DATA(40),
	CHUNK_VISIBILITY(50),
	BLOCK_REGION_UPDATE(51),
	CHUNK_TILES_UPDATE(52),
	TILE_UPDATE(53),
	TILE_EVENT(54),
	EXPLODE(60),
	LEVEL_EVENT(61),
	GAME_EVENT(70),
	ADD_GLOBAL_ENTITY(71),
	CONTAINER_OPEN(100),
	CONTAINER_CLOSE(101),
	CONTAINER_CLICK(102),
	CONTAINER_SET_SLOT(103),
	CONTAINER_SET_CONTENT(104),
	CONTAINER_SET_DATA(105),
	CONTAINER_ACK(106),
	SIGN_UPDATE(130),
	MAP_ITEM_DATA(131),
	UPDATE_STAT(200),
	DISCONNECT(255);

	private final int id;

	BetaPackets(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static BetaPackets getPacket(final int id) {
		for (final BetaPackets packet : BetaPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
