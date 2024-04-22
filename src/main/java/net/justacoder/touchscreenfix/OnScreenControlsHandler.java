package net.justacoder.touchscreenfix;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class OnScreenControlsHandler {

    private static MinecraftClient client = MinecraftClient.getInstance();

    public static boolean allowLeftMouseButton(int action, double x, double y) {

        if (!TouchscreenFix.ENABLE_ONSCREEN_CONTROLS) {
            return true;
        }

        if (action == GLFW.GLFW_PRESS) {

            int winSizeX = client.getWindow().getWidth();
            int winSizeY = client.getWindow().getHeight();

            if (x < (winSizeX - TouchscreenFix.hitBoxSizeX) || y < (winSizeY - TouchscreenFix.hitBoxSizeY)
                || x > winSizeX || y > winSizeY /* what */ ) {
                return false;
            }

            return true;

        }

        return true;
    }

}
