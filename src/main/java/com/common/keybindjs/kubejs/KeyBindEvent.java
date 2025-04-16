package com.common.keybindjs.kubejs;

import com.common.keybindjs.Keybindjs;
import dev.latvian.mods.kubejs.client.ClientKubeEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyModifier;

@EventBusSubscriber(modid = Keybindjs.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class KeyBindEvent implements ClientKubeEvent {
    @HideFromJS
    public KeyBindEvent() {
    }

    public KeyBindBuilder create(String customName, String keyNameKey, int keyCode, String keyGroupKey) {
        KeyMapping keyMapping = new KeyMapping(keyNameKey, keyCode, keyGroupKey);
        AllKeyBindJSList.RegisterKeyMappings.put(customName, keyMapping);
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

    @SubscribeEvent
    @HideFromJS
    public static void registerKeys(final RegisterKeyMappingsEvent event) {
        KeyBindEvents.KEY_BINDING.post(new KeyBindEvent());
        AllKeyBindJSList.RegisterKeyMappings.values().forEach(event::register);
    }
}
