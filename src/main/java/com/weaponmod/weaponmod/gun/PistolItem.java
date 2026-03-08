package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.config.WeaponModConfig;
import com.weaponmod.weaponmod.item.ModItems;

public class PistolItem extends GunItem {
    public PistolItem() {
        super(new GunProperties.Builder()
                .durability(400).damage(6).accuracy(0.95).cooldown(10)
                .maxAmmo(15).ammoType(ModItems.PISTOL_MAGAZINE.get()).usesMagazines(true).range(60).build());
    }

    @Override
    protected int getConfigRange() {
        return WeaponModConfig.PISTOL_RANGE.get();
    }
}