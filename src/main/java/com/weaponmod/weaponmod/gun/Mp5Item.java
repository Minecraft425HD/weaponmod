package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.config.WeaponModConfig;
import com.weaponmod.weaponmod.item.ModItems;
import java.util.Set;

public class Mp5Item extends GunItem {
    public Mp5Item() {
        super(new GunProperties.Builder()
                .durability(500).damage(5).accuracy(0.8).cooldown(2)
                .maxAmmo(30).ammoType(ModItems.MP5_MAGAZINE.get()).usesMagazines(true).range(80).build());
    }

    @Override
    protected int getConfigRange() {
        return WeaponModConfig.MP5_RANGE.get();
    }

    @Override
    public Set<Integer> getCompatibleFireModes() {
        return Set.of(0, 1, 2); // Single, Burst, and Full-Auto
    }
}