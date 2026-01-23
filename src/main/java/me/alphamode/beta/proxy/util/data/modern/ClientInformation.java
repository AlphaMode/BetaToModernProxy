package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.enums.ChatVisiblity;
import me.alphamode.beta.proxy.util.data.modern.enums.HumanoidArm;
import me.alphamode.beta.proxy.util.data.modern.enums.ParticleStatus;

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
			ModernStreamCodecs.stringUtf8(MAX_LANGUAGE_LENGTH),
			ClientInformation::language,
			BasicStreamCodecs.BYTE,
			ClientInformation::viewDistance,
			ChatVisiblity.CODEC,
			ClientInformation::chatVisibility,
			BasicStreamCodecs.BOOL,
			ClientInformation::chatColors,
			BasicStreamCodecs.UNSIGNED_BYTE,
			ClientInformation::modelCustomisation,
			HumanoidArm.CODEC,
			ClientInformation::mainHand,
			BasicStreamCodecs.BOOL,
			ClientInformation::textFilteringEnabled,
			BasicStreamCodecs.BOOL,
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
