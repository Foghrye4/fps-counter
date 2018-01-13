package fps_counter;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void load() {
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}

	@Override
	public File getMinecraftDir() {
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public void preInit() {
	}
}
