package com.common.keybindjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;

public interface KeyBindEvents {
    EventGroup GROUP = EventGroup.of("KeyBindEvents");
    EventHandler KEY_BINDING = GROUP.startup("register",()-> KeyBindEvent.class);
    EventHandler KEY_MODIFY = GROUP.startup("modifyDefaultKey",()->modifyDefaultKeyBindEvent.class);
    EventHandler KEY_REMOVE = GROUP.startup("remove",()-> KeyBindRemoveEvent.class);
    EventHandler KEY_PRESS = GROUP.client("keyPress",()->KeyPressedEvent.class).extra(Extra.STRING);
    EventHandler KEY_PRESS_GUI = GROUP.client("keyPressInGui",()->KeyPressInGuiEvent.class).extra(Extra.STRING);
}
