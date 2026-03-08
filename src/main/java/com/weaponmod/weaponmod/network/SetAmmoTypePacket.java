package com.weaponmod.weaponmod.network;

import com.weaponmod.weaponmod.gun.GunItem;
import com.weaponmod.weaponmod.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class SetAmmoTypePacket {
    private final int ammoIndex;

    /** Geordnete Liste der waehlbaren Munitionstypen (muss mit Client-Anzeige uebereinstimmen). */
    public static final List<RegistryObject<Item>> AMMO_TYPES = List.of(
            ModItems.AMMO_STANDARD,
            ModItems.AMMO_AP,
            ModItems.AMMO_TRACER,
            ModItems.AMMO_RUBBER
    );

    public SetAmmoTypePacket(int ammoIndex) {
        this.ammoIndex = ammoIndex;
    }

    public static void encode(SetAmmoTypePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.ammoIndex);
    }

    public static SetAmmoTypePacket decode(FriendlyByteBuf buf) {
        return new SetAmmoTypePacket(buf.readInt());
    }

    public static void handle(SetAmmoTypePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            int index = msg.ammoIndex;
            if (index < 0 || index >= AMMO_TYPES.size()) return;
            ItemStack mainHand = player.getMainHandItem();
            if (!(mainHand.getItem() instanceof GunItem gun)) return;
            gun.setLoadedAmmoType(mainHand, AMMO_TYPES.get(index).get());
        });
        ctx.get().setPacketHandled(true);
    }
}
