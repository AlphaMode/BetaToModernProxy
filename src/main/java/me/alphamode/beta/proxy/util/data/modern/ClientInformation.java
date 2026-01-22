package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ClientInformation(
		String language,
		int viewDistance,
		ChatVisiblity chatVisibility,
		boolean chatColors,
		int modelCustomisation,
		HumanoidArm mainHand,
		boolean textFilteringEnabled,
		boolean allowsListing,
		ParticleStatus particleStatus
) {
	public static final int MAX_LANGUAGE_LENGTH = 16;

	public static final StreamCodec<ByteBuf, ClientInformation> CODEC = StreamCodec.composite(
			ModernCodecs.stringUtf8(MAX_LANGUAGE_LENGTH),
			ClientInformation::language,
			BasicCodecs.INT,
			ClientInformation::viewDistance,
			ChatVisiblity.CODEC,
			ClientInformation::chatVisibility,
			BasicCodecs.BOOL,
			ClientInformation::chatColors,
			BasicCodecs.INT,
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
				2,
				ChatVisiblity.FULL,
				true,
				0,
				HumanoidArm.RIGHT,
				false,
				false,
				ParticleStatus.ALL
		);
	}
}
