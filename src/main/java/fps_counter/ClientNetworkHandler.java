package fps_counter;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class ClientNetworkHandler extends ServerNetworkHandler {

	@SubscribeEvent
	public void onPacketFromServerToClient(FMLNetworkEvent.ClientCustomPacketEvent event) throws IOException {
		ByteBuf data = event.getPacket().payload();
		PacketBuffer byteBufInputStream = new PacketBuffer(data);
		switch (ClientCommands.values()[byteBufInputStream.readByte()]) {
			case START_RECORDING:
				String comment = byteBufInputStream.readString(1024);
				ClientEventHandler.startRecording = true;
				ClientEventHandler.addComment(comment);
				break;
			case PAUSE_RECORDING :
				ClientEventHandler.startRecording = false;
				ClientEventHandler.resetFrameCounter();
				break;
			case STOP_AND_WRITE_RESULTS:
				ClientEventHandler.startRecording = false;
				ClientEventHandler.flushResults();
				break;
			default :
				break;
		}
	}
}
