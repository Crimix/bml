package com.black_dog20.bml.client.radial.api.items;

import com.black_dog20.bml.client.radial.api.RadialDrawingContext;
import com.black_dog20.bml.internal.utils.InternalTranslations;
import com.black_dog20.bml.utils.text.TextUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface IRadialItemStack extends IRadialItem {

    default void drawStack(RadialDrawingContext context, ItemStack stack) {
        if (stack.getCount() > 0) {
            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(context.guiGraphics.pose().last().pose());
            viewModelPose.translate(-8, -8, context.z);
            RenderSystem.applyModelViewMatrix();
            context.guiGraphics.renderItem(stack, (int) context.x, (int) context.y); //TODO CHECK
            context.guiGraphics.renderItemDecorations(context.fontRenderer, stack, (int) context.x, (int) context.y, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();
        }
    }

    default List<Component> getItemToolTip(ItemStack stack) {
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
