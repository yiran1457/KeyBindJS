package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
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
import java.util.HashMap;
import java.util.HashSet;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = KeyBindJS.MODID)
public class KeyBindModifyEvent extends EventJS {

    private static HashSet<String> removeList = new HashSet<>();
    private static HashMap<String, Integer> modifyListKey = new HashMap<>();
    private static HashMap<String, KeyModifier> modifyListModifier = new HashMap<>();
    private static HashMap<String, String> modifyListCategory = new HashMap<>();
    public static HashMap<String, KeyMapping> keyMappingListener = new HashMap<>();

    public KeyBindModifyEvent() {

    }

    public void addListener(String cusTomName,String keyName) {
        keyMappingListener.put(cusTomName, KeyBindUtil.INSTANCE.findKeyMappingInAllKeyMapping(keyName));
    }

    public void modifyKey(String keyBindName, int keyCode) {
        modifyListKey.put(keyBindName, keyCode);
    }

    public void modifyModifier(String keyBindName, KeyModifier keyModifier) {
        modifyListModifier.put(keyBindName, keyModifier);
    }

    public void modifyCategory(String keyBindName, String category) {
        modifyListCategory.put(keyBindName, category);
    }

    public void remove(String keyBindName) {
        removeList.add(keyBindName);
    }

    @SubscribeEvent
    public static void onClientStartup(final FMLClientSetupEvent event) {
        KeyBindEvents.KEY_MODIFY.post(new KeyBindModifyEvent());

        HashSet<String> CATEGORYSET = new HashSet<>();
        keyMappingListener.putAll(KeyBindEvent.keyMappings);
        ArrayList<KeyMapping> NewMappings = new ArrayList<>();
        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
            if (modifyListKey.containsKey(keyMapping.getName())) {
                keyMapping.defaultKey = InputConstants.Type.KEYSYM.getOrCreate(modifyListKey.get(keyMapping.getName()));
            }
            if (modifyListModifier.containsKey(keyMapping.getName())) {
                keyMapping.keyModifierDefault = modifyListModifier.get(keyMapping.getName());
                keyMapping.keyModifier = keyMapping.keyModifierDefault;
            }
            if (modifyListCategory.containsKey(keyMapping.getName())) {
                keyMapping.category = modifyListCategory.get(keyMapping.getName());
            }
            if (removeList.contains(keyMapping.getName())) {
                keyMapping.setKey(InputConstants.Type.KEYSYM.getOrCreate(-1));
            } else {
                KEY_NAME = KEY_NAME + keyMapping.getName() + ",";
                if (!CATEGORYSET.contains(keyMapping.getCategory())) {
                    CATEGORYSET.add(keyMapping.getCategory());
                    CATEGORY_NAME = CATEGORY_NAME + keyMapping.getCategory() + ",";
                }
                NewMappings.add(keyMapping);
            }
        }
        Minecraft.getInstance().options.keyMappings = NewMappings.toArray(new KeyMapping[0]);
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
