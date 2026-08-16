package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.util.wrap.TypeWrapperValidator;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindJSPlugin implements KubeJSPlugin {
    public static TypeWrapperValidator NONNULL = ((o, _) -> o != null);

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(KeyBindEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        if (!FMLEnvironment.getDist().isClient()) return;
        bindings.add("KeyBindUtil", KeyBindUtil.INSTANCE);
        bindings.add("GLFW", GLFW.class);
        bindings.add("KeyModifier", KeyModifier.class);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        if (!FMLEnvironment.getDist().isClient()) return;
        if (!registry.hasTypeWrapper(KeyMapping.class)) {
            registry.register(KeyMapping.class, NONNULL, KeyBindJSPlugin::wrapperKeyMapping);
        }
        if (!registry.hasTypeWrapper(KeyMapping.Category.class)) {
            registry.register(KeyMapping.Category.class, NONNULL, KeyBindJSPlugin::wrapperKeyCategory);
        }
    }

    public static KeyMapping wrapperKeyMapping(Object o) {
        while (o instanceof Wrapper) {
            o = ((Wrapper) o).unwrap();
        }
        return switch (o) {
            case KeyMapping k -> k;
            case String s -> KeyMapping.get(s);
            default -> KeyMapping.get(o.toString());
        };
    }

    public static KeyMapping.Category wrapperKeyCategory(Object o) {
        while (o instanceof Wrapper) {
            o = ((Wrapper) o).unwrap();
        }

        return switch (o) {
            case KeyMapping.Category c -> c;
            case Identifier i -> new KeyMapping.Category(i);
            case String s -> new KeyMapping.Category(Identifier.parse(s));
            default -> new KeyMapping.Category(Identifier.parse(o.toString()));
        };
    }

}
