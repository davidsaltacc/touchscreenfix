package net.justacoder.touchscreenfix.screen;

import net.justacoder.touchscreenfix.TouchscreenFix;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

    private final Screen parent;

    public ConfigScreen(Screen parent) {

        super(Text.translatable("screen.touchscreenfix.config"));

        this.parent = parent;

    }

    @Override
    protected void init() {

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("button.touchscreenfix.config.return"), button -> this.client.setScreen(parent)).dimensions(5, 5, 50, 20).build());

        GridWidget grid = new GridWidget();
        grid.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = grid.createAdder(1);

        Widget ENABLE = new CheckboxWidget(0, 0, 120, 20, Text.translatable("button.touchscreenfix.enable"), TouchscreenFix.isEnabled()){ @Override public void onPress() { super.onPress();
            TouchscreenFix.setEnabled(isChecked());
        }};

        Widget INVERT_X = new CheckboxWidget(0, 0, 120, 20, Text.translatable("button.touchscreenfix.config.invert_x"), TouchscreenFix.INVERT_X){ @Override public void onPress() { super.onPress();
            TouchscreenFix.INVERT_X = isChecked();
        }};

        Widget INVERT_Y = new CheckboxWidget(0, 0, 120, 20, Text.translatable("button.touchscreenfix.config.invert_y"), TouchscreenFix.INVERT_Y){ @Override public void onPress() { super.onPress();
            TouchscreenFix.INVERT_Y = isChecked();
        }};

        Widget ENABLE_ONSCREEN_CONTROLS = new CheckboxWidget(0, 0, 120, 20, Text.translatable("button.touchscreenfix.config.enable_onscreen_controls"), TouchscreenFix.ENABLE_ONSCREEN_CONTROLS){ @Override public void onPress() { super.onPress();
            TouchscreenFix.ENABLE_ONSCREEN_CONTROLS = isChecked();
        }};

        Widget SHOW_ONSCREEN_CONTROLS = new CheckboxWidget(0, 0, 120, 20, Text.translatable("button.touchscreenfix.config.show_onscreen_controls"), TouchscreenFix.SHOW_ONSCREEN_CONTROLS){ @Override public void onPress() { super.onPress();
            TouchscreenFix.SHOW_ONSCREEN_CONTROLS = isChecked();
        }};

        adder.add(ENABLE);
        adder.add(INVERT_X);
        adder.add(INVERT_Y);
        adder.add(ENABLE_ONSCREEN_CONTROLS);
        adder.add(SHOW_ONSCREEN_CONTROLS);

        grid.refreshPositions();
        SimplePositioningWidget.setPos(grid, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        grid.forEachChild(this::addDrawableChild);

    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
