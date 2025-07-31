package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.client.ClientInitEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = KeyBindJS.MODID)
public class KeyBindModifyEvent extends ClientInitEventJS {
    public KeyBindModifyEvent() {

    }

    @Info("隐藏按键，隐藏后将只使用默认按键而不读取options文件")
    public void addHideKey(KeyMapping keyMapping) {
        AllKeyBindJSList.HideKeySet.add(keyMapping);
    }

    @Deprecated
    @Info("给已有按键添加customName用于监听")
    public void addListener(String customName, KeyMapping keyMapping) {
        AllKeyBindJSList.RegisterKeyMappings.put(customName,keyMapping);
    }

    @Info("修改按键的默认按键")
    public void modifyKey(KeyMapping keyMapping, int keyCode) {
        keyMapping.defaultKey = InputConstants.Type.KEYSYM.getOrCreate(keyCode);
    }

    @Info("修改按键的默认修饰键")
    public void modifyModifier(KeyMapping keyMapping, KeyModifier keyModifier) {
        keyMapping.keyModifierDefault = keyModifier;
        keyMapping.keyModifier = keyModifier;
    }

    @Info("修改按键的分组")
    public void modifyCategory(KeyMapping keyMapping, String category) {
        keyMapping.category = category;
    }

    @Info("移除按键绑定")
    public void remove(KeyMapping keyMapping) {
        AllKeyBindJSList.RemoveKeySet.add(keyMapping);
        keyMapping.setKey(InputConstants.Type.KEYSYM.getOrCreate(-1));
    }

    @Info("将按键绑定加入配置屏幕")
    public void join(KeyMapping keyMapping){
        AllKeyBindJSList.JoinKeySet.add(keyMapping);
    }

    @SubscribeEvent
    @HideFromJS
    public static void onClientStartup(final FMLClientSetupEvent event) {
        KeyBindEvents.KEY_BINDING.post(new KeyBindEvent());
        KeyBindEvents.KEY_MODIFY.post(new KeyBindModifyEvent());

        //修改后的按键
        ArrayList<KeyMapping> NewMappings = new ArrayList<>();

        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {

            if (AllKeyBindJSList.RemoveKeySet.contains(keyMapping)) {
                //隐藏并把按键修改为未指定
                //keyMapping.setKey(InputConstants.Type.KEYSYM.getOrCreate(-1));
            } else if (!AllKeyBindJSList.HideKeySet.contains(keyMapping)) {
                NewMappings.add(keyMapping);
            }
        }
        NewMappings.addAll(AllKeyBindJSList.JoinKeySet);

        //应用修改后的keymapping
        Minecraft.getInstance().options.keyMappings = NewMappings.toArray(new KeyMapping[0]);


    }
}
