package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLevelChunkPacketData;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLightUpdatePacketData;

public class S2CLevelChunkWithLightPacket implements S2CPlayPacket {
	public static StreamCodec<ByteBuf, S2CLevelChunkWithLightPacket> CODEC = AbstractPacket.codec(S2CLevelChunkWithLightPacket::write, S2CLevelChunkWithLightPacket::new);

	private final int x;
	private final int z;
	private final ClientboundLevelChunkPacketData chunkData;
	private final ClientboundLightUpdatePacketData lightData;

	public S2CLevelChunkWithLightPacket(final int x, final int z, final ClientboundLevelChunkPacketData chunkData, final ClientboundLightUpdatePacketData lightData) {
		this.x = x;
		this.z = z;
		this.chunkData = chunkData;
		this.lightData = lightData;
	}

	public S2CLevelChunkWithLightPacket(final ByteBuf buf) {
		this.x = buf.readInt();
		this.z = buf.readInt();
		this.chunkData = new ClientboundLevelChunkPacketData(buf);
		this.lightData = new ClientboundLightUpdatePacketData(buf);
	}

	public void write(final ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.z);
		this.chunkData.write(buf);
		this.lightData.write(buf);
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	public ClientboundLevelChunkPacketData getChunkData() {
		return this.chunkData;
	}

	public ClientboundLightUpdatePacketData getLightData() {
		return this.lightData;
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.LEVEL_CHUNK_WITH_LIGHT;
	}
}
