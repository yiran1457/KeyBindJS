package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.HashMap;


@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD,modid = KeyBindJS.MODID)
public class KeyBindEvent extends EventJS {

    @HideFromJS
    public static HashMap<String,KeyMapping> keyMappings = new HashMap<>();

    @HideFromJS
    public KeyBindEvent() {
    }

    public KeyBindBuilder create(String customName, String keyNameKey, int keyCode, String keyGroupKey) {
        keyMappings.put(customName,new KeyMapping(keyNameKey,keyCode,keyGroupKey));
        return new KeyBindBuilder(customName);
    }

    @HideFromJS
    public static class KeyBindBuilder {
        private String HashMapKey;
        private KeyBindBuilder(String customName) {
            HashMapKey = customName;
        }
        public void addModifier(KeyModifier keyModifier) throws NoSuchFieldException, IllegalAccessException {
            keyMappings.get(HashMapKey).keyModifierDefault = keyModifier;
            keyMappings.get(HashMapKey).keyModifier = keyModifier;
        }
    }

    @HideFromJS
    public HashMap<String, KeyMapping> getKeyMappings() {
        return keyMappings;
    }


    @SubscribeEvent
    @HideFromJS
    public static void registerKeys(final RegisterKeyMappingsEvent event) {
        KeyBindEvents.KEY_BINDING.post(new KeyBindEvent());
        KeyBindEvent.keyMappings.values().forEach(event::register);
    }

}
