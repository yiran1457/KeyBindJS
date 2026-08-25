package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.client.ClientKubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.settings.KeyModifier;

@EventBusSubscriber(value = Dist.CLIENT, modid = KeyBindJS.MODID)
public class KeyBindModifyEvent implements ClientKubeEvent {
    public KeyBindModifyEvent() {
    }

    @Info("隐藏按键，隐藏后将只使用默认按键而不读取options文件")
    public void addHideKey(KeyMapping keyMapping) {
        AllKeyBindJSList.HideKeySet.add(keyMapping);
    }

    @Info("修改按键的默认按键")
    public void modifyKey(KeyMapping keyMapping, int keyCode) {
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(keyCode);
        keyMapping.setKeyModifierAndCode(keyMapping.getKeyModifier(), key);
        keyMapping.defaultKey = key;
    }

    @Info("修改按键的默认修饰键")
    public void modifyModifier(KeyMapping keyMapping, KeyModifier keyModifier) {
        keyMapping.setKeyModifierAndCode(keyModifier, keyMapping.getKey());
        keyMapping.keyModifierDefault = keyModifier;
    }

    @Info("修改按键的分组")
    public void modifyCategory(KeyMapping keyMapping, String category) {
        keyMapping.category = category;
    }

    @Info("移除按键绑定")
    public void remove(KeyMapping keyMapping) {
        AllKeyBindJSList.RemoveKeySet.add(keyMapping);
        keyMapping.setKey(InputConstants.UNKNOWN);
    }

    @Info("将按键绑定加入配置屏幕")
    public void join(KeyMapping keyMapping) {
        AllKeyBindJSList.JoinKeySet.add(keyMapping);
    }

    @SubscribeEvent
    @HideFromJS
    public static void onClientStartup(final FMLClientSetupEvent event) {
        KeyBindEvents.KEY_MODIFY.post(new KeyBindModifyEvent());

        // 修改后的按键
        ObjectArrayList<KeyMapping> newMappings = new ObjectArrayList<>();

        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
            if (AllKeyBindJSList.RemoveKeySet.contains(keyMapping)) {
                // 隐藏并把按键修改为未指定
                keyMapping.setKey(InputConstants.UNKNOWN);
            } else if (!AllKeyBindJSList.HideKeySet.contains(keyMapping)) {
                newMappings.add(keyMapping);
            }
        }

        // 确保新创建/加入的按键在列表中，且不重复
        for (KeyMapping keyMapping : AllKeyBindJSList.JoinKeySet) {
            if (!newMappings.contains(keyMapping)) {
                newMappings.add(keyMapping);
            }
        }
        AllKeyBindJSList.RemoveKeySet.clear();
        AllKeyBindJSList.RemoveKeySet = null;
        AllKeyBindJSList.HideKeySet.clear();
        AllKeyBindJSList.HideKeySet = null;
        AllKeyBindJSList.JoinKeySet.clear();
        AllKeyBindJSList.JoinKeySet = null;
        // 应用修改后的 keymapping
        Minecraft.getInstance().options.keyMappings = newMappings.toArray(new KeyMapping[0]);
    }
}
