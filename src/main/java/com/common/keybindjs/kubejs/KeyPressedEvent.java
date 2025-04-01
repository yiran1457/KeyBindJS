package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import dev.latvian.mods.kubejs.client.ClientEventJS;
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

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE, modid = KeyBindJS.MODID)
public class KeyPressedEvent extends ClientEventJS {

    private String customName;

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

    public KeyPressedEvent(String customName) {
        this.customName = customName;
    }

    @SubscribeEvent
    @HideFromJS
    public static void onKeyPress(InputEvent.Key event) {
        KeyBindModifyEvent.keyMappingListener.forEach((k, v) -> {
            if (isDown(event, v)) {
                if (Minecraft.getInstance().screen == null) {
                    if (event.getAction() == 1)
                        KeyBindEvents.FIRST_KEY_PRESS.post(new KeyPressedEvent(k), k);
                    if (event.getAction() == 0)
                        KeyBindEvents.KEY_RELEASE.post(new KeyPressedEvent(k), k);
                    if (event.getAction() != 0)
                        KeyBindEvents.KEY_PRESS.post(new KeyPressedEvent(k), k);
                } else {
                    if (event.getAction() == 1)
                        KeyBindEvents.FIRST_KEY_PRESS_GUI.post(new KeyPressedEvent(k), k);
                    if (event.getAction() == GLFW.GLFW_RELEASE)
                        KeyBindEvents.KEY_RELEASE_GUI.post(new KeyPressedEvent(k), k);
                    if (event.getAction() != 0)
                        KeyBindEvents.KEY_PRESS_GUI.post(new KeyPressedEvent(k), k);
                }
            }
        });
    }

    private static Boolean isDown(InputEvent.Key event, KeyMapping keyMapping) {
        return keyMapping.getKey().getValue() == event.getKey() && getModifyerMap().get(event.getModifiers()) == keyMapping.getKeyModifier();


    }


    public String getCustomName() {
        return customName;
    }
}
