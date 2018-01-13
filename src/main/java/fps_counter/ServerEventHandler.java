package fps_counter;

import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHandler {

	public static boolean allowSpawn = true;

	@SubscribeEvent
	public void onAllowSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
		if (!allowSpawn)
			event.setResult(Result.DENY);
	}
}
