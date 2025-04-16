package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;

public interface KeyBindEvents {
    EventGroup GROUP = EventGroup.of("KeyBindJSEvents");
    EventHandler KEY_BINDING = GROUP.client("register", () -> KeyBindEvent.class);
    EventHandler KEY_MODIFY = GROUP.client("modify", () -> KeyBindModifyEvent.class);

    TargetedEventHandler<String> KEY_PRESS = GROUP.client("keyPress", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_RELEASE = GROUP.client("keyRelease", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
    TargetedEventHandler<String> FIRST_KEY_PRESS = GROUP.client("firstKeyPress", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_PRESS_GUI = GROUP.client("keyPressInGui", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
    TargetedEventHandler<String> KEY_RELEASE_GUI = GROUP.client("keyReleaseInGui", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
    TargetedEventHandler<String> FIRST_KEY_PRESS_GUI = GROUP.client("firstKeyPressInGui", () -> KeyPressedEvent.class).supportsTarget(EventTargetType.STRING);
}
