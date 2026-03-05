package com.weaponmod.weaponmod.sound;

import com.weaponmod.weaponmod.WeaponMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WeaponMod.MOD_ID);

    public static final RegistryObject<SoundEvent> GUN_SHOT = register("gun_shot");
    public static final RegistryObject<SoundEvent> EMPTY_CLICK = register("empty_click");
    public static final RegistryObject<SoundEvent> CLICK = register("click");
    public static final RegistryObject<SoundEvent> GRENADE_EXPLODE = register("grenade_explode");
    public static final RegistryObject<SoundEvent> RELOAD = register("reload");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WeaponMod.MOD_ID, name)));
    }
}