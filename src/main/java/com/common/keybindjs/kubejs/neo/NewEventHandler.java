package com.common.keybindjs.kubejs.neo;

import com.common.keybindjs.kubejs.AllKeyBindJSList;
import com.common.keybindjs.kubejs.KeyBindJSPlugin;
import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.stream.Collectors;

public class NewEventHandler {

    public static List<KeyMapping> FirstPressList = new ArrayList<>(10);
    public static List<KeyMapping> FirstDownList = new ArrayList<>(10);

    public static Set<Object> findClientExtraIds(EventHandler... handlers) {
        return Arrays.stream(handlers)
                .map(handler -> handler.findUniqueExtraIds(ScriptType.CLIENT))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;

        for (Object extraId : findClientExtraIds(NewKeybindEvents.NEW_IS_DOWN, NewKeybindEvents.NEW_IS_Release, NewKeybindEvents.NEW_IS_FIRST_DOWN)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if(mapping == null) continue;
            if (mapping.isDown()) {
                if(!FirstDownList.contains(mapping)) {
                    FirstDownList.add(mapping);
                    NewKeybindEvents.NEW_IS_FIRST_DOWN.post(new NewKeyBindEventJS(mapping), extraId);
                }
                NewKeybindEvents.NEW_IS_DOWN.post(new NewKeyBindEventJS(mapping), extraId);
            }else if(FirstDownList.contains(mapping)){
                FirstDownList.remove(mapping);
                NewKeybindEvents.NEW_IS_Release.post(new NewKeyBindEventJS(mapping), extraId);
            }

        }/*
        for (Object extraId : NewKeybindEvents.NEW_IS_DOWN.findUniqueExtraIds(ScriptType.CLIENT)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if (mapping != null && mapping.isDown()) {
                NewKeybindEvents.NEW_IS_DOWN.post(new NewKeyBindEventJS(mapping), extraId);
            }
        }*/
        for (KeyMapping keyMapping : AllKeyBindJSList.ConsumeClickSet) {
            keyMapping.consumeClick();
        }
    }

    @SubscribeEvent
    public static void onScreenKeyPress(ScreenEvent.KeyPressed.Pre event) {
        for (Object extraId : findClientExtraIds(NewKeybindEvents.NEW_PRESS_GUI, NewKeybindEvents.NEW_FIRST_PRESS_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if (mapping != null && mapping.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
                if (!FirstPressList.contains(mapping)) {
                    FirstPressList.add(mapping);
                    NewKeybindEvents.NEW_FIRST_PRESS_GUI.post(new NewKeyBindEventJS(mapping), extraId);
                }
                NewKeybindEvents.NEW_PRESS_GUI.post(new NewKeyBindEventJS(mapping), extraId);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenMouseButtonPress(ScreenEvent.MouseButtonPressed.Pre event) {
        for (Object extraId : findClientExtraIds(NewKeybindEvents.NEW_PRESS_GUI, NewKeybindEvents.NEW_FIRST_PRESS_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if (mapping != null && mapping.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(event.getButton()))) {
                if (!FirstPressList.contains(mapping)) {
                    FirstPressList.add(mapping);
                    NewKeybindEvents.NEW_FIRST_PRESS_GUI.post(new NewKeyBindEventJS(mapping), extraId);
                }
                NewKeybindEvents.NEW_PRESS_GUI.post(new NewKeyBindEventJS(mapping), extraId);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenKeyRelease(ScreenEvent.KeyReleased.Pre event) {
        for (Object extraId :findClientExtraIds(NewKeybindEvents.NEW_RELEASE_GUI, NewKeybindEvents.NEW_FIRST_PRESS_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if (mapping != null && mapping.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
                FirstPressList.remove(mapping);
                NewKeybindEvents.NEW_RELEASE_GUI.post(new NewKeyBindEventJS(mapping), extraId);
            }
        }
    }


    @SubscribeEvent
    public static void onScreenMouseButtonRelease(ScreenEvent.MouseButtonReleased.Pre event) {
        for (Object extraId : findClientExtraIds(NewKeybindEvents.NEW_RELEASE_GUI, NewKeybindEvents.NEW_FIRST_PRESS_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.keyOf(extraId);
            if (mapping != null && mapping.isActiveAndMatches(InputConstants.Type.MOUSE.getOrCreate(event.getButton()))) {
                FirstPressList.remove(mapping);
                NewKeybindEvents.NEW_RELEASE_GUI.post(new NewKeyBindEventJS(mapping), extraId);
            }
        }
    }
}
