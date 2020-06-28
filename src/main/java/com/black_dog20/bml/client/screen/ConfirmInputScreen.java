package com.black_dog20.bml.client.screen;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Based on {@link net.minecraft.client.gui.screen.ConfirmScreen}
 * This screen presents the user with a text input and two buttons.
 * A yes and a no button.
 * If the user inputs a text and presses the yes button the callback function
 * will be called with a boolean value true and what ever text the user entered.
 * If the user presses the no button the callback is called with false and empty String.
 */
@OnlyIn(Dist.CLIENT)
public class ConfirmInputScreen extends Screen {
    private final List<String> listLines = Lists.newArrayList();
    /**
     * The text shown for the first button in GuiYesNo
     */
    protected final String confirmButtonText;
    /**
     * The text shown for the second button in GuiYesNo
     */
    protected final String cancelButtonText;
    private int ticksUntilEnable;
    private TextFieldWidget textField;
    private final ITextComponent message;
    protected final BiConsumer<Boolean, String> callbackFunction;

    public ConfirmInputScreen(BiConsumer<Boolean, String> callbackFunction, ITextComponent title, ITextComponent message) {
        this(callbackFunction, title, I18n.format("gui.yes"), I18n.format("gui.no"), message);
    }

    public ConfirmInputScreen(BiConsumer<Boolean, String> callbackFunction, ITextComponent title, String confirmButtonText, String cancelButtonText, ITextComponent message) {
        super(title);
        this.callbackFunction = callbackFunction;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.message = message;
    }

    protected void init() {
        super.init();
        this.listLines.clear();
        this.listLines.addAll(this.font.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50));
        textField = new TextFieldWidget(this.font, this.width / 2 - 100, 70 + ((listLines.size() + 1) * 9), 200, 20, "");
        this.addButton(textField);
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.confirmButtonText, (p_213002_1_) -> {
            this.callbackFunction.accept(true, textField.getText());
        }));
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20, this.cancelButtonText, (p_213001_1_) -> {
            this.callbackFunction.accept(false, "");
        }));
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 50, 16777215);

        int i = 70;

        for (String s : this.listLines) {
            this.drawCenteredString(this.font, s, this.width / 2, i, 16777215);
            i += 9;
        }
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (Widget widget : this.buttons) {
            if (!(widget instanceof TextFieldWidget))
                widget.active = false;
        }

    }

    public void tick() {
        super.tick();
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttons) {
                if (!(widget instanceof TextFieldWidget))
                    widget.active = true;
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