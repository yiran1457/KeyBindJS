package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.util.wrap.TypeWrapperValidator;
import net.minecraft.client.KeyMapping;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin implements KubeJSPlugin {
    public static TypeWrapperValidator NONNULL = ((o, type) -> o != null);

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(KeyBindEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        if (!FMLEnvironment.dist.isClient()) return;
        bindings.add("KeyBindUtil", KeyBindUtil.INSTANCE);
        bindings.add("GLFW", GLFW.class);
        bindings.add("KeyModifier", KeyModifier.class);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        if (!FMLEnvironment.dist.isClient()) return;
        if (!registry.hasTypeWrapper(KeyMapping.class)) {
            registry.register(KeyMapping.class, NONNULL, KeyBindJSPlugin::wrapperKeyMapping);
        }
    }

    public static KeyMapping wrapperKeyMapping(Object o) {
        while (o instanceof Wrapper) {
            o = ((Wrapper) o).unwrap();
        }
        return switch (o) {
            case KeyMapping k -> k;
            case String s -> KeyMapping.ALL.get(s);
            default -> KeyMapping.ALL.get(o.toString());
        };
    }
}
