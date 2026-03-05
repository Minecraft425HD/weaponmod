package com.weaponmod.weaponmod.handler;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.weaponmod.weaponmod.WeaponMod;
import com.weaponmod.weaponmod.attachment.Attachment;
import com.weaponmod.weaponmod.gun.GunItem;
import com.weaponmod.weaponmod.network.ModPackets;
import com.weaponmod.weaponmod.network.ReloadPacket;
import com.weaponmod.weaponmod.network.StartAutoFirePacket;
import com.weaponmod.weaponmod.network.StopAutoFirePacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = WeaponMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    private static boolean isScopeZoomed = false;
    private static boolean isMouseDown = false;

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof GunItem gun) {
            GuiGraphics graphics = event.getGuiGraphics();
            int w = mc.getWindow().getGuiScaledWidth();
            int h = mc.getWindow().getGuiScaledHeight();

            int ammo = gun.getCurrentAmmo(mainHand);
            int maxAmmo = gun.getProperties().getMaxAmmo();
            String ammoText = "🔫 " + ammo + "/" + maxAmmo;
            graphics.drawString(mc.font, ammoText, w - 100, h - 50, 0xFFFFFF);

            int mode = gun.getFireMode(mainHand);
            String modeText = switch (mode) {
                case 0 -> "Single";
                case 1 -> "Burst";
                case 2 -> "Auto";
                default -> "";
            };
            graphics.drawString(mc.font, "Mode: " + modeText, w - 100, h - 40, 0xFFFF55);
        }
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;
        ItemStack mainHand = player.getMainHandItem();
        if (!(mainHand.getItem() instanceof GunItem gun)) return;
        if (!gun.hasAttachment(mainHand) || gun.getAttachment(mainHand).getType() != Attachment.Type.LASER) return;

        Vec3 start = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(50));
        Camera camera = mc.gameRenderer.getMainCamera();
        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = camera.getPosition();
        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        VertexConsumer builder = mc.renderBuffers().bufferSource().getBuffer(RenderType.LINES);
        LevelRenderer.renderLineBox(poseStack, builder, end.x - 0.1, end.y - 0.1, end.z - 0.1, end.x + 0.1, end.y + 0.1, end.z + 0.1, 1, 0, 0, 1);
        LevelRenderer.renderLineBox(poseStack, builder, start.x - 0.05, start.y - 0.05, start.z - 0.05, start.x + 0.05, start.y + 0.05, start.z + 0.05, 0, 1, 0, 1);
        builder.vertex(poseStack.last().pose(), (float) start.x, (float) start.y, (float) start.z).color(255, 0, 0, 255).endVertex();
        builder.vertex(poseStack.last().pose(), (float) end.x, (float) end.y, (float) end.z).color(255, 0, 0, 255).endVertex();

        poseStack.popPose();
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        LocalPlayer player = mc.player;
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof GunItem gun) {
            if (gun.hasAttachment(mainHand) && gun.getAttachment(mainHand).getType() == Attachment.Type.SCOPE) {
                if (mc.options.keyShift.isDown()) {
                    if (!isScopeZoomed) {
                        mc.options.fov().set(30);
                        isScopeZoomed = true;
                    }
                } else {
                    if (isScopeZoomed) {
                        mc.options.fov().set(70);
                        isScopeZoomed = false;
                    }
                }
            } else if (isScopeZoomed) {
                mc.options.fov().set(70);
                isScopeZoomed = false;
            }

            long handle = mc.getWindow().getWindow();
            boolean isRightDown = InputConstants.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT) ||
                    GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
            if (isRightDown && gun.getFireMode(mainHand) == 2) {
                if (!isMouseDown) {
                    ModPackets.sendToServer(new StartAutoFirePacket(mc.player.getInventory().selected));
                    isMouseDown = true;
                }
            } else {
                if (isMouseDown) {
                    ModPackets.sendToServer(new StopAutoFirePacket());
                    isMouseDown = false;
                }
            }
        } else {
            if (isScopeZoomed) {
                mc.options.fov().set(70);
                isScopeZoomed = false;
            }
            if (isMouseDown) {
                ModPackets.sendToServer(new StopAutoFirePacket());
                isMouseDown = false;
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R) {
            ModPackets.sendToServer(new ReloadPacket());
        }
    }
}