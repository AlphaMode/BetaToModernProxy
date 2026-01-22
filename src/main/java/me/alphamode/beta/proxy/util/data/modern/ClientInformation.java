package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ClientInformation(
		String language,
		byte viewDistance,
		ChatVisiblity chatVisibility,
		boolean chatColors,
		short modelCustomisation,
		HumanoidArm mainHand,
		boolean textFilteringEnabled,
		boolean allowsListing,
		ParticleStatus particleStatus
) {
	public static final int MAX_LANGUAGE_LENGTH = 16;

	public static final StreamCodec<ByteBuf, ClientInformation> CODEC = StreamCodec.composite(
			ModernCodecs.stringUtf8(MAX_LANGUAGE_LENGTH),
			ClientInformation::language,
			BasicCodecs.BYTE,
			ClientInformation::viewDistance,
			ChatVisiblity.CODEC,
			ClientInformation::chatVisibility,
			BasicCodecs.BOOL,
			ClientInformation::chatColors,
			BasicCodecs.UNSIGNED_BYTE,
			ClientInformation::modelCustomisation,
			HumanoidArm.CODEC,
			ClientInformation::mainHand,
			BasicCodecs.BOOL,
			ClientInformation::textFilteringEnabled,
			BasicCodecs.BOOL,
			ClientInformation::allowsListing,
			ParticleStatus.CODEC,
			ClientInformation::particleStatus,
			ClientInformation::new
	);

	public ClientInformation() {
		this(
				"en_us",
				(byte) 2,
				ChatVisiblity.FULL,
				true,
				(short) 0,
				HumanoidArm.RIGHT,
				false,
				false,
				ParticleStatus.ALL
		);
	}
}
