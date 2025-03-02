package com.common.keybindjs.kubejs;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyBindUtil {
    public static KeyMapping[] getAllKeyMappings() {
        return Minecraft.getInstance().options.keyMappings;
    }

    public static String[] getAllKeyName() {
        return Arrays.stream(getAllKeyMappings()).map(keyMapping-> Component.translatable(keyMapping.getName()).getString()+" : "+keyMapping.getName()).toArray(String[]::new);
    }

    public static String[] getAllKeyCategory() {
        return Arrays.stream(getAllKeyMappings()).map(keyMapping->
                Component.translatable(keyMapping.getCategory()).getString() + " : "+keyMapping.getCategory()
        ).collect(Collectors.toSet()).toArray(String[]::new);
    }

    public static boolean isDown (String customName) {
        return isDown(getKeyMapping(customName));
    }

    public static KeyMapping getKeyMapping (String customName) {
        return KeyBindEvent.keyMappings.get(customName);
    }

    private static boolean isDown (KeyMapping Mapping) {
        return Mapping.isDown();
    }
}
