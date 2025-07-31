package com.common.keybindjs.kubejs.neo;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.client.KeyMapping;

public class NewKeyBindEventJS extends EventJS {
    public KeyMapping keyMapping;
    public NewKeyBindEventJS(KeyMapping pKeyMapping) {
        keyMapping = pKeyMapping;
    }
    public KeyMapping getKeyMapping() {
        return keyMapping;
    }
}
