package com.weaponmod.weaponmod.gun;

import com.weaponmod.weaponmod.attachment.Attachment;
import com.weaponmod.weaponmod.attachment.BaseAttachmentItem;
import com.weaponmod.weaponmod.attachment.ModAttachments;
import com.weaponmod.weaponmod.entity.CustomBulletEntity;
import com.weaponmod.weaponmod.item.ModItems;
import com.weaponmod.weaponmod.particle.ModParticles;
import com.weaponmod.weaponmod.skill.SkillSystem;
import com.weaponmod.weaponmod.sound.ModSounds;
import com.weaponmod.weaponmod.util.ModNBT;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GunItem extends Item {
    protected final GunProperties properties;

    private static final String TAG_AMMO = "CurrentAmmo";
    private static final String TAG_FIRE_MODE = "FireMode";
    private static final String TAG_LAST_SHOT_TIME = "LastShotTime";
    private static final String TAG_SHOTS_FIRED = "ShotsFired";
    private static final String TAG_ATTACHMENT_1 = "Attachment1";
    private static final String TAG_ATTACHMENT_2 = "Attachment2";
    private static final String TAG_AMMO_TYPE = "AmmoType";

    public GunItem(GunProperties properties) {
        super(new Item.Properties().stacksTo(1).durability(properties.getDurability()));
        this.properties = properties;
    }

    public int getCurrentAmmo(ItemStack stack) {
        return stack.getOrCreateTag().getInt(TAG_AMMO);
    }

    public void setCurrentAmmo(ItemStack stack, int ammo) {
        stack.getOrCreateTag().putInt(TAG_AMMO, Math.min(ammo, properties.getMaxAmmo()));
    }

    public int getFireMode(ItemStack stack) {
        return stack.getOrCreateTag().getInt(TAG_FIRE_MODE);
    }

    public void cycleFireMode(ItemStack stack, Player player) {
        int mode = getFireMode(stack);
        mode = (mode + 1) % 3;
        stack.getOrCreateTag().putInt(TAG_FIRE_MODE, mode);
        String modeName = switch (mode) {
            case 0 -> "§aEinzelfeuer";
            case 1 -> "§eBurst (3)";
            case 2 -> "§cVollauto";
            default -> "";
        };
        player.displayClientMessage(Component.literal("Feuermodus: " + modeName), true);
        player.playSound(ModSounds.CLICK.get(), 0.5F, 1.0F);
    }

    // Fügt ein Attachment in den nächsten freien Slot ein.
    // Gibt false zurück wenn: Typ bereits montiert ODER beide Slots voll.
    public boolean addAttachment(ItemStack gunStack, Attachment attachment) {
        if (hasAttachmentType(gunStack, attachment.getType())) return false;
        CompoundTag tag = gunStack.getOrCreateTag();
        if (!tag.contains(TAG_ATTACHMENT_1)) {
            tag.putString(TAG_ATTACHMENT_1, attachment.getType().name());
            return true;
        }
        if (!tag.contains(TAG_ATTACHMENT_2)) {
            tag.putString(TAG_ATTACHMENT_2, attachment.getType().name());
            return true;
        }
        return false; // beide Slots belegt
    }

    public boolean hasAttachment(ItemStack gunStack) {
        if (!gunStack.hasTag()) return false;
        CompoundTag tag = gunStack.getTag();
        return tag.contains(TAG_ATTACHMENT_1) || tag.contains(TAG_ATTACHMENT_2);
    }

    public boolean hasAttachmentType(ItemStack gunStack, Attachment.Type type) {
        if (!gunStack.hasTag()) return false;
        String name = type.name();
        CompoundTag tag = gunStack.getTag();
        return name.equals(tag.getString(TAG_ATTACHMENT_1)) || name.equals(tag.getString(TAG_ATTACHMENT_2));
    }

    public List<Attachment> getAttachments(ItemStack gunStack) {
        List<Attachment> result = new ArrayList<>();
        if (!gunStack.hasTag()) return result;
        CompoundTag tag = gunStack.getTag();
        if (tag.contains(TAG_ATTACHMENT_1)) {
            Attachment a = ModAttachments.getByType(tag.getString(TAG_ATTACHMENT_1));
            if (a != null) result.add(a);
        }
        if (tag.contains(TAG_ATTACHMENT_2)) {
            Attachment a = ModAttachments.getByType(tag.getString(TAG_ATTACHMENT_2));
            if (a != null) result.add(a);
        }
        return result;
    }

    // Gibt das erste Attachment zurück (für Stellen die nur eines brauchen)
    public Attachment getAttachment(ItemStack gunStack) {
        List<Attachment> atts = getAttachments(gunStack);
        return atts.isEmpty() ? null : atts.get(0);
    }

    // Inventory-Click: Attachment-Item auf Waffe klicken
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack other, Slot slot,
                                             ClickAction action, Player player, SlotAccess access) {
        if (action != ClickAction.SECONDARY) return false;
        if (!(other.getItem() instanceof BaseAttachmentItem attachmentItem)) return false;

        Attachment attachment = attachmentItem.getModAttachment();
        if (hasAttachmentType(thisStack, attachment.getType())) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                    Component.literal("§cDieses Zubehör ist bereits montiert!"), true);
            }
            return true;
        }
        if (!addAttachment(thisStack, attachment)) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                    Component.literal("§cMaximal 2 Zubehörteile pro Waffe!"), true);
            }
            return true;
        }
        other.shrink(1);
        if (!player.level().isClientSide) {
            String name = switch (attachment.getType()) {
                case SCOPE -> "Visier";
                case SILENCER -> "Schalldämpfer";
                case LASER -> "Laser";
            };
            player.displayClientMessage(Component.literal("§a" + name + " montiert!"), true);
        }
        return true;
    }

    public Item getLoadedAmmoType(ItemStack gunStack) {
        if (!gunStack.hasTag() || !gunStack.getTag().contains(TAG_AMMO_TYPE))
            return ModItems.AMMO_STANDARD.get();
        String ammoName = gunStack.getTag().getString(TAG_AMMO_TYPE);
        return ModItems.ITEMS.getEntries().stream()
                .map(reg -> reg.get())
                .filter(item -> item.getDescriptionId().equals(ammoName))
                .findFirst().orElse(ModItems.AMMO_STANDARD.get());
    }

    public void setLoadedAmmoType(ItemStack gunStack, Item ammoItem) {
        gunStack.getOrCreateTag().putString(TAG_AMMO_TYPE, ammoItem.getDescriptionId());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack gunStack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                cycleFireMode(gunStack, player);
            }
            return InteractionResultHolder.success(gunStack);
        }

        return InteractionResultHolder.pass(gunStack);
    }

    public void performShots(Level level, Player player, ItemStack gunStack, int count) {
        for (int i = 0; i < count; i++) {
            if (!canShoot(gunStack, player)) break;
            if (!player.isCreative()) {
                int currentAmmo = getCurrentAmmo(gunStack);
                if (currentAmmo <= 0) break;
                setCurrentAmmo(gunStack, currentAmmo - 1);
            }
            shootProjectile(level, player, gunStack);
            addShotHistory(gunStack);
            applyRecoil(player);
        }
        player.getCooldowns().addCooldown(this, getCurrentCooldown(gunStack));
    }

    public void shootProjectile(Level level, Player player, ItemStack gunStack) {
        Item ammoType = getLoadedAmmoType(gunStack);
        List<Attachment> attachments = getAttachments(gunStack);

        float baseDamage = properties.getBaseDamage();
        if (ammoType == ModItems.AMMO_AP.get()) baseDamage *= 0.8;
        else if (ammoType == ModItems.AMMO_RUBBER.get()) baseDamage *= 0.3;
        for (Attachment a : attachments) baseDamage *= a.getDamageMultiplier();

        baseDamage = SkillSystem.modifyDamage(player,
                this instanceof PistolItem || this instanceof RevolverItem ? SkillSystem.SkillType.PISTOL : SkillSystem.SkillType.RIFLE,
                baseDamage);

        // Erstes Attachment an die Bullet-Entity übergeben (für visuelle Effekte)
        Attachment firstAttachment = attachments.isEmpty() ? null : attachments.get(0);
        CustomBulletEntity bullet = new CustomBulletEntity(level, player, baseDamage, ammoType, firstAttachment, properties.getRange());
        double accuracy = getCurrentAccuracy(gunStack);
        int shotsFired = getShotsFired(gunStack);
        double spread = (1.0 - accuracy) * 0.1 * (1 + shotsFired * 0.1);

        Vec3 lookVec = player.getLookAngle();
        bullet.shoot(
                lookVec.x + (level.random.nextDouble() - 0.5) * spread,
                lookVec.y + (level.random.nextDouble() - 0.5) * spread,
                lookVec.z + (level.random.nextDouble() - 0.5) * spread,
                3.0F, 0F
        );

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, gunStack) > 0) {
            bullet.setSecondsOnFire(100);
        }

        level.addFreshEntity(bullet);

        float volume = 1.0f;
        for (Attachment a : attachments) volume *= a.getSoundMultiplier();
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.GUN_SHOT.get(), SoundSource.PLAYERS, volume, 1.0F);

        if (level instanceof ServerLevel server) {
            Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(1.2));
            server.sendParticles(ModParticles.MUZZLE_FLASH.get(), pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }
    }

    public boolean canShoot(ItemStack stack, Player player) {
        if (player.isCreative()) return true;
        if (properties.usesMagazines()) {
            return getCurrentAmmo(stack) > 0;
        } else {
            return hasAmmo(player);
        }
    }

    private boolean hasAmmo(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == properties.getAmmoType()) return true;
        }
        return false;
    }

    public void reload(Player player, ItemStack gunStack) {
        if (player.isCreative()) {
            setCurrentAmmo(gunStack, properties.getMaxAmmo());
            setLoadedAmmoType(gunStack, ModItems.AMMO_STANDARD.get());
            return;
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == properties.getAmmoType()) {
                Item loadedAmmo = stack.getItem();
                stack.shrink(1);
                setCurrentAmmo(gunStack, properties.getMaxAmmo());
                setLoadedAmmoType(gunStack, loadedAmmo);
                player.playSound(ModSounds.RELOAD.get(), 1.0F, 1.0F);
                return;
            }
        }
        player.displayClientMessage(Component.literal("§cKein Magazin gefunden!"), true);
    }

    private int getShotsFired(ItemStack stack) {
        return stack.getOrCreateTag().getInt(TAG_SHOTS_FIRED);
    }

    public void addShotHistory(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int shots = tag.getInt(TAG_SHOTS_FIRED) + 1;
        tag.putInt(TAG_SHOTS_FIRED, shots);
        tag.putLong(TAG_LAST_SHOT_TIME, System.currentTimeMillis());
    }

    public void resetShotHistory(ItemStack stack) {
        stack.getOrCreateTag().putInt(TAG_SHOTS_FIRED, 0);
    }

    private double getCurrentAccuracy(ItemStack stack) {
        double accuracy = properties.getBaseAccuracy();
        for (Attachment a : getAttachments(stack)) accuracy *= a.getAccuracyMultiplier();
        int shots = getShotsFired(stack);
        long last = stack.getOrCreateTag().getLong(TAG_LAST_SHOT_TIME);
        long now = System.currentTimeMillis();
        if (now - last > 2000) {
            resetShotHistory(stack);
        } else {
            accuracy *= Math.max(0.5, 1.0 - shots * 0.05);
        }
        return accuracy;
    }

    private int getCurrentCooldown(ItemStack stack) {
        int cd = properties.getBaseCooldown();
        for (Attachment a : getAttachments(stack)) {
            if (a.getType() == Attachment.Type.SILENCER) {
                cd = (int) (cd * 1.2f);
            }
        }
        return cd;
    }

    private void applyRecoil(Player player) {}

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int ammo = getCurrentAmmo(stack);
        tooltip.add(Component.literal("§7Munition: §a" + ammo + "/" + properties.getMaxAmmo()));
        int mode = getFireMode(stack);
        String modeStr = switch (mode) {
            case 0 -> "Einzelfeuer";
            case 1 -> "Burst";
            case 2 -> "Vollauto";
            default -> "";
        };
        tooltip.add(Component.literal("§7Modus: §e" + modeStr));
        List<Attachment> atts = getAttachments(stack);
        if (!atts.isEmpty()) {
            String names = atts.stream()
                .map(a -> switch (a.getType()) {
                    case SCOPE -> "Visier";
                    case SILENCER -> "Schalldämpfer";
                    case LASER -> "Laser";
                })
                .collect(Collectors.joining(", "));
            tooltip.add(Component.literal("§7Zubehör: §d" + names));
        }
        Item ammoType = getLoadedAmmoType(stack);
        tooltip.add(Component.literal("§7Geladene Munition: §b" + ammoType.getDescription().getString()));
    }

    public GunProperties getProperties() { return properties; }
}
