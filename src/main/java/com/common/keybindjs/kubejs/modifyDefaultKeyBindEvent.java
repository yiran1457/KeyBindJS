package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;


@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD,modid = KeyBindJS.MODID)
public class modifyDefaultKeyBindEvent extends EventJS {

    private static final HashMap<String,Integer> modifyListKey = new HashMap<>();
    private static final HashMap<String, KeyModifier> modifyListModifier = new HashMap<>();

    public modifyDefaultKeyBindEvent() {

    }

    public void modifyKey(String keyBindName,int keyCode) {
        modifyListKey.put(keyBindName,keyCode);
    }
    public void modifyModifier(String keyBindName,KeyModifier keyModifier) {
        modifyListModifier.put(keyBindName,keyModifier);
    }

    @SubscribeEvent
    public static void onClientStartup(final FMLClientSetupEvent event){
        KeyBindEvents.KEY_MODIFY.post(new modifyDefaultKeyBindEvent());
        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
            if(modifyListKey.containsKey(keyMapping.getName())) {
                keyMapping.defaultKey = InputConstants.Type.KEYSYM.getOrCreate(modifyListKey.get(keyMapping.getName()));
            }
            if(modifyListModifier.containsKey(keyMapping.getName())) {
                keyMapping.keyModifierDefault = modifyListModifier.get(keyMapping.getName());
                keyMapping.keyModifier = keyMapping.keyModifierDefault;
            }

        }
    }
}
