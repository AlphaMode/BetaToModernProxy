package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddItemEntityPacket(int entityId, BetaItemStack item, Vec3i position, byte xa, byte ya, byte za) implements RecordPacket {
	public static final StreamCodec<ByteBuf, AddItemEntityPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            AddItemEntityPacket::entityId,
            BetaItemStack.CODEC,
            AddItemEntityPacket::item,
            Vec3i.CODEC,
            AddItemEntityPacket::position,
            ByteBufCodecs.BYTE,
            AddItemEntityPacket::xa,
            ByteBufCodecs.BYTE,
            AddItemEntityPacket::ya,
            ByteBufCodecs.BYTE,
            AddItemEntityPacket::za,
            AddItemEntityPacket::new
    );

    @Override
    public BetaPackets getType() {
        return BetaPackets.ADD_ITEM_ENTITY;
    }

    //    public AddItemEntityPacket(ItemEntity item) {
//        this.entity = item.id;
//        this.item = item.item.id;
//        this.count = item.item.count;
//        this.aux = item.item.getAuxValue();
//        this.x = Mth.floor(item.x * 32.0);
//        this.y = Mth.floor(item.y * 32.0);
//        this.z = Mth.floor(item.z * 32.0);
//        this.xa = (byte)(item.xd * 128.0);
//        this.ya = (byte)(item.yd * 128.0);
//        this.za = (byte)(item.zd * 128.0);
//    }
}
