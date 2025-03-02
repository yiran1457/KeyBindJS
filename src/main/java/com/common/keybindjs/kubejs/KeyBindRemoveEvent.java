package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.HashSet;

@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD,modid = KeyBindJS.MODID)
public class KeyBindRemoveEvent extends EventJS {
    private static HashSet<String> removeList = new HashSet<>();

    public KeyBindRemoveEvent() {
    }

    public void remove(String keyBindName){
        removeList.add(keyBindName);
    }

    @SubscribeEvent
    public static void onClientStartup(final FMLClientSetupEvent event) {
        KeyBindEvents.KEY_REMOVE.post(new KeyBindRemoveEvent());
        ArrayList<KeyMapping> NewMappings = new ArrayList<>();
        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
            if (removeList.contains(keyMapping.getName())) {
                keyMapping.setKey(InputConstants.Type.KEYSYM.getOrCreate(-1));
            } else {
                NewMappings.add(keyMapping);
            }
        }
        Minecraft.getInstance().options.keyMappings = NewMappings.toArray(new KeyMapping[0]);
    }


}
