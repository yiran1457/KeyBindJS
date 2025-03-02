package com.common.keybindjs.kubejs;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyBindUtil {
    public static KeyMapping[] getKeyMappings() {
        return Minecraft.getInstance().options.keyMappings;
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
