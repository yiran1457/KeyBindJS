package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.client.ClientInitEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyBindEvent extends ClientInitEventJS {

    @HideFromJS
    public KeyBindEvent() {
    }

    @Deprecated
    public KeyBindBuilder create(String customName, String keyNameKey, int keyCode, String keyGroupKey) {
        KeyMapping keyMapping = new KeyMapping(keyNameKey, keyCode, keyGroupKey);
        AllKeyBindJSList.JoinKeySet.add(keyMapping);
        AllKeyBindJSList.RegisterKeyMappings.put(customName, keyMapping);
        return new KeyBindBuilder(keyMapping);
    }

    public KeyBindBuilder create(String keyNameKey, int keyCode, String keyGroupKey) {
        return create(keyNameKey,keyCode,keyGroupKey,true);
    }

    public KeyBindBuilder create(String keyNameKey, int keyCode, String keyGroupKey,boolean needConsumeClick) {
        KeyMapping keyMapping = new KeyMapping(keyNameKey, keyCode, keyGroupKey);
        AllKeyBindJSList.JoinKeySet.add(keyMapping);
        if (needConsumeClick) {
            AllKeyBindJSList.ConsumeClickSet.add(keyMapping);
        }
        return new KeyBindBuilder(keyMapping);
    }


    public static class KeyBindBuilder {
        private KeyMapping keyMapping;

        private KeyBindBuilder(KeyMapping keyMapping) {
            this.keyMapping = keyMapping;
        }

        public KeyBindBuilder addModifier(KeyModifier keyModifier) {
            keyMapping.keyModifierDefault = keyModifier;
            keyMapping.keyModifier = keyModifier;
            return this;
        }

        public KeyMapping getBuildKeyMapping() {
            return keyMapping;
        }
    }

}
