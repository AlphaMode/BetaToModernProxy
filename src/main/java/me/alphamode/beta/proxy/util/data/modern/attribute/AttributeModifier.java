package me.alphamode.beta.proxy.util.data.modern.attribute;

import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.data.modern.attribute.modifier.BlendToGray;
import me.alphamode.beta.proxy.util.data.modern.attribute.modifier.FloatWithAlpha;
import net.lenni0451.mcstructs.converter.codec.Codec;

import java.util.Map;

public interface AttributeModifier<Subject, Argument> {
	Map<OperationId, Codec<?>> BOOLEAN_LIBRARY = Map.of(
			AttributeModifier.OperationId.AND,
			Codec.BOOLEAN,
			AttributeModifier.OperationId.NAND,
			Codec.BOOLEAN,
			AttributeModifier.OperationId.OR,
			Codec.BOOLEAN,
			AttributeModifier.OperationId.NOR,
			Codec.BOOLEAN,
			AttributeModifier.OperationId.XOR,
			Codec.BOOLEAN,
			AttributeModifier.OperationId.XNOR,
			Codec.BOOLEAN
	);
	Map<AttributeModifier.OperationId, Codec<?>> FLOAT_LIBRARY = Map.of(
			AttributeModifier.OperationId.ALPHA_BLEND,
			FloatWithAlpha.CODEC,
			AttributeModifier.OperationId.ADD,
			Codec.FLOAT,
			AttributeModifier.OperationId.SUBTRACT,
			Codec.FLOAT,
			AttributeModifier.OperationId.MULTIPLY,
			Codec.FLOAT,
			AttributeModifier.OperationId.MINIMUM,
			Codec.FLOAT,
			AttributeModifier.OperationId.MAXIMUM,
			Codec.FLOAT
	);
	Map<AttributeModifier.OperationId, Codec<?>> RGB_COLOR_LIBRARY = Map.of(
			AttributeModifier.OperationId.ALPHA_BLEND,
			ModernCodecs.STRING_ARGB_COLOR,
			AttributeModifier.OperationId.ADD,
			ModernCodecs.STRING_RGB_COLOR,
			AttributeModifier.OperationId.SUBTRACT,
			ModernCodecs.STRING_RGB_COLOR,
			AttributeModifier.OperationId.MULTIPLY,
			ModernCodecs.STRING_RGB_COLOR,
			AttributeModifier.OperationId.BLEND_TO_GRAY,
			BlendToGray.CODEC
	);
	Map<AttributeModifier.OperationId, Codec<?>> ARGB_COLOR_LIBRARY = Map.of(
			AttributeModifier.OperationId.ALPHA_BLEND,
			ModernCodecs.STRING_ARGB_COLOR,
			AttributeModifier.OperationId.ADD,
			ModernCodecs.STRING_RGB_COLOR,
			AttributeModifier.OperationId.SUBTRACT,
			ModernCodecs.STRING_RGB_COLOR,
			AttributeModifier.OperationId.MULTIPLY,
			ModernCodecs.STRING_ARGB_COLOR,
			AttributeModifier.OperationId.BLEND_TO_GRAY,
			BlendToGray.CODEC
	);

	enum OperationId implements StringRepresentable {
		OVERRIDE("override"),
		ALPHA_BLEND("alpha_blend"),
		ADD("add"),
		SUBTRACT("subtract"),
		MULTIPLY("multiply"),
		BLEND_TO_GRAY("blend_to_gray"),
		MINIMUM("minimum"),
		MAXIMUM("maximum"),
		AND("and"),
		NAND("nand"),
		OR("or"),
		NOR("nor"),
		XOR("xor"),
		XNOR("xnor");

		public static final Codec<OperationId> CODEC = StringRepresentable.fromEnum(AttributeModifier.OperationId::values);
		private final String name;

		OperationId(final String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
