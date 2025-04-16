package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import dev.latvian.mods.kubejs.client.ClientInitEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = KeyBindJS.MODID)
public class KeyBindEvent extends ClientInitEventJS {

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
