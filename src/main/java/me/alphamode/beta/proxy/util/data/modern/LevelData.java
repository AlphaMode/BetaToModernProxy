package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public interface LevelData {
	record RespawnData(GlobalPos globalPos, float yaw, float pitch) {
		public static final StreamCodec<ByteStream, RespawnData> CODEC = StreamCodec.composite(
				GlobalPos.CODEC,
				RespawnData::globalPos,
				CommonStreamCodecs.FLOAT,
				RespawnData::yaw,
				CommonStreamCodecs.FLOAT,
				RespawnData::pitch,
				RespawnData::new
		);
	}
}
