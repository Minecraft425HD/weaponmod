package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.item.ModItems;

public class RevolverItem extends GunItem {
    public RevolverItem() {
        super(new GunProperties.Builder()
                .durability(300).damage(10).accuracy(0.9).cooldown(15)
                .maxAmmo(6).ammoType(ModItems.PISTOL_MAGAZINE.get()).usesMagazines(true).build());
    }
}