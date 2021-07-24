package com.black_dog20.bml.client.radial.items;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.internal.utils.InternalTranslations;
import com.black_dog20.bml.utils.text.TextUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fmlclient.gui.GuiUtils;

import java.util.List;

/**
 * Radial item for an itemstack.
 *
 * @author black_dog20
 */
public class ItemstackRadialItem extends TextRadialItem {

    private final ItemStack stack;

    public ItemstackRadialItem(ItemStack stack, Component text) {
        super(text);
        this.stack = stack;
    }

    public ItemstackRadialItem(ItemStack stack, Component text, ChatFormatting color) {
        super(text, color);
        this.stack = stack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(DrawingContext context) {
        if (stack.getCount() > 0) {
            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(context.poseStack.last().pose());
            viewModelPose.translate(-8, -8, context.z);
            RenderSystem.applyModelViewMatrix();
            context.itemRenderer.renderAndDecorateItem(stack, (int) context.x, (int) context.y);
            context.itemRenderer.renderGuiItemDecorations(context.fontRenderer, stack, (int) context.x, (int) context.y, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();
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
            GuiUtils.drawHoveringText(stack, context.poseStack, getItemToolTip(stack), (int) context.x, (int) context.y, (int) context.width, (int) context.height, -1, context.fontRenderer);
            GuiUtils.postItemToolTip();
        } else {
            super.drawTooltips(context);
        }
    }

    private List<Component> getItemToolTip(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        List<Component> list = stack.getTooltipLines(minecraft.player, minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
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
