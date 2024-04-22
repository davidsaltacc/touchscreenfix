package net.justacoder.touchscreenfix;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouchscreenFix implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("touchscreenfix");

	private static boolean ENABLED = false;
	public static boolean INVERT_X = false;
	public static boolean INVERT_Y = false;
	public static boolean ENABLE_ONSCREEN_CONTROLS = true;
	public static boolean SHOW_ONSCREEN_CONTROLS = true;

	public static int hitBoxSizeX = 400;
	public static int hitBoxSizeY = 400;
	public static int hitBoxColor = 516475080;

	public static boolean isEnabled() {
		if (MinecraftClient.getInstance().player == null) {
			return false;
		}
		return ENABLED;
	}

	public static void setEnabled(boolean enabled) {
		ENABLED = enabled;
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("hi! ");
	}
}