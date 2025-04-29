package com.common.keybindjs.kubejs;

import com.common.keybindjs.oi.KubeJSDebugScreen;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyBindUtil {
    public static KeyBindUtil INSTANCE = new KeyBindUtil();

    public KeyMapping[] getAllKeyMappings() {
        return  Minecraft.getInstance().options.keyMappings;
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
        return AllKeyBindJSList.RegisterKeyMappings.get(customName);
    }

    public KeyMapping findKeyMappingInAllKeyMapping(String keyName) {
        return KeyMapping.ALL.get(keyName);
    }

    @Info("请在事件里面使用，在外面使用可能出现问题，此screen仅为方便使用指令来调试kjs")
    public void openKubeJSDebugScreen() {
        Minecraft.getInstance().setScreen(new KubeJSDebugScreen());
    }
    private boolean isDown(KeyMapping Mapping) {
        return Mapping.isDown();
    }
}
