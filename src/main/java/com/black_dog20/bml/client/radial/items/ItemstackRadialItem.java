package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.internal.utils.InternalTranslations;
import com.black_dog20.bml.utils.text.TextUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.List;

/**
 * Radial item for an itemstack.
 *
 * @author black_dog20
 */
public class ItemstackRadialItem extends TextRadialItem {

    private final ItemStack stack;

    public ItemstackRadialItem(ItemStack stack, ITextComponent text) {
        super(text);
        this.stack = stack;
    }

    public ItemstackRadialItem(ItemStack stack, ITextComponent text, TextFormatting color) {
        super(text, color);
        this.stack = stack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(DrawingContext context) {
        if (stack.getCount() > 0) {
            RenderHelper.enableStandardItemLighting();
            RenderSystem.pushMatrix();
            RenderSystem.translatef(-8, -8, context.z);
            context.itemRenderer.renderItemAndEffectIntoGUI(stack, (int) context.x, (int) context.y);
            context.itemRenderer.renderItemOverlayIntoGUI(context.fontRenderer, stack, (int) context.x, (int) context.y, "");
            RenderSystem.popMatrix();
            RenderHelper.disableStandardItemLighting();
        } else {
            super.draw(context);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTooltips(DrawingContext context) {
        if (stack.getCount() > 0) {
            GuiUtils.preItemToolTip(stack);
            GuiUtils.drawHoveringText(stack, context.matrixStack, getItemToolTip(stack), (int) context.x, (int) context.y, (int) context.width, (int) context.height, -1, context.fontRenderer);
            GuiUtils.postItemToolTip();
        } else {
            super.drawTooltips(context);
        }
    }

    private List<ITextComponent> getItemToolTip(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        List<ITextComponent> list = stack.getTooltip(minecraft.player, minecraft.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        if (!getContextItems().isEmpty()) {
            if (getContextItems().size() == 1 && skipMenuIfSingleContextItem()) {
                IRadialItem item = getContextItems().get(0);
                list.add(TranslationUtil.translate(InternalTranslations.Translations.RIGHT_CLICK_TO, TextUtil.getFormattedText(item.getCenterText()).toLowerCase()));
            } else {
                list.add(TranslationUtil.translate(InternalTranslations.Translations.RIGHT_CLICK_FOR_OPTIONS));
            }
        }
        return list;
    }
}
