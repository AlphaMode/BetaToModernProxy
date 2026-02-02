package me.alphamode.beta.proxy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
	@Unique
	private static final ResourceKey<Level> beta$SKY = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath("minecraftbeta", "sky"));

	@Inject(method = "handleSystemChat", at = @At("TAIL"))
	private void meow2(final ClientboundSystemChatPacket packet, final CallbackInfo ci) {
		if (packet.content().tryCollapseToString().contains("em")) {
			final Minecraft minecraft = Minecraft.getInstance();
			final RegistryAccess registryAccess = minecraft.level.registryAccess();

			final Registry<DimensionType> registry = registryAccess.lookup(Registries.DIMENSION_TYPE).orElseThrow();
			for (final var entry : registry) {
				System.out.println("Entry Name: " + registry.getKey(entry));
				System.out.println("Entry Network ID: " + registry.getId(entry));
			}
		}
	}
}
