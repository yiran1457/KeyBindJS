package com.common.keybindjs;

import com.common.keybindjs.compat.probejs.KeyBindProbePlugin;
import com.common.keybindjs.kubejs.neo.NewEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import zzzank.probejs.ProbeJS;
import zzzank.probejs.plugin.ProbeJSPlugins;

@Mod(KeyBindJS.MODID)
public class KeyBindJS {
    public static final String MODID = "keybindjs";
    public KeyBindJS(){
        if(ModList.get().isLoaded(ProbeJS.MOD_ID)){
            ProbeJSPlugins.register(new KeyBindProbePlugin());
        }
        if(FMLEnvironment.dist == Dist.CLIENT){
            MinecraftForge.EVENT_BUS.register(NewEventHandler.class);
        }
    }
}
