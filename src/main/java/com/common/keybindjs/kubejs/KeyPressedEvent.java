package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.machinezoo.noexception.throwing.ThrowingRunnable;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Set;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE, modid = KeyBindJS.MODID)
public class KeyPressedEvent extends ClientEventJS {

    public static HashMap<Integer, KeyModifier> getModifyerMap() {
        return Lazy.of(() -> {
            HashMap<Integer, KeyModifier> modifierMap = new HashMap<>();
            modifierMap.put(0, KeyModifier.valueOf("NONE"));
            modifierMap.put(1, KeyModifier.valueOf("SHIFT"));
            modifierMap.put(2, KeyModifier.valueOf("CONTROL"));
            modifierMap.put(4, KeyModifier.valueOf("ALT"));
            return modifierMap;
        }).get();
    }

    public KeyPressedEvent() {
    }

    @SubscribeEvent
    @HideFromJS
    public static void onKeyPress(InputEvent.Key event) {

        if (Minecraft.getInstance().screen == null) {
            switch (event.getAction()) {
                case 1 -> post(KeyBindEvents.FIRST_KEY_PRESS,event);
                case 0 -> post(KeyBindEvents.KEY_RELEASE,event);
                case 2 -> post(KeyBindEvents.KEY_PRESS,event);
            }
        } else {
            switch (event.getAction()) {
                case 1 -> post(KeyBindEvents.FIRST_KEY_PRESS_GUI,event);
                case 0 -> post(KeyBindEvents.KEY_RELEASE_GUI,event);
                case 2 -> post(KeyBindEvents.KEY_PRESS_GUI,event);
            }
        }
    }

    private static void post(EventHandler JSEvent,InputEvent.Key event) {
        JSEvent.findUniqueExtraIds(ScriptType.CLIENT).forEach(key -> {
            if (isDown(event, AllKeyBindJSList.RegisterKeyMappings.getOrDefault(key, KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping((String) key)))) {
                JSEvent.post(new KeyPressedEvent(), key);
            }
        });
    }

    private static Boolean isDown(InputEvent.Key event, KeyMapping keyMapping) {
        return keyMapping.getKey().getValue() == event.getKey() && getModifyerMap().get(event.getModifiers()) == keyMapping.getKeyModifier();
    }
}
