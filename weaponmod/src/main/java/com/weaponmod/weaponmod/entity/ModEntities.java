package com.weaponmod.weaponmod.entity;

import com.weaponmod.weaponmod.WeaponMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WeaponMod.MOD_ID);

    public static final RegistryObject<EntityType<ThrownGrenade>> THROWN_GRENADE = ENTITIES.register("thrown_grenade",
            () -> EntityType.Builder.<ThrownGrenade>of(ThrownGrenade::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(WeaponMod.MOD_ID, "thrown_grenade").toString()));

    public static final RegistryObject<EntityType<CustomBulletEntity>> CUSTOM_BULLET = ENTITIES.register("custom_bullet",
            () -> EntityType.Builder.<CustomBulletEntity>of(CustomBulletEntity::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(WeaponMod.MOD_ID, "custom_bullet").toString()));
}