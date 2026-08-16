package com.common.keybindjs.kubejs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class KeyBindUtil {
    public static KeyBindUtil INSTANCE = new KeyBindUtil();

    public KeyMapping[] getAllKeyMappings() {
        return Minecraft.getInstance().options.keyMappings;
    }

    public String[] getAllKeyName() {
        return Arrays.stream(getAllKeyMappings()).map(keyMapping -> Component.translatable(keyMapping.getName()).getString() + " : " + keyMapping.getName()).toArray(String[]::new);
    }

    public ObjectArrayList<KeyMapping.Category> getAllKeyCategory() {
        return Arrays.stream(getAllKeyMappings()).map(KeyMapping::getCategory).collect(ObjectArrayList.toList());
    }

    public KeyMapping ToKeyMapping(KeyMapping keyMapping) {
        return keyMapping;
    }

    public KeyMapping getKeyMapping(String keyName) {
        return KeyMapping.get(keyName);
    }

    public boolean isDown(KeyMapping mapping) {
        return mapping != null && mapping.isDown();
    }
}
