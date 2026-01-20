package me.alphamode.beta.proxy.packet.beta;

import net.raphimc.netminecraft.constants.ConnectionState;
import net.raphimc.netminecraft.constants.PacketDirection;

public enum BetaPackets {
    KEEPALIVE(0, true, true),
    LOGIN(1, true, true),
    PRE_LOGIN(2, true, true),
    CHAT(3, true, true),
    SET_TIME(4, true, false),
    SET_EQUIPPED_ITEM(5, true, false),
    SET_SPAWN_POSITION(6, true, false),
    INTERACT(7, false, true),
    SETHEALTH(8, true, false),
    PLAYER_CHANGE_DIMENSION(9, true, true),
    MOVE_PLAYER(10, true, true),
    MOVE_PLAYER_POS(11, true, true),
    MOVE_PLAYER_ROT(12, true, true),
    MOVE_PLAYER_POS_ROT(13, true, true),
    PLAYER_ACTION(14, false, true),
    USE_ITEM(15, false, true),
    SET_CARRIED_ITEM(16, false, true),
    INTERACTION(17, true, false),
    ANIMATE(18, true, true),
    PLAYER_COMMAND(19, false, true),
    ADD_PLAYER(20, true, false),
    ADD_ITEM_ENTITY(21, true, false),
    TAKE_ITEM_ENTITY(22, true, false),
    ADD_ENTITY(23, true, false),
    ADD_MOB(24, true, false),
    ADD_PAINTING(25, true, false),
    PLAYER_INPUT(27, false, true),
    SET_ENTITY_MOTION(28, true, false),
    REMOVE_ENTITY(29, true, false),
    MOVE_ENTITY(30, true, false),
    MOVE_ENTITY_POS(31, true, false),
    MOVE_ENTITY_ROT(32, true, false),
    MOVE_ENTITY_POS_ROT(33, true, false),
    TELEPORT_ENTITY(34, true, false),
    ENTITY_EVENT(38, true, false),
    SET_RIDING(39, true, false),
    SET_ENTITY_DATA(40, true, false),
    CHUNK_VISIBILITY(50, true, false),
    BLOCK_REGION_UPDATE(51, true, false),
    CHUNK_TILES_UPDATE(52, true, false),
    TILE_UPDATE(53, true, false),
    TILE_EVENT(54, true, false),
    EXPLODE(60, true, false),
    LEVEL_EVENT(61, true, false),
    GAME_EVENT(70, true, false),
    ADD_GLOBAL_ENTITY(71, true, false),
    CONTAINER_OPEN(100, true, false),
    CONTAINER_CLOSE(101, true, true),
    CONTAINER_CLICK(102, false, true),
    CONTAINER_SET_SLOT(103, true, false),
    CONTAINER_SET_CONTENT(104, true, false),
    CONTAINER_SET_DATA(105, true, false),
    CONTAINER_ACK(106, true, true),
    SIGN_UPDATE(130, true, true),
    MAP_ITEM_DATA(131, true, false),
    UPDATE_STAT(200, true, false),
    DISCONNECT(255, true, true);

    private int packetId;

    BetaPackets(int id, boolean clientbound, boolean serverbound) {
        this.packetId = id;
    }

    public int getId() {
        return packetId;
    }

    public static BetaPackets getPacket(final int packetId) {
        for (BetaPackets packet : BetaPackets.values()) {
//            if (packet.getState() != state) continue;
//            if (packet.getDirection() != direction) continue;
            if (packet.getId() == packetId) return packet;
        }
        return null;
    }
}
