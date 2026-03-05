package com.weaponmod.weaponmod.network;

import com.weaponmod.weaponmod.WeaponMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WeaponMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(packetId++,
                StartAutoFirePacket.class,
                StartAutoFirePacket::encode,
                StartAutoFirePacket::decode,
                StartAutoFirePacket::handle);
        INSTANCE.registerMessage(packetId++,
                StopAutoFirePacket.class,
                StopAutoFirePacket::encode,
                StopAutoFirePacket::decode,
                StopAutoFirePacket::handle);
        INSTANCE.registerMessage(packetId++,
                ReloadPacket.class,
                ReloadPacket::encode,
                ReloadPacket::decode,
                ReloadPacket::handle);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}