package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(KeyBindEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        if(FMLEnvironment.dist.isClient()){
            bindings.add("GLFW", GLFW.class);
            bindings.add("KeyModifier", KeyModifier.class);
        }
    }
}
