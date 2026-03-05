package com.weaponmod.weaponmod;

import com.weaponmod.weaponmod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WeaponMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TABS.register("weapon_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.AK47.get()))
                    .title(Component.translatable("creativetab.weaponmod"))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.AK47.get());
                        output.accept(ModItems.PISTOL.get());
                        output.accept(ModItems.BASEBALL_BAT.get());
                        output.accept(ModItems.MACHETE.get());
                        output.accept(ModItems.SHOTGUN.get());
                        output.accept(ModItems.SNIPER.get());
                        output.accept(ModItems.REVOLVER.get());
                        output.accept(ModItems.MP5.get());
                        output.accept(ModItems.COMBAT_KNIFE.get());
                        output.accept(ModItems.FRAG_GRENADE.get());
                        output.accept(ModItems.SMOKE_GRENADE.get());
                        output.accept(ModItems.FLASH_GRENADE.get());
                        output.accept(ModItems.AK47_MAGAZINE.get());
                        output.accept(ModItems.PISTOL_MAGAZINE.get());
                        output.accept(ModItems.SHOTGUN_SHELLS.get());
                        output.accept(ModItems.SNIPER_MAGAZINE.get());
                        output.accept(ModItems.MP5_MAGAZINE.get());
                        output.accept(ModItems.AMMO_STANDARD.get());
                        output.accept(ModItems.AMMO_AP.get());
                        output.accept(ModItems.AMMO_TRACER.get());
                        output.accept(ModItems.AMMO_RUBBER.get());
                        output.accept(ModItems.SCOPE.get());
                        output.accept(ModItems.SILENCER.get());
                        output.accept(ModItems.LASER.get());
                        output.accept(ModItems.RIFLE_AMMO.get());
                        output.accept(ModItems.PISTOL_AMMO.get());
                    })
                    .build());
}