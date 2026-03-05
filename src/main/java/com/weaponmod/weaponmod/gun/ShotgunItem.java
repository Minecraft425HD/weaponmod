package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShotgunItem extends GunItem {
    public ShotgunItem() {
        super(new GunProperties.Builder()
                .durability(600).damage(4).accuracy(0.6).cooldown(25)
                .maxAmmo(8).ammoType(ModItems.SHOTGUN_SHELLS.get()).usesMagazines(true).build());
    }

    @Override
    protected void shootProjectile(Level level, Player player, ItemStack gunStack) {
        for (int i = 0; i < 5; i++) {
            super.shootProjectile(level, player, gunStack);
        }
    }
}