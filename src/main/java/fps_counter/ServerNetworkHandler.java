package fps_counter;

import static fps_counter.FPSCounterMod.MODID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class ServerNetworkHandler {

	protected static final FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MODID);

	public enum ClientCommands {
		START_RECORDING,
		PAUSE_RECORDING,
		STOP_AND_WRITE_RESULTS;
	}

	public ServerNetworkHandler() {
		channel.register(this);
	}

	public void sendCommand(ClientCommands command, String comment) {
		ByteBuf bb = Unpooled.buffer(36);
		PacketBuffer byteBufOutputStream = new PacketBuffer(bb);
		byteBufOutputStream.writeByte(command.ordinal());
		byteBufOutputStream.writeString(comment);
		channel.sendToAll(new FMLProxyPacket(byteBufOutputStream, MODID));
	}
}
