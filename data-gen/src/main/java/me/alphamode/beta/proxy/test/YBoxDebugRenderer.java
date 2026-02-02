package me.alphamode.beta.proxy.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public class YBoxDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
    @Override
    public void emitGizmos(double camX, double camY, double camZ, DebugValueAccess debugValues, Frustum frustum, float partialTicks) {
        GizmoStyle style = GizmoStyle.stroke(-1);
        Entity cameraEntity = Minecraft.getInstance().gameRenderer.getMainCamera().entity();
        AABB bb = cameraEntity.getBoundingBox();
        double x = cameraEntity.getX();
        double y = cameraEntity.getY();
        double z = cameraEntity.getZ();
        Gizmos.cuboid(new AABB(bb.minX, y, bb.minZ, bb.maxX, y, bb.maxZ), style);
    }
}
