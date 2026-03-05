package com.weaponmod.weaponmod.network;

import com.weaponmod.weaponmod.util.ModNBT;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StopAutoFirePacket {
    public StopAutoFirePacket() {}

    public static void encode(StopAutoFirePacket msg, FriendlyByteBuf buf) {}

    public static StopAutoFirePacket decode(FriendlyByteBuf buf) {
        return new StopAutoFirePacket();
    }

    public static void handle(StopAutoFirePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            player.getPersistentData().remove(ModNBT.AUTO_FIRE_ACTIVE);
        });
        ctx.get().setPacketHandled(true);
    }
}