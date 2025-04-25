package com.common.keybindjs.oi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class KubeJSDebugScreen extends Screen {
    public KubeJSDebugScreen() {
        super(Component.literal("debug"));
    }

    @Override
    protected void init() {
        Minecraft mc = this.minecraft;
        int CW = mc.getWindow().getGuiScaledWidth() / 2;
        int CH = mc.getWindow().getGuiScaledHeight() / 2;

        addWidgetWithSize(CW + 55, CH - 88, 88, 20, "reload", (button) -> mc.player.connection.sendCommand("reload"));
        addWidgetWithSize(CW - 55, CH - 88, 88, 20, "kjs reload server", (button) -> mc.player.connection.sendCommand("kjs reload server_scripts"));
        addWidgetWithSize(CW + 55, CH - 60, 88, 20, "kjs reload client", (button) -> mc.player.connection.sendCommand("kjs reload client_scripts"));
        addWidgetWithSize(CW - 55, CH - 60, 88, 20, "kjs reload startup", (button) -> mc.player.connection.sendCommand("kjs reload startup_scripts"));
        addWidgetWithSize(CW, CH - 32, 110 + 88, 20, "probejs dump", (button) -> mc.player.connection.sendCommand("probejs dump"));

    }

    void addWidgetWithSize(int x, int y, int sizeX, int sizeY, String text, Button.OnPress action) {
        addWidget(x - sizeX / 2, y - sizeY / 2, sizeX, sizeY, text, action);
    }

    void addWidget(int x, int y, int w, int h, String text, Button.OnPress action) {
        this.addRenderableWidget(
                Button.builder(
                                Component.literal(text),
                                action
                        )
                        .pos(x, y)
                        .size(w, h)
                        .build()
        );
    }
}
