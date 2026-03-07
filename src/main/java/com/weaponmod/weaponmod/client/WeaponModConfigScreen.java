package com.weaponmod.weaponmod.client;

import com.weaponmod.weaponmod.config.WeaponModConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WeaponModConfigScreen extends Screen {

    private final Screen parent;

    private EditBox pistolField;
    private EditBox revolverField;
    private EditBox ak47Field;
    private EditBox mp5Field;
    private EditBox sniperField;
    private EditBox shotgunField;

    private static final int ROW_HEIGHT = 24;
    private static final int FIELD_X_OFFSET = 10;
    private static final int FIELD_WIDTH = 70;
    private static final int FIELD_HEIGHT = 18;
    private static final int LABEL_COLOR = 0xFFFFFF;
    private static final int ERROR_COLOR = 0xFF5555;

    public WeaponModConfigScreen(Screen parent) {
        super(Component.literal("WeaponMod - Reichweiten Konfiguration"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int startY = 50;

        pistolField   = addRangeField(cx, startY,                 WeaponModConfig.PISTOL_RANGE.get(),   1000);
        revolverField = addRangeField(cx, startY + ROW_HEIGHT,    WeaponModConfig.REVOLVER_RANGE.get(), 1000);
        ak47Field     = addRangeField(cx, startY + ROW_HEIGHT * 2, WeaponModConfig.AK47_RANGE.get(),    1000);
        mp5Field      = addRangeField(cx, startY + ROW_HEIGHT * 3, WeaponModConfig.MP5_RANGE.get(),     1000);
        sniperField   = addRangeField(cx, startY + ROW_HEIGHT * 4, WeaponModConfig.SNIPER_RANGE.get(),  2000);
        shotgunField  = addRangeField(cx, startY + ROW_HEIGHT * 5, WeaponModConfig.SHOTGUN_RANGE.get(), 1000);

        this.addRenderableWidget(Button.builder(
                Component.literal("Speichern"), btn -> save())
                .pos(cx - 105, this.height - 32).size(100, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.literal("Abbrechen"), btn -> onClose())
                .pos(cx + 5, this.height - 32).size(100, 20).build());
    }

    private EditBox addRangeField(int cx, int y, int currentValue, int max) {
        EditBox box = new EditBox(this.font, cx + FIELD_X_OFFSET, y, FIELD_WIDTH, FIELD_HEIGHT,
                Component.empty());
        box.setValue(String.valueOf(currentValue));
        box.setMaxLength(4);
        box.setFilter(s -> s.isEmpty() || s.matches("\\d{1,4}"));
        this.addRenderableWidget(box);
        return box;
    }

    private boolean isValid(EditBox field, int max) {
        try {
            int v = Integer.parseInt(field.getValue());
            return v >= 1 && v <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void save() {
        boolean valid = isValid(pistolField, 1000)
                && isValid(revolverField, 1000)
                && isValid(ak47Field, 1000)
                && isValid(mp5Field, 1000)
                && isValid(sniperField, 2000)
                && isValid(shotgunField, 1000);
        if (!valid) return;

        WeaponModConfig.PISTOL_RANGE.set(Integer.parseInt(pistolField.getValue()));
        WeaponModConfig.REVOLVER_RANGE.set(Integer.parseInt(revolverField.getValue()));
        WeaponModConfig.AK47_RANGE.set(Integer.parseInt(ak47Field.getValue()));
        WeaponModConfig.MP5_RANGE.set(Integer.parseInt(mp5Field.getValue()));
        WeaponModConfig.SNIPER_RANGE.set(Integer.parseInt(sniperField.getValue()));
        WeaponModConfig.SHOTGUN_RANGE.set(Integer.parseInt(shotgunField.getValue()));
        WeaponModConfig.SPEC.save();

        onClose();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 14, LABEL_COLOR);

        int cx = this.width / 2;
        int startY = 50;
        String[] labels = {
            "Pistole:        Blocks",
            "Revolver:       Blocks",
            "AK47:           Blocks",
            "MP5:            Blocks",
            "Scharfschuetze: Blocks",
            "Schrotflinte:   Blocks"
        };
        EditBox[] fields = {pistolField, revolverField, ak47Field, mp5Field, sniperField, shotgunField};
        int[] maxValues = {1000, 1000, 1000, 1000, 2000, 1000};

        for (int i = 0; i < labels.length; i++) {
            int y = startY + ROW_HEIGHT * i + 4;
            graphics.drawString(this.font, labels[i], cx - 155, y, LABEL_COLOR);
            if (fields[i] != null && !isValid(fields[i], maxValues[i])) {
                graphics.drawString(this.font, "! (1-" + maxValues[i] + ")",
                        cx + FIELD_X_OFFSET + FIELD_WIDTH + 4, y, ERROR_COLOR);
            }
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}
