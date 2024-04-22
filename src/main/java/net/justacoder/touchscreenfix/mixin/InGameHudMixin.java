package net.justacoder.touchscreenfix.mixin;

import net.justacoder.touchscreenfix.TouchscreenFix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/gui/DrawContext;)V"))
    private void renderOnScreenControls(DrawContext context, float tickDelta, CallbackInfo ci) {

        if (!TouchscreenFix.isEnabled() || !TouchscreenFix.SHOW_ONSCREEN_CONTROLS || !TouchscreenFix.ENABLE_ONSCREEN_CONTROLS) {
            return;
        }

        int winSizeX = context.getScaledWindowWidth();
        int winSizeY = context.getScaledWindowHeight();
        double guiScale = client.getWindow().getScaleFactor();

        context.fill((int) (winSizeX - TouchscreenFix.hitBoxSizeX / guiScale), (int) (winSizeY - TouchscreenFix.hitBoxSizeY / guiScale), winSizeX, winSizeY, TouchscreenFix.hitBoxColor);
        context.drawItem(new ItemStack(Items.STONE_SWORD), winSizeX - 16, winSizeY - 16);
        context.drawItem(new ItemStack(Items.STONE_PICKAXE), winSizeX - 16 - 16, winSizeY - 16);

    }


}
