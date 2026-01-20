package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class PlayerInputPacket implements Packet {
    private float xxa;
    private float zza;
    private boolean isJumping;
    private boolean isShiftKeyDown;
    private float xRot;
    private float yRot;

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.xxa = data.readFloat();
        this.zza = data.readFloat();
        this.xRot = data.readFloat();
        this.yRot = data.readFloat();
        this.isJumping = data.readBoolean();
        this.isShiftKeyDown = data.readBoolean();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeFloat(this.xxa);
        data.writeFloat(this.zza);
        data.writeFloat(this.xRot);
        data.writeFloat(this.yRot);
        data.writeBoolean(this.isJumping);
        data.writeBoolean(this.isShiftKeyDown);
    }
}
