package com.common.keybindjs.kubejs;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.util.Lazy;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyBindUtil {
    private KeyBindUtil() {
    }

    public static KeyBindUtil INSTANCE = Lazy.of(KeyBindUtil::new).get();

    public KeyMapping[] getAllKeyMappings() {
        return Lazy.of(() -> Minecraft.getInstance().options.keyMappings).get();
    }

    public String[] getAllKeyName() {
        return Arrays.stream(getAllKeyMappings()).map(keyMapping -> Component.translatable(keyMapping.getName()).getString() + " : " + keyMapping.getName()).toArray(String[]::new);
    }

    public String[] getAllKeyCategory() {
        return Arrays.stream(getAllKeyMappings()).map(keyMapping ->
                Component.translatable(keyMapping.getCategory()).getString() + " : " + keyMapping.getCategory()
        ).collect(Collectors.toSet()).toArray(String[]::new);
    }

    public boolean isDown(String customName) {
        return isDown(getKeyMapping(customName));
    }

    public KeyMapping getKeyMapping(String customName) {
        return KeyBindEvent.keyMappings.get(customName);
    }

    public KeyMapping findKeyMappingInAllKeyMapping(String keyName) {
        return KeyMapping.ALL.get(keyName);
    }

    private boolean isDown(KeyMapping Mapping) {
        return Mapping.isDown();
    }
}
