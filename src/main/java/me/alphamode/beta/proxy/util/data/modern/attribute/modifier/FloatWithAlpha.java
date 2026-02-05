package me.alphamode.beta.proxy.util.data.modern.attribute.modifier;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Either;

public record FloatWithAlpha(float value, float alpha) {
	public static final Codec<FloatWithAlpha> CODEC = Codec.either(Codec.FLOAT, MapCodecMerger.codec(
			Codec.FLOAT.mapCodec("data").required(), FloatWithAlpha::alpha,
			Codec.FLOAT.mapCodec("alpha").optional().defaulted(1.0F), FloatWithAlpha::alpha,
			FloatWithAlpha::new
	)).map(parameter -> parameter.alpha() == 1.0F ? Either.left(parameter.value()) : Either.right(parameter), either -> either.xmap(FloatWithAlpha::new, a -> a));

	public FloatWithAlpha(final float value) {
		this(value, 1.0F);
	}
}
