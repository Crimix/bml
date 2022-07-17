package com.black_dog20.bml.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiConsumer;

/**
 * Based on {@link net.minecraft.client.gui.screens.ConfirmScreen}
 * This screen presents the user with a text input and two buttons.
 * A yes and a no button.
 * If the user inputs a text and presses the yes button the callback function
 * will be called with a boolean value true and what ever text the user entered.
 * If the user presses the no button the callback is called with false and empty String.
 */
@OnlyIn(Dist.CLIENT)
public class ConfirmInputScreen extends Screen {
    private MultiLineLabel listLines = MultiLineLabel.EMPTY;
    /**
     * The text shown for the first button in GuiYesNo
     */
    protected final Component confirmButtonText;
    /**
     * The text shown for the second button in GuiYesNo
     */
    protected final Component cancelButtonText;
    private int ticksUntilEnable;
    private EditBox textField;
    private final Component message;
    protected final BiConsumer<Boolean, String> callbackFunction;

    public ConfirmInputScreen(BiConsumer<Boolean, String> callbackFunction, Component title, Component message) {
        this(callbackFunction, title, CommonComponents.GUI_YES, CommonComponents.GUI_NO, message);
    }

    public ConfirmInputScreen(BiConsumer<Boolean, String> callbackFunction, Component title, Component confirmButtonText, Component cancelButtonText, Component message) {
        super(title);
        this.callbackFunction = callbackFunction;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.message = message;
    }

    protected void init() {
        super.init();
        this.listLines = MultiLineLabel.create(this.font, this.message, this.width - 50);
        textField = new EditBox(this.font, this.width / 2 - 100, 70 + ((listLines.getLineCount() + 1) * 9), 200, 20, CommonComponents.EMPTY);
        this.addRenderableWidget(textField);
        this.addRenderableWidget(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.confirmButtonText, (p_213002_1_) -> {
            this.callbackFunction.accept(true, textField.getValue());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20, this.cancelButtonText, (p_213001_1_) -> {
            this.callbackFunction.accept(false, "");
        }));
    }

    @Override
    public void render(PoseStack poseStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(poseStack);
        this.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 50, 16777215);
        this.listLines.renderCentered(poseStack, this.width / 2, 70);
        super.render(poseStack, p_render_1_, p_render_2_, p_render_3_);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (Widget widget : this.renderables) {
            if (widget instanceof AbstractWidget abstractWidget) {
                if (!(abstractWidget instanceof EditBox)) {
                    abstractWidget.active = false;
                }
            }
        }

    }

    public void tick() {
        super.tick();
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.renderables) {
                if (widget instanceof AbstractWidget abstractWidget) {
                    if (!(abstractWidget instanceof EditBox)) {
                        abstractWidget.active = true;
                    }
                }
            }
        }

    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256) {
            this.callbackFunction.accept(false, "");
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }
}