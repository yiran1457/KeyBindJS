package com.common.keybindjs.kubejs;

import com.common.keybindjs.KeyBindJS;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE,modid = KeyBindJS.MODID)
public class KeyPressedEvent extends ClientEventJS {

    private final String customName;

    public KeyPressedEvent(String customName) {
        this.customName = customName;
    }

    @SubscribeEvent
    @HideFromJS
    public static void keyPressed(TickEvent.ClientTickEvent event) {
        KeyBindEvent.keyMappings.forEach((k, v)->{
            if(v.consumeClick())
                KeyBindEvents.KEY_PRESS.post(new KeyPressedEvent(k),k);
        });
    }


    public String getCustomName() {
        return customName;
    }
}
