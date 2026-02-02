package me.alphamode.beta.proxy.mixin;

import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConfigurationPacketListenerImpl.class)
public abstract class MixinClientConfigurationPacketListenerImpl {
	@Inject(method = "handleRegistryData", at = @At("TAIL"))
	private void meowmeow(final ClientboundRegistryDataPacket packet, final CallbackInfo ci) {
		System.out.println("Registry: " + packet.registry());
		if (packet.registry().equals(Registries.DIMENSION_TYPE)) {
			packet.entries().forEach(entry -> {
				System.out.println("Entry: " + entry.id());
			});
		}
	}
}
