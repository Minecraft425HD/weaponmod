package com.weaponmod.weaponmod;

import com.weaponmod.weaponmod.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.MUZZLE_FLASH.get(), MuzzleFlashParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLOOD.get(), BloodParticle.Provider::new);
    }

    // Einfacher Muzzle-Flash-Partikel (nutzt die registrierten Sprites)
    static class MuzzleFlashParticle extends TextureSheetParticle {
        MuzzleFlashParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
            super(level, x, y, z);
            this.pickSprite(sprites);
            this.lifetime = 3;
            this.scale(0.4f);
            this.rCol = 1.0f;
            this.gCol = 0.8f;
            this.bCol = 0.2f;
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        public static class Provider implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet sprites;
            public Provider(SpriteSet sprites) { this.sprites = sprites; }

            @Override
            public net.minecraft.client.particle.Particle createParticle(
                    SimpleParticleType type, ClientLevel level,
                    double x, double y, double z,
                    double xd, double yd, double zd) {
                return new MuzzleFlashParticle(level, x, y, z, sprites);
            }
        }
    }

    // Einfacher Blut-Partikel
    static class BloodParticle extends TextureSheetParticle {
        BloodParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
            super(level, x, y, z);
            this.pickSprite(sprites);
            this.lifetime = 8;
            this.scale(0.2f);
            this.rCol = 0.8f;
            this.gCol = 0.0f;
            this.bCol = 0.0f;
            this.xd = (random.nextDouble() - 0.5) * 0.1;
            this.yd = random.nextDouble() * 0.1;
            this.zd = (random.nextDouble() - 0.5) * 0.1;
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        public static class Provider implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet sprites;
            public Provider(SpriteSet sprites) { this.sprites = sprites; }

            @Override
            public net.minecraft.client.particle.Particle createParticle(
                    SimpleParticleType type, ClientLevel level,
                    double x, double y, double z,
                    double xd, double yd, double zd) {
                return new BloodParticle(level, x, y, z, sprites);
            }
        }
    }
}
