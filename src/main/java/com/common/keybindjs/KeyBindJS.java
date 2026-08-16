package com.common.keybindjs;

import com.common.keybindjs.kubejs.KeyEventHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(KeyBindJS.MODID)
public class KeyBindJS {
    public static final String MODID = "keybindjs";

    public KeyBindJS(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.getDist().isClient()) {
            NeoForge.EVENT_BUS.register(KeyEventHandler.class);
        }
    }
}
