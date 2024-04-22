package net.justacoder.touchscreenfix.mixin;

import net.justacoder.touchscreenfix.OnScreenControlsHandler;
import net.justacoder.touchscreenfix.TouchscreenFix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow public abstract boolean isCursorLocked();
    @Shadow @Final private MinecraftClient client;
    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;
    @Shadow private double x;
    @Shadow private double y;
    @Shadow private boolean leftButtonClicked;
    @Shadow public abstract void updateMouse();

    @Inject(method = "lockCursor", at = @At("HEAD"), cancellable = true)
    private void preventCursorLock(CallbackInfo ci) {

        if (TouchscreenFix.isEnabled()) {
            ci.cancel();
        }

    }

    @Inject(method = "onCursorPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;updateMouse()V", shift = At.Shift.BEFORE), cancellable = true)
    private void enableUnlockedMouseCameraMovement(long window, double x, double y, CallbackInfo ci) {

        if (!this.leftButtonClicked && TouchscreenFix.isEnabled()) {
            this.x = x;
            this.y = y;
            this.updateMouse();
            this.client.getProfiler().pop();
            ci.cancel();
            return;
        }

        if (TouchscreenFix.isEnabled() && this.client.isWindowFocused() && !this.isCursorLocked()) {
            this.cursorDeltaX = -this.x + x;
            this.cursorDeltaY = -this.y + y;

            this.cursorDeltaX *= TouchscreenFix.INVERT_X ? -1 : 1;
            this.cursorDeltaY *= TouchscreenFix.INVERT_Y ? -1 : 1;
        }

    }

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;isCursorLocked()Z"))
    private boolean bypassDisableCamMovementOnMouseUnlocked(Mouse mouse) {

        return TouchscreenFix.isEnabled() || this.isCursorLocked();

    }

    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;setKeyPressed(Lnet/minecraft/client/util/InputUtil$Key;Z)V", shift = At.Shift.BEFORE), cancellable = true)
    private void handleUnwantedTouchscreenClicks(long window, int button, int action, int mods, CallbackInfo ci) {

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && TouchscreenFix.isEnabled()) {
            if (!OnScreenControlsHandler.allowLeftMouseButton(action, this.x, this.y)) {
                ci.cancel();
            }
        }

    }

}