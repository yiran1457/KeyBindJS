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
    public void addHideKey(String keyBindName) {
        AllKeyBindJSList.HideKeySet.add(keyBindName);
    }

    @Info("给已有按键添加customName用于监听")
    public void addListener(String customName, String keyName) {
        AllKeyBindJSList.RegisterKeyMappings.put(customName, KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping(keyName));
    }

    @Info("修改按键的默认按键")
    public void modifyKey(String keyBindName, int keyCode) {
        KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping(keyBindName).defaultKey = InputConstants.Type.KEYSYM.getOrCreate(keyCode);
    }

    @Info("修改按键的默认修饰键")
    public void modifyModifier(String keyBindName, KeyModifier keyModifier) {
        KeyMapping keyMapping = KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping(keyBindName);
        keyMapping.keyModifierDefault = keyModifier;
        keyMapping.keyModifier = keyModifier;
    }

    @Info("修改按键的分组")
    public void modifyCategory(String keyBindName, String category) {
        KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping(keyBindName).category = category;
    }

    @Info("移除按键绑定")
    public void remove(String keyBindName) {
        AllKeyBindJSList.RemoveKeySet.add(keyBindName);
    }

    @SubscribeEvent
    @HideFromJS
    public static void onClientStartup(final FMLClientSetupEvent event) {
        KeyBindEvents.KEY_MODIFY.post(new KeyBindModifyEvent());

        //已经添加的按键组的set
        HashSet<String> CATEGORYSET = new HashSet<>();

        //修改后的按键
        ArrayList<KeyMapping> NewMappings = new ArrayList<>();

        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {

            if (AllKeyBindJSList.RemoveKeySet.contains(keyMapping.getName())) {
                //隐藏并把按键修改为未指定
                keyMapping.setKey(InputConstants.Type.KEYSYM.getOrCreate(-1));
            } else if (!AllKeyBindJSList.HideKeySet.contains(keyMapping.getName())) {
                //将按键添加入vsc提示文件
                KEY_NAME = KEY_NAME + keyMapping.getName() + ",";
                if (!CATEGORYSET.contains(keyMapping.getCategory())) {
                    CATEGORYSET.add(keyMapping.getCategory());
                    //将按键组添加入vsc提示文件
                    CATEGORY_NAME = CATEGORY_NAME + keyMapping.getCategory() + ",";
                }
                NewMappings.add(keyMapping);
            }
        }
        //应用修改后的keymapping
        Minecraft.getInstance().options.keyMappings = NewMappings.toArray(new KeyMapping[0]);

        //生成vsc提示文件
        KEY_NAME = KEY_NAME.substring(0, KEY_NAME.length() - 1);
        CATEGORY_NAME = CATEGORY_NAME.substring(0, CATEGORY_NAME.length() - 1);
        File folder = VSCODE_PATH.toFile();
        if (!folder.exists()) {
            boolean isCreated = folder.mkdirs(); // 创建多级目录
            if (!folder.mkdirs()) return;
        }

        File file = new File(String.valueOf(VSCODE_PATH), FILE_NAME);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("\n" +
                    "{\n" +
                    "    \"keyName\": {\n" +
                    "        \"prefix\": [\n" +
                    "            \"@key_name\"\n" +
                    "        ],\n" +
                    "        \"body\": [\n" +
                    "            \"\\\"${1|" + KEY_NAME + "|}\\\"$0\"\n" +
                    "        ],\n" +
                    "        \"description\": \"All key remaining names\"\n" +
                    "    },\n" +
                    "    \"CategoryName\": {\n" +
                    "        \"prefix\": [\n" +
                    "            \"@category_name\"\n" +
                    "        ],\n" +
                    "        \"body\": [\n" +
                    "            \"\\\"${1|" + CATEGORY_NAME + "|}\\\"$0\"\n" +
                    "        ],\n" +
                    "        \"description\": \"All category remaining names\"\n" +
                    "    }\n" +
                    "}\n"
            );
        } catch (IOException e) {
            ConsoleJS.STARTUP.warn("文件写入失败：" + e.getMessage());
        }
    }

    private static final Path VSCODE_PATH = Platform.getGameFolder().resolve(".vscode");

    private static final String FILE_NAME = "key.code-snippets";

    private static String KEY_NAME = "";

    private static String CATEGORY_NAME = "";
}
