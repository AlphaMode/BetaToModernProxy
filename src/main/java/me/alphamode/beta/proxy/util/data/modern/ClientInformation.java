package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.enums.ChatVisibility;
import me.alphamode.beta.proxy.util.data.modern.enums.HumanoidArm;
import me.alphamode.beta.proxy.util.data.modern.enums.ParticleStatus;

public record ClientInformation(
		String language,
		byte viewDistance,
		ChatVisibility chatVisibility,
		boolean chatColors,
		short modelCustomisation,
		HumanoidArm mainHand,
		boolean textFilteringEnabled,
		boolean allowsListing,
		ParticleStatus particleStatus
) {
	public static final int MAX_LANGUAGE_LENGTH = 16;
	public static final StreamCodec<ByteStream, String> LANGUAGE_CODEC = ModernStreamCodecs.stringUtf8(MAX_LANGUAGE_LENGTH);
	public static final StreamCodec<ByteStream, ClientInformation> CODEC = StreamCodec.composite(
			LANGUAGE_CODEC,
			ClientInformation::language,
			CommonStreamCodecs.BYTE,
			ClientInformation::viewDistance,
			ChatVisibility.CODEC,
			ClientInformation::chatVisibility,
			CommonStreamCodecs.BOOL,
			ClientInformation::chatColors,
			CommonStreamCodecs.UNSIGNED_BYTE,
			ClientInformation::modelCustomisation,
			HumanoidArm.CODEC,
			ClientInformation::mainHand,
			CommonStreamCodecs.BOOL,
			ClientInformation::textFilteringEnabled,
			CommonStreamCodecs.BOOL,
			ClientInformation::allowsListing,
			ParticleStatus.CODEC,
			ClientInformation::particleStatus,
			ClientInformation::new
	);

	public ClientInformation() {
		this(
				"en_us",
				(byte) 2,
				ChatVisibility.FULL,
				true,
				(short) 0,
				HumanoidArm.RIGHT,
				false,
				false,
				ParticleStatus.ALL
		);
	}
}
