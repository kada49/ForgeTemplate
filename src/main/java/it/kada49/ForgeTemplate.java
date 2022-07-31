package it.kada49;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = "ForgeTemplate", modid = "forgetemplate", version = "1.0")

public class ForgeTemplate {

    public Logger LOGGER = LogManager.getLogger("ForgeTemplate");

    @EventHandler
    public void init (FMLInitializationEvent event) {
        LOGGER.info("Hello, World!");
        MinecraftForge.EVENT_BUS.register(this);
    }
}