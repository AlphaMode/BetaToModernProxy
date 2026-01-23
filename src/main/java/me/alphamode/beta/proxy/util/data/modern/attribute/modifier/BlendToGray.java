package me.alphamode.beta.proxy.util.data.modern.attribute.modifier;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;

public record BlendToGray(float brightness, float factor) {
    public static final Codec<BlendToGray> CODEC = MapCodecMerger.codec(
            Codec.rangedFloat(0.0F, 1.0F).mapCodec("brightness").required(), BlendToGray::brightness,
            Codec.rangedFloat(0.0F, 1.0F).mapCodec("factor").required(), BlendToGray::factor,
            BlendToGray::new
    );
}
