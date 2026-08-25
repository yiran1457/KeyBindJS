package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;

public interface KeyBindEvents {
    EventGroup GROUP = EventGroup.of("KeyBindJSEvents");

    EventHandler KEY_BINDING = GROUP.client("register", () -> KeyBindJSRegisterEvent.class);
    EventHandler KEY_MODIFY = GROUP.client("modify", () -> KeyBindModifyEvent.class);

    TargetedEventHandler<String> CONSUME_CLICK = GROUP.client("consumeClick", () -> KeyBindJSKeyEvent.class).supportsTarget(EventTargetType.STRING);

    TargetedEventHandler<String> KEY_PRESS = GROUP.client("keyPress", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_RELEASE = GROUP.client("keyRelease", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> FIRST_KEY_PRESS = GROUP.client("firstKeyPress", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_PRESS_IN_GUI = GROUP.client("keyPressInGui", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_RELEASE_IN_GUI = GROUP.client("keyReleaseInGui", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> FIRST_KEY_PRESS_IN_GUI = GROUP.client("firstKeyPressInGui", () -> KeyBindJSKeyEvent.class).requiredTarget(EventTargetType.STRING);
}
