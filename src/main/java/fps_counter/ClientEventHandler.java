package fps_counter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.common.base.Strings;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

	public static boolean startRecording = false;
	public static boolean prevStartRecording = false;
	final static int PROBE_SIZE = 100;
	static int currentIndex = 0;
	int currentFrame = 0;
	static long[] results = new long[6000];
	static int[] frames = new int[6000];
	static Int2ObjectMap<String> comments = new Int2ObjectOpenHashMap<String>();
	long lastTimeMeasure = 0;
	private static boolean resetFrameCounter = false;

	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event) {
		if (prevStartRecording != startRecording) {
			prevStartRecording = startRecording;
			lastTimeMeasure = System.currentTimeMillis();
			currentFrame = 0;
		}
		if (startRecording && currentFrame++ == PROBE_SIZE) {
			results[currentIndex] = System.currentTimeMillis() - lastTimeMeasure;
			if (currentIndex == 0 || resetFrameCounter) {
				frames[currentIndex] = PROBE_SIZE;
				resetFrameCounter = false;
			} else {
				frames[currentIndex] = frames[currentIndex - 1] + PROBE_SIZE;
			}
			currentIndex++;
			lastTimeMeasure = System.currentTimeMillis();
			currentFrame = 0;
		}
	}

	public static void flushResults() {
		OutputStreamWriter osWriter = null;
		try {
			osWriter = new OutputStreamWriter(new FileOutputStream(getFile("fps_counter_output", "output.csv")));
			BufferedWriter writer = new BufferedWriter(osWriter);
			for (int i = 0; i < currentIndex; i++) {
				if (comments.containsKey(i)) {
					writer.append(comments.get(i) + ", ,");
					writer.newLine();
				}
				writer.append(frames[i] + "," + results[i] + ",");
				writer.newLine();
			}
			writer.close();
			osWriter.close();
			currentIndex = 0;
		} catch (IOException e) {
		}
	}

	public static File getFile(String folder_name, String filename) {
		File folder = new File(FPSCounterMod.proxy.getMinecraftDir(), folder_name);
		folder.mkdirs();
		return new File(folder, filename);
	}

	public static void addComment(String comment) {
		if (!comment.equals(""))
			comments.put(currentIndex, comment);
	}

	public static void resetFrameCounter() {
		resetFrameCounter = true;
	}
}
