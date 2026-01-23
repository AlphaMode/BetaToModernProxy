package me.alphamode.beta.proxy.mixin;

import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {PacketEncoder.class, PacketDecoder.class})
public class PacketEncoderAndDecoderMixin {
    @Redirect(method = {
            "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",
            "decode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Ljava/util/List;)V"
    }, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;isDebugEnabled()Z"))
    private boolean alwaysLogPacket(Logger instance) {
        return true;
    }

    @Redirect(method = {
            "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",
            "decode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Ljava/util/List;)V"
    }, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Lorg/slf4j/Marker;Ljava/lang/String;[Ljava/lang/Object;)V"))
    private void logPacket(Logger instance, Marker marker, String format, Object[] arguments) {
        instance.warn(marker, format, arguments);
    }
}
