package com.weaponmod.weaponmod.skill;

import com.weaponmod.weaponmod.util.ModNBT;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SkillSystem {
    public enum SkillType {
        PISTOL(ModNBT.SKILL_PISTOL_XP),
        RIFLE(ModNBT.SKILL_RIFLE_XP),
        MELEE(ModNBT.SKILL_MELEE_XP);

        public final String nbtKey;
        SkillType(String key) { nbtKey = key; }
    }

    public static void addXP(Player player, SkillType type, int amount) {
        CompoundTag data = player.getPersistentData();
        String key = type.nbtKey;
        int current = data.getInt(key);
        data.putInt(key, current + amount);

        int newLevel = current / 100;
        int oldLevel = (current - amount) / 100;
        if (newLevel > oldLevel) {
            player.displayClientMessage(Component.literal("§aSkill erhöht! " + type + " Level " + newLevel), true);
        }
    }

    public static int getLevel(Player player, SkillType type) {
        return player.getPersistentData().getInt(type.nbtKey) / 100;
    }

    public static float modifyDamage(Player player, SkillType type, float damage) {
        int level = getLevel(player, type);
        return damage * (1 + level * 0.05f);
    }
}