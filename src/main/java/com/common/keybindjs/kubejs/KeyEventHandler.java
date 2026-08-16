package com.common.keybindjs.kubejs;

import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import dev.latvian.mods.kubejs.script.ScriptType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KeyEventHandler {

    public static List<KeyMapping> FirstPressList = new ObjectArrayList<>(10);
    public static List<KeyMapping> FirstDownList = new ObjectArrayList<>(10);

    @SafeVarargs
    public static Set<String> findClientExtraIds(TargetedEventHandler<String>... handlers) {
        return Arrays.stream(handlers)
                .map(handler -> handler.findUniqueExtraIds(ScriptType.CLIENT))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Pre event) {
        for (String extraId : findClientExtraIds(KeyBindEvents.KEY_PRESS, KeyBindEvents.KEY_RELEASE, KeyBindEvents.FIRST_KEY_PRESS)) {
            KeyMapping mapping = KeyBindJSPlugin.wrapperKeyMapping(extraId);
            if (mapping == null) continue;

            if (mapping.isDown()) {
                if (!FirstDownList.contains(mapping)) {
                    FirstDownList.add(mapping);
                    KeyBindEvents.FIRST_KEY_PRESS.post(new KeyBindJSKeyEvent(mapping), extraId);
                }
                KeyBindEvents.KEY_PRESS.post(new KeyBindJSKeyEvent(mapping), extraId);
            } else if (FirstDownList.contains(mapping)) {
                FirstDownList.remove(mapping);
                KeyBindEvents.KEY_RELEASE.post(new KeyBindJSKeyEvent(mapping), extraId);
            }
        }

        for (KeyMapping keyMapping : AllKeyBindJSList.ConsumeClickSet) {
            keyMapping.consumeClick();
        }
    }

    @SubscribeEvent
    public static void onScreenKeyPress(ScreenEvent.KeyPressed.Pre event) {
        InputConstants.Key inputKey = InputConstants.getKey(event.getKeyEvent());
        for (String extraId : findClientExtraIds(KeyBindEvents.KEY_PRESS_IN_GUI, KeyBindEvents.FIRST_KEY_PRESS_IN_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.wrapperKeyMapping(extraId);
            if (mapping != null && mapping.isActiveAndMatches(inputKey)) {
                if (!FirstPressList.contains(mapping)) {
                    FirstPressList.add(mapping);
                    KeyBindEvents.FIRST_KEY_PRESS_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
                }
                KeyBindEvents.KEY_PRESS_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenMouseButtonPress(ScreenEvent.MouseButtonPressed.Pre event) {
        InputConstants.Key inputKey = InputConstants.Type.MOUSE.getOrCreate(event.getButton());
        for (String extraId : findClientExtraIds(KeyBindEvents.KEY_PRESS_IN_GUI, KeyBindEvents.FIRST_KEY_PRESS_IN_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.wrapperKeyMapping(extraId);
            if (mapping != null && mapping.isActiveAndMatches(inputKey)) {
                if (!FirstPressList.contains(mapping)) {
                    FirstPressList.add(mapping);
                    KeyBindEvents.FIRST_KEY_PRESS_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
                }
                KeyBindEvents.KEY_PRESS_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenKeyRelease(ScreenEvent.KeyReleased.Pre event) {
        InputConstants.Key inputKey = InputConstants.getKey(event.getKeyEvent());
        for (String extraId : findClientExtraIds(KeyBindEvents.KEY_RELEASE_IN_GUI, KeyBindEvents.FIRST_KEY_PRESS_IN_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.wrapperKeyMapping(extraId);
            if (mapping != null && mapping.isActiveAndMatches(inputKey)) {
                FirstPressList.remove(mapping);
                KeyBindEvents.KEY_RELEASE_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenMouseButtonRelease(ScreenEvent.MouseButtonReleased.Pre event) {
        InputConstants.Key inputKey = InputConstants.Type.MOUSE.getOrCreate(event.getButton());
        for (String extraId : findClientExtraIds(KeyBindEvents.KEY_RELEASE_IN_GUI, KeyBindEvents.FIRST_KEY_PRESS_IN_GUI)) {
            KeyMapping mapping = KeyBindJSPlugin.wrapperKeyMapping(extraId);
            if (mapping != null && mapping.isActiveAndMatches(inputKey)) {
                FirstPressList.remove(mapping);
                KeyBindEvents.KEY_RELEASE_IN_GUI.post(new KeyBindJSKeyEvent(mapping), extraId);
            }
        }
    }
}
