package com.weaponmod.weaponmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WeaponModConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue PISTOL_RANGE;
    public static final ForgeConfigSpec.IntValue REVOLVER_RANGE;
    public static final ForgeConfigSpec.IntValue AK47_RANGE;
    public static final ForgeConfigSpec.IntValue MP5_RANGE;
    public static final ForgeConfigSpec.IntValue SNIPER_RANGE;
    public static final ForgeConfigSpec.IntValue SHOTGUN_RANGE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("WeaponMod Reichweiten-Konfiguration").push("ranges");

        PISTOL_RANGE = builder
                .comment("Pistole: maximale Reichweite in Bloecken")
                .defineInRange("pistol_range", 60, 1, 1000);

        REVOLVER_RANGE = builder
                .comment("Revolver: maximale Reichweite in Bloecken")
                .defineInRange("revolver_range", 80, 1, 1000);

        AK47_RANGE = builder
                .comment("AK47: maximale Reichweite in Bloecken")
                .defineInRange("ak47_range", 120, 1, 1000);

        MP5_RANGE = builder
                .comment("MP5: maximale Reichweite in Bloecken")
                .defineInRange("mp5_range", 80, 1, 1000);

        SNIPER_RANGE = builder
                .comment("Scharfschuetzengewehr: maximale Reichweite in Bloecken")
                .defineInRange("sniper_range", 400, 1, 2000);

        SHOTGUN_RANGE = builder
                .comment("Schrotflinte: maximale Reichweite in Bloecken")
                .defineInRange("shotgun_range", 30, 1, 1000);

        builder.pop();
        SPEC = builder.build();
    }
}
