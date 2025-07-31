package com.common.keybindjs.core.mixins;

import com.common.keybindjs.kubejs.neo.NewKeyBindEventJS;
import com.common.keybindjs.kubejs.neo.NewKeybindEvents;
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
            NewKeybindEvents.NEW_CONSUME_CLICK.post(new NewKeyBindEventJS(keyMapping), keyMapping.getName());
        }
    }
}
