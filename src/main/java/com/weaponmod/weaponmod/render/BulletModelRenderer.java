package com.weaponmod.weaponmod.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.weaponmod.weaponmod.entity.CustomBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

/**
 * Rendert Kugeln als kleines 3D-Projektil-Modell (zwei gekreuzte,
 * vorne zugespitzte Quads – sieht aus jedem Blickwinkel nach einem
 * Projektil aus).
 */
@OnlyIn(Dist.CLIENT)
public class BulletModelRenderer extends EntityRenderer<CustomBulletEntity> {

    // Huelsenbreite an der Basis
    private static final float BASE_HW = 0.036f;
    // Breite an der Spitze (zugespitzt)
    private static final float TIP_HW  = 0.008f;
    // Laenge des Projektils
    private static final float LENGTH  = 0.22f;

    // Messingfarbe (Huelse)
    private static final int BR = 200, BG = 160, BB = 50, BA = 255;
    // Dunkleres Kupfer/Blei an der Spitze
    private static final int TR = 120, TG = 85, TB = 25, TA = 255;

    public BulletModelRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(CustomBulletEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        Vec3 delta = entity.getDeltaMovement();
        if (delta.lengthSqr() < 1e-6) return;

        Vec3 dir = delta.normalize();

        poseStack.pushPose();

        // +Z der lokalen Achse auf die Flugrichtung ausrichten
        double yaw   =  Math.toDegrees(Math.atan2(-dir.x, dir.z));
        double pitch = -Math.toDegrees(Math.asin(Math.max(-1.0, Math.min(1.0, dir.y))));
        poseStack.mulPose(Axis.YP.rotationDegrees((float) yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) pitch));

        VertexConsumer buf = bufferSource.getBuffer(RenderType.lightning());
        Matrix4f mat = poseStack.last().pose();

        // Quad 1 – horizontal (X-Achse): Basis breit, Spitze schmal
        buf.vertex(mat,  BASE_HW, 0f, 0f).color(BR, BG, BB, BA).endVertex();
        buf.vertex(mat, -BASE_HW, 0f, 0f).color(BR, BG, BB, BA).endVertex();
        buf.vertex(mat, -TIP_HW,  0f, LENGTH).color(TR, TG, TB, TA).endVertex();
        buf.vertex(mat,  TIP_HW,  0f, LENGTH).color(TR, TG, TB, TA).endVertex();

        // Quad 2 – vertikal (Y-Achse): gleiche Form, 90° gedreht
        buf.vertex(mat, 0f,  BASE_HW, 0f).color(BR, BG, BB, BA).endVertex();
        buf.vertex(mat, 0f, -BASE_HW, 0f).color(BR, BG, BB, BA).endVertex();
        buf.vertex(mat, 0f, -TIP_HW,  LENGTH).color(TR, TG, TB, TA).endVertex();
        buf.vertex(mat, 0f,  TIP_HW,  LENGTH).color(TR, TG, TB, TA).endVertex();

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CustomBulletEntity entity) {
        return new ResourceLocation("textures/misc/unknown_server.png");
    }
}
