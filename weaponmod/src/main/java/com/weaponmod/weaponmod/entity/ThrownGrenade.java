package com.weaponmod.weaponmod.entity;

import com.weaponmod.weaponmod.grenade.GrenadeType;
import com.weaponmod.weaponmod.item.ModItems;
import com.weaponmod.weaponmod.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class ThrownGrenade extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> GRENADE_TYPE =
            SynchedEntityData.defineId(ThrownGrenade.class, EntityDataSerializers.INT);

    public ThrownGrenade(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownGrenade(Level level, LivingEntity thrower, GrenadeType type) {
        super(ModEntities.THROWN_GRENADE.get(), thrower, level);
        this.entityData.set(GRENADE_TYPE, type.ordinal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GRENADE_TYPE, 0);
    }

    @Override
    protected Item getDefaultItem() {
        return switch (GrenadeType.values()[entityData.get(GRENADE_TYPE)]) {
            case FRAG -> ModItems.FRAG_GRENADE.get();
            case SMOKE -> ModItems.SMOKE_GRENADE.get();
            case FLASH -> ModItems.FLASH_GRENADE.get();
        };
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            GrenadeType type = GrenadeType.values()[entityData.get(GRENADE_TYPE)];
            switch (type) {
                case FRAG -> explode();
                case SMOKE -> spawnSmoke();
                case FLASH -> flash();
            }
            this.discard();
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.GRENADE_EXPLODE.get(), net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
    }

    private void explode() {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, true, Level.ExplosionInteraction.MOB);
    }

    private void spawnSmoke() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(),
                    200, 2, 2, 2, 0.05);
        }
    }

    private void flash() {
        AABB aabb = this.getBoundingBox().inflate(8);
        for (var entity : this.level().getEntities(this, aabb)) {
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("GrenadeType", entityData.get(GRENADE_TYPE));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(GRENADE_TYPE, tag.getInt("GrenadeType"));
    }
}