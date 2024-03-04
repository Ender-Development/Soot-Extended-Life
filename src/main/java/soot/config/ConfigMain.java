package soot.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import soot.Soot;

@Mod.EventBusSubscriber(modid = Soot.MODID)
public class ConfigMain {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Soot.MODID)) {
            ConfigManager.sync(Soot.MODID, Config.Type.INSTANCE);
        }
    }
}

