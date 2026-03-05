package com.weaponmod.weaponmod;

import com.weaponmod.weaponmod.attachment.ModAttachments;
import com.weaponmod.weaponmod.entity.ModEntities;
import com.weaponmod.weaponmod.handler.ClientEventHandler;
import com.weaponmod.weaponmod.handler.ServerEventHandler;
import com.weaponmod.weaponmod.item.ModItems;
import com.weaponmod.weaponmod.network.ModPackets;
import com.weaponmod.weaponmod.particle.ModParticles;
import com.weaponmod.weaponmod.skill.SkillSystem;
import com.weaponmod.weaponmod.sound.ModSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WeaponMod.MOD_ID)
public class WeaponMod {
    public static final String MOD_ID = "weaponmod";
    public static final Logger LOGGER = LogManager.getLogger();

    public WeaponMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(SkillSystem.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModPackets::register);
        LOGGER.info("WeaponMod Netzwerk registriert");
    }
}