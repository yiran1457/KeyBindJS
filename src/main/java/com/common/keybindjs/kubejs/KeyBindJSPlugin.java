package com.common.keybindjs.kubejs;

import com.common.keybindjs.kubejs.extra.JSIO;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.DistExecutor;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin extends KubeJSPlugin {

    public void registerEvents() {
        KeyBindEvents.GROUP.register();
    }
    @Override
    public void registerBindings(BindingsEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->{
            event.add("KeyBindUtil", KeyBindUtil.INSTANCE);
            event.add("GLFW", GLFW.class);
            event.add("KeyModifier", KeyModifier.class);
        });
        event.add("JSIO", JSIO.INSTANCE);
    }
}
