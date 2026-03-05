package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.item.ModItems;

public class Ak47Item extends GunItem {
    public Ak47Item() {
        super(new GunProperties.Builder()
                .durability(1200).damage(8).accuracy(0.85).cooldown(3)
                .maxAmmo(30).ammoType(ModItems.AK47_MAGAZINE.get()).usesMagazines(true).build());
    }
}