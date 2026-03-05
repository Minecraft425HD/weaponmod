package com.weaponmod.weaponmod.network;

import com.weaponmod.weaponmod.gun.GunItem;
import com.weaponmod.weaponmod.util.ModNBT;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartAutoFirePacket {
    private final int slot;

    public StartAutoFirePacket(int slot) {
        this.slot = slot;
    }

    public static void encode(StartAutoFirePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.slot);
    }

    public static StartAutoFirePacket decode(FriendlyByteBuf buf) {
        return new StartAutoFirePacket(buf.readInt());
    }

    public static void handle(StartAutoFirePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            ItemStack gunStack = msg.slot == -1 ? player.getOffhandItem() : player.getInventory().getItem(msg.slot);
            if (gunStack.getItem() instanceof GunItem) {
                player.getPersistentData().putBoolean(ModNBT.AUTO_FIRE_ACTIVE, true);
                player.getPersistentData().putInt(ModNBT.AUTO_FIRE_GUN_SLOT, msg.slot);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}