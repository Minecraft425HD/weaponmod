package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.item.ModItems;

public class SniperItem extends GunItem {
    public SniperItem() {
        super(new GunProperties.Builder()
                .durability(800).damage(20).accuracy(0.98).cooldown(40)
                .maxAmmo(5).ammoType(ModItems.SNIPER_MAGAZINE.get()).usesMagazines(true).build());
    }
}