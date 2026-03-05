package com.weaponmod.weaponmod.network;

import com.weaponmod.weaponmod.gun.GunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadPacket {
    public ReloadPacket() {}

    public static void encode(ReloadPacket msg, FriendlyByteBuf buf) {}

    public static ReloadPacket decode(FriendlyByteBuf buf) {
        return new ReloadPacket();
    }

    public static void handle(ReloadPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.getItem() instanceof GunItem gun) {
                gun.reload(player, mainHand);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}