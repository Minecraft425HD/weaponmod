package com.weaponmod.weaponmod.entity;

import com.weaponmod.weaponmod.attachment.Attachment;
import com.weaponmod.weaponmod.item.ModItems;
import com.weaponmod.weaponmod.particle.ModParticles;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class CustomBulletEntity extends AbstractArrow {
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(CustomBulletEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> AMMO_TYPE = SynchedEntityData.defineId(CustomBulletEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> ATTACHMENT = SynchedEntityData.defineId(CustomBulletEntity.class, EntityDataSerializers.STRING);

    public CustomBulletEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public CustomBulletEntity(Level level, LivingEntity shooter, float damage, Item ammoType, Attachment attachment) {
        super(ModEntities.CUSTOM_BULLET.get(), shooter, level);
        this.setNoGravity(true);
        this.entityData.set(DAMAGE, damage);
        this.entityData.set(AMMO_TYPE, ammoType.getDescriptionId());
        this.entityData.set(ATTACHMENT, attachment != null ? attachment.getType().name() : "");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DAMAGE, 5.0f);
        this.entityData.define(AMMO_TYPE, ModItems.AMMO_STANDARD.get().getDescriptionId());
        this.entityData.define(ATTACHMENT, "");
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (target instanceof LivingEntity living) {
            float damage = this.entityData.get(DAMAGE);
            String ammoTypeId = this.entityData.get(AMMO_TYPE);
            Item ammoType = ModItems.ITEMS.getEntries().stream()
                    .map(reg -> reg.get())
                    .filter(item -> item.getDescriptionId().equals(ammoTypeId))
                    .findFirst().orElse(ModItems.AMMO_STANDARD.get());

            if (ammoType == ModItems.AMMO_AP.get()) {
                damage *= 1.5;
            } else if (ammoType == ModItems.AMMO_RUBBER.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0));
                damage *= 0.3;
            }

            living.hurt(living.damageSources().arrow(this, (LivingEntity) this.getOwner()), damage);

            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ModParticles.BLOOD.get(), living.getX(), living.getY() + 1, living.getZ(),
                        10, 0.2, 0.2, 0.2, 0);
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, result.getLocation().x, result.getLocation().y, result.getLocation().z,
                    10, 0.1, 0.1, 0.1, 0.1);
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel server) {
            String ammoTypeId = this.entityData.get(AMMO_TYPE);
            if (ammoTypeId.equals(ModItems.AMMO_TRACER.get().getDescriptionId())) {
                server.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(),
                        1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("BulletDamage", this.entityData.get(DAMAGE));
        tag.putString("BulletAmmo", this.entityData.get(AMMO_TYPE));
        tag.putString("BulletAttachment", this.entityData.get(ATTACHMENT));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DAMAGE, tag.getFloat("BulletDamage"));
        this.entityData.set(AMMO_TYPE, tag.getString("BulletAmmo"));
        this.entityData.set(ATTACHMENT, tag.getString("BulletAttachment"));
    }
}