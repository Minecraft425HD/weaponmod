package com.weaponmod.weaponmod.handler;

import com.weaponmod.weaponmod.gun.GunItem;
import com.weaponmod.weaponmod.item.ModItems;
import com.weaponmod.weaponmod.melee.MeleeWeaponItem;
import com.weaponmod.weaponmod.skill.SkillSystem;
import com.weaponmod.weaponmod.util.ModNBT;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ServerEventHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            Player player = event.player;
            if (player.getPersistentData().getBoolean(ModNBT.AUTO_FIRE_ACTIVE)) {
                int slot = player.getPersistentData().getInt(ModNBT.AUTO_FIRE_GUN_SLOT);
                ItemStack gunStack = slot == -1 ? player.getOffhandItem() : player.getInventory().getItem(slot);
                if (gunStack.getItem() instanceof GunItem gun && gun.getFireMode(gunStack) == 2) {
                    if (gun.canShoot(gunStack, player)) {
                        int ammo = gun.getCurrentAmmo(gunStack);
                        if (ammo > 0 || player.isCreative()) {
                            if (!player.isCreative()) {
                                gun.setCurrentAmmo(gunStack, ammo - 1);
                            }
                            gun.shootProjectile(player.level(), player, gunStack);
                            gun.addShotHistory(gunStack);
                        } else {
                            player.getPersistentData().remove(ModNBT.AUTO_FIRE_ACTIVE);
                        }
                    } else {
                        player.getPersistentData().remove(ModNBT.AUTO_FIRE_ACTIVE);
                    }
                } else {
                    player.getPersistentData().remove(ModNBT.AUTO_FIRE_ACTIVE);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof Player player) {
            if (!event.getEntity().isAlive()) {
                ItemStack weapon = player.getMainHandItem();
                if (weapon.getItem() instanceof GunItem) {
                    if (weapon.getItem() == ModItems.PISTOL.get() || weapon.getItem() == ModItems.REVOLVER.get()) {
                        SkillSystem.addXP(player, SkillSystem.SkillType.PISTOL, 10);
                    } else {
                        SkillSystem.addXP(player, SkillSystem.SkillType.RIFLE, 10);
                    }
                } else if (weapon.getItem() instanceof MeleeWeaponItem) {
                    SkillSystem.addXP(player, SkillSystem.SkillType.MELEE, 10);
                }
            }
        }
    }
}