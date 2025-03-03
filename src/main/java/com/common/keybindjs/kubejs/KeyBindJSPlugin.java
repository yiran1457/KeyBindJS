package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin extends KubeJSPlugin {

    public void registerEvents() {
        KeyBindEvents.GROUP.register();
    }
    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("KeyBindUtil", KeyBindUtil.INSTANCE);
        event.add("GLFW", GLFW.class);
        event.add("KeyModifier", KeyModifier.class);
    }
}
