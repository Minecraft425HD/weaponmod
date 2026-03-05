package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.item.ModItems;

public class Mp5Item extends GunItem {
    public Mp5Item() {
        super(new GunProperties.Builder()
                .durability(500).damage(5).accuracy(0.8).cooldown(2)
                .maxAmmo(30).ammoType(ModItems.MP5_MAGAZINE.get()).usesMagazines(true).build());
    }
}