package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public interface LevelData {
	record RespawnData(GlobalPos globalPos, float yaw, float pitch) {
		public static final StreamCodec<ByteBuf, RespawnData> CODEC = StreamCodec.composite(
				GlobalPos.CODEC,
				RespawnData::globalPos,
				BasicStreamCodecs.FLOAT,
				RespawnData::yaw,
				BasicStreamCodecs.FLOAT,
				RespawnData::pitch,
				RespawnData::new
		);
	}
}
