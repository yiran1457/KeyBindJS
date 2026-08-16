package com.common.keybindjs.core.mixins;

import com.common.keybindjs.kubejs.KeyBindEvents;
import com.common.keybindjs.kubejs.KeyBindJSKeyEvent;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {
    @Inject(method = "consumeClick", at = @At(value = "RETURN"))
    private void consumeClick(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            KeyMapping keyMapping = (KeyMapping) (Object) this;
            KeyBindEvents.CONSUME_CLICK.post(new KeyBindJSKeyEvent(keyMapping), keyMapping.getName());
        }
    }
}
