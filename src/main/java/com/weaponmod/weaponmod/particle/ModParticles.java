package com.weaponmod.weaponmod.particle;

import com.weaponmod.weaponmod.WeaponMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WeaponMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> MUZZLE_FLASH = PARTICLES.register("muzzle_flash",
            () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLOOD = PARTICLES.register("blood",
            () -> new SimpleParticleType(true));
}