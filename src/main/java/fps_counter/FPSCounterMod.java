package fps_counter;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import fps_counter.command.ModCommands;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = FPSCounterMod.MODID, name = FPSCounterMod.NAME, version = FPSCounterMod.VERSION)
public class FPSCounterMod {
	public static final String MODID = "fps_counter";
	public static final String NAME = "FPS Counter Mod";
	public static final String VERSION = "1.0.1";

	public static Logger log;
	@SidedProxy(clientSide = "fps_counter.ClientProxy", serverSide = "fps_counter.ServerProxy")
	public static ServerProxy proxy;
	@SidedProxy(clientSide = "fps_counter.ClientNetworkHandler", serverSide = "fps_counter.ServerNetworkHandler")
	public static ServerNetworkHandler network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException, IllegalAccessException {
		log = event.getModLog();
		proxy.load();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		proxy.setServer(event.getServer());
		event.registerServerCommand(new ModCommands());
	}
}
