package dev.asbyth.template;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "forgetemplate", name = "Forge Template", version = "1.0")
public class ForgeTemplate {

    Logger LOGGER = LogManager.getLogger("forgetemplate");

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        // $USER = The username of the currently logged in user.
        // Simply prints out Hello, $USER.
        LOGGER.info("Hello, " + Minecraft.getMinecraft().getSession().getUsername() + "!");
    }
}