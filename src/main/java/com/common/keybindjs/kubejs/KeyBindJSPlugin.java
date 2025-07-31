package com.common.keybindjs.kubejs;

import com.common.keybindjs.kubejs.neo.NewKeybindEvents;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin extends KubeJSPlugin {

    public void registerEvents() {
        KeyBindEvents.GROUP.register();
        NewKeybindEvents.NEW_GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        if(FMLEnvironment.dist == Dist.CLIENT){
            event.add("KeyBindUtil", KeyBindUtil.INSTANCE);
            event.add("GLFW", GLFW.class);
            event.add("KeyModifier", KeyModifier.class);
        }
    }


    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            typeWrappers.registerSimple(KeyMapping.class, KeyBindJSPlugin::keyOf);
        }
    }

    public static KeyMapping keyOf(Object o) {
        if (o instanceof KeyMapping) {
            return (KeyMapping) o;
        }
        return KeyMapping.ALL.get(o);
    }
}
