package com.weaponmod.weaponmod.attachment;

import net.minecraft.world.item.Item;

/**
 * Basisklasse für alle Attachment-Items.
 * Hält die zugehörige Attachment-Instanz und erlaubt GunItem,
 * den Typ per instanceof-Check zu erkennen.
 */
public abstract class BaseAttachmentItem extends Item {
    private final Attachment attachment;

    public BaseAttachmentItem(Attachment attachment) {
        super(new Item.Properties().stacksTo(1));
        this.attachment = attachment;
    }

    public Attachment getModAttachment() {
        return attachment;
    }
}
