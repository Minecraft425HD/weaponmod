package com.weaponmod.weaponmod.attachment;

import com.weaponmod.weaponmod.gun.GunItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilencerAttachment extends Item {
    public SilencerAttachment() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack attachmentStack = player.getItemInHand(hand);
        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.getItem() instanceof GunItem gun && !gun.hasAttachment(mainHand)) {
            if (!level.isClientSide) {
                gun.attach(mainHand, ModAttachments.SILENCER);
                attachmentStack.shrink(1);
                player.displayClientMessage(Component.literal("§aSchalldämpfer angebracht!"), true);
            }
            return InteractionResultHolder.success(attachmentStack);
        }
        return InteractionResultHolder.pass(attachmentStack);
    }
}