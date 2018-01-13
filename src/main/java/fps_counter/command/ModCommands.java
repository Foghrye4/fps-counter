package fps_counter.command;

import fps_counter.FPSCounterMod;
import fps_counter.ServerEventHandler;
import fps_counter.ServerNetworkHandler.ClientCommands;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class ModCommands extends CommandBase {

	@Override
	public String getName() {
		return "fps";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 4;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/fps [spawnoff|spawnon|start|pause|stop]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1)
			throw new WrongUsageException(this.getUsage(sender), new Object[0]);
		if (args[0].equalsIgnoreCase("spawnoff"))
			ServerEventHandler.allowSpawn = false;
		else if (args[0].equalsIgnoreCase("spawnon"))
			ServerEventHandler.allowSpawn = true;
		else if (args[0].equalsIgnoreCase("start"))
			FPSCounterMod.network.sendCommand(ClientCommands.START_RECORDING, args.length > 1? args[1]:"");
		else if (args[0].equalsIgnoreCase("pause"))
			FPSCounterMod.network.sendCommand(ClientCommands.PAUSE_RECORDING, args.length > 1? args[1]:"");
		else if (args[0].equalsIgnoreCase("stop"))
			FPSCounterMod.network.sendCommand(ClientCommands.STOP_AND_WRITE_RESULTS, args.length > 1? args[1]:"");
		else
			throw new WrongUsageException(this.getUsage(sender), new Object[0]);
	}
}
