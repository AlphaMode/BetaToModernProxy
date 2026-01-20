package me.alphamode.beta.proxy.networking.packet.beta.packets;

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
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.xxa = buf.readFloat();
		this.zza = buf.readFloat();
		this.xRot = buf.readFloat();
		this.yRot = buf.readFloat();
		this.isJumping = buf.readBoolean();
		this.isShiftKeyDown = buf.readBoolean();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeFloat(this.xxa);
		buf.writeFloat(this.zza);
		buf.writeFloat(this.xRot);
		buf.writeFloat(this.yRot);
		buf.writeBoolean(this.isJumping);
		buf.writeBoolean(this.isShiftKeyDown);
	}
}
