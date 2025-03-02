package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE,modid = KeyBindJS.MODID)
public class KeyPressInGuiEvent extends EventJS {

    private final String customName;

    public KeyPressInGuiEvent(String customName) {
        this.customName = customName;
    }

    @SubscribeEvent
    @HideFromJS
    public static void keyPressed(ScreenEvent.KeyPressed.Pre event) {
        KeyBindEvent.keyMappings.forEach((k, v)->{
            if(isDown(event,v))
                KeyBindEvents.KEY_PRESS_GUI.post(new KeyPressInGuiEvent(k),k);
        });
    }

    private static Boolean isDown(ScreenEvent.KeyPressed.Pre event, KeyMapping keyMapping){
        return keyMapping.getKey().getValue() == event.getKeyCode() &&
                keyMapping.getKeyModifier().compareTo(keyMapping.getKeyModifier()) == event.getModifiers();
    }

    public String getCustomName() {
        return customName;
    }
}
