package com.common.keybindjs.kubejs.neo;

import com.common.keybindjs.kubejs.AllKeyBindJSList;
import com.common.keybindjs.kubejs.KeyBindEvents;
import com.common.keybindjs.kubejs.KeyBindJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;
import net.minecraft.client.KeyMapping;

public interface NewKeybindEvents {/*
    Extra EXTRA_KEYMAPPING = new Extra()
            .transformer(NewKeybindEvents::transformer)
            .toString((o) -> ((KeyMapping)o).getName())
            .describeType((context) -> context.javaType(KeyMapping.class));*/
    Extra EXTRA_KEYMAPPING = Extra.STRING.copy()
            .describeType(cxt->cxt.javaType(KeyMapping.class));
    Extra REQUIRED_KEYMAPPING = Extra.REQUIRES_STRING.copy()
            .describeType(cxt->cxt.javaType(KeyMapping.class));

    EventGroup NEW_GROUP = EventGroup.of("NewKeyBindEvents");
    EventHandler NEW_CONSUME_CLICK = NEW_GROUP.client("consumeClick", () -> NewKeyBindEventJS.class).extra(EXTRA_KEYMAPPING);
    EventHandler NEW_IS_DOWN = NEW_GROUP.client("isKeyDown", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
    EventHandler NEW_IS_Release = NEW_GROUP.client("isRelease", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
    EventHandler NEW_IS_FIRST_DOWN = NEW_GROUP.client("isFirstKeyDown", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
    EventHandler NEW_FIRST_PRESS_GUI = NEW_GROUP.client("isFirstPressInGui", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
    EventHandler NEW_PRESS_GUI = NEW_GROUP.client("isPressInGui", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
    EventHandler NEW_RELEASE_GUI = NEW_GROUP.client("isReleaseInGui", () -> NewKeyBindEventJS.class).extra(REQUIRED_KEYMAPPING);
}