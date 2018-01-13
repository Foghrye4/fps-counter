package fps_counter;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy {

	private MinecraftServer server;

	public void load() {
		MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
	}

	public File getMinecraftDir() {
		return new File(".");
	}

	public void preInit() {
	}

	public void setServer(MinecraftServer serverIn) {
		this.server = serverIn;
	}

	public MinecraftServer getServer() {
		return server;
	}
}
