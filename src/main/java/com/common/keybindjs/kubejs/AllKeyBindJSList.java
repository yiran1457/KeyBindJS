package com.common.keybindjs.kubejs;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.KeyMapping;

public class AllKeyBindJSList {
    public static Object2ObjectOpenHashMap<String, KeyMapping> RegisterKeyMappings = new Object2ObjectOpenHashMap<>();

    public static ObjectOpenHashSet<KeyMapping> ConsumeClickSet = new ObjectOpenHashSet<>();

    public static ObjectOpenHashSet<KeyMapping> RemoveKeySet = new ObjectOpenHashSet<>();
    public static ObjectOpenHashSet<KeyMapping> HideKeySet = new ObjectOpenHashSet<>();
    public static ObjectOpenHashSet<KeyMapping> JoinKeySet = new ObjectOpenHashSet<>();
    public static ObjectOpenHashSet<KeyMapping> NewKeySet = new ObjectOpenHashSet<>();
}
