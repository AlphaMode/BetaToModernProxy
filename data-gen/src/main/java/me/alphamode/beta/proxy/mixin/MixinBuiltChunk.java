package me.alphamode.beta.proxy.mixin;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionRenderDispatcher.RenderSection.class)
public abstract class MixinBuiltChunk {
	@Inject(method = "hasAllNeighbors", at = @At("HEAD"), cancellable = true)
	private void alwaysRenderChunks(final CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}
