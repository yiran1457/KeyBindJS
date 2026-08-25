package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.client.ClientKubeEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyModifier;

@EventBusSubscriber(modid = KeyBindJS.MODID, value = Dist.CLIENT)
public class KeyBindJSRegisterEvent implements ClientKubeEvent {
    public static String DEF_GROUP = "keybindjs:keybindjs";

    @HideFromJS
    public KeyBindJSRegisterEvent() {
    }

    public KeyBindBuilder create(String keyNameKey, int keyCode) {
        return create(keyNameKey, keyCode, DEF_GROUP);
    }

    public KeyBindBuilder create(String keyNameKey, int keyCode, String keyGroup) {
        return create(keyNameKey, keyCode, keyGroup, true);
    }

    public KeyBindBuilder create(String keyNameKey, int keyCode, String keyGroup, boolean needConsumeClick) {
        return new KeyBindBuilder(createMapping(keyNameKey, keyCode, keyGroup, needConsumeClick, false));
    }

    public KeyBindBuilder createMouse(String keyNameKey, int keyCode) {
        return createMouse(keyNameKey, keyCode, DEF_GROUP);
    }

    public KeyBindBuilder createMouse(String keyNameKey, int keyCode, String keyGroup) {
        return createMouse(keyNameKey, keyCode, keyGroup, true);
    }

    public KeyBindBuilder createMouse(String keyNameKey, int keyCode, String keyGroup, boolean needConsumeClick) {
        return new KeyBindBuilder(createMapping(keyNameKey, keyCode, keyGroup, needConsumeClick, true));
    }

    private KeyMapping createMapping(String keyNameKey, int keyCode, String keyGroup, boolean needConsumeClick, boolean isMouse) {
        KeyMapping keyMapping = new KeyMapping(keyNameKey, isMouse ? InputConstants.Type.MOUSE : InputConstants.Type.KEYSYM, keyCode, keyGroup);
        AllKeyBindJSList.NewKeySet.add(keyMapping);
        AllKeyBindJSList.JoinKeySet.add(keyMapping);
        if (needConsumeClick) {
            AllKeyBindJSList.ConsumeClickSet.add(keyMapping);
        }
        return keyMapping;
    }

    public static class KeyBindBuilder {
        private final KeyMapping keyMapping;

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
        KeyBindEvents.KEY_BINDING.post(new KeyBindJSRegisterEvent());
        for (KeyMapping keyMapping : AllKeyBindJSList.NewKeySet) {
            event.register(keyMapping);
        }
        AllKeyBindJSList.NewKeySet.clear();
        AllKeyBindJSList.NewKeySet = null;
    }
}
