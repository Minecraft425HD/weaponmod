package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.config.WeaponModConfig;
import com.weaponmod.weaponmod.item.ModItems;
import java.util.Set;

public class Ak47Item extends GunItem {
    public Ak47Item() {
        super(new GunProperties.Builder()
                .durability(1200).damage(8).accuracy(0.85).cooldown(3)
                .maxAmmo(30).ammoType(ModItems.AK47_MAGAZINE.get()).usesMagazines(true).range(120).build());
    }

    @Override
    protected int getConfigRange() {
        return WeaponModConfig.AK47_RANGE.get();
    }

    @Override
    public Set<Integer> getCompatibleFireModes() {
        return Set.of(0, 2); // Single and Full-Auto
    }
}