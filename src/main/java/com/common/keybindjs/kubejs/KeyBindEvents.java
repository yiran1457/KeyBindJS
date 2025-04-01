package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;

public interface KeyBindEvents {
    EventGroup GROUP = EventGroup.of("KeyBindEvents");

    EventHandler KEY_BINDING = GROUP.startup("register", () -> KeyBindEvent.class);
    EventHandler KEY_MODIFY = GROUP.startup("modify", () -> KeyBindModifyEvent.class);

    EventHandler KEY_PRESS = GROUP.client("keyPress", () -> KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler KEY_RELEASE = GROUP.client("keyRelease", () -> KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler FIRST_KEY_PRESS = GROUP.client("firstKeyPress", () -> KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler KEY_PRESS_GUI = GROUP.client("keyPressInGui", () -> KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler KEY_RELEASE_GUI = GROUP.client("keyReleaseInGui", () -> KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler FIRST_KEY_PRESS_GUI = GROUP.client("firstKeyPressInGui", () -> KeyPressedEvent.class).extra(Extra.STRING);
}
