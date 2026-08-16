package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.client.KeyMapping;

public class KeyBindJSKeyEvent implements KubeEvent {
    public KeyMapping keyMapping;

    public KeyBindJSKeyEvent(KeyMapping pKeyMapping) {
        keyMapping = pKeyMapping;
    }

    public KeyMapping getKeyMapping() {
        return keyMapping;
    }
}
