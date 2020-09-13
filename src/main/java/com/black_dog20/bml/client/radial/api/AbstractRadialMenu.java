package com.black_dog20.bml.client.radial.api;

import com.black_dog20.bml.client.radial.api.items.IRadialCategory;
import com.black_dog20.bml.client.radial.api.items.IRadialItem;
import com.black_dog20.bml.internal.utils.InternalTranslations;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.util.TriConsumer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.stream.Collectors;

import static com.black_dog20.bml.internal.utils.InternalTranslations.Translations.*;

/*
Note: This code is heavily inspired and has been modified from David Quintana's solution.
https://github.com/gigaherz/ToolBelt
Below is the required copyright notice.

Copyright (c) 2015, David Quintana <gigaherz@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the author nor the
      names of the contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/**
 * Abstract radial menu to extend to create your own.
 */
@OnlyIn(Dist.CLIENT)
public abstract class AbstractRadialMenu extends Screen {

    public enum State {
        INITIALIZING,
        OPENING,
        NORMAL,
        CLOSING,
        CLOSED
    }

    private static final float PRECISION = 2.5f / 360.0f;
    private static final double TWO_PI = 2.0 * Math.PI;
    private State state = State.INITIALIZING;
    public double startAnimation;
    public float animationProgress;
    public float radiusIn;
    public float radiusOut;
    public float itemRadius;
    public float animationTop;

    private List<IRadialItem> items;
    private List<IRadialItem> visibleItems;
    private int maxPages;
    private int currentPage;

    public AbstractRadialMenu(ITextComponent title, List<IRadialItem> items) {
        super(title);
        this.changeItems(items);

        Minecraft minecraft = Minecraft.getInstance();
        this.startAnimation = minecraft.world.getGameTime() + (double) minecraft.getRenderPartialTicks();
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Override to keep the menu open.
     *
     * @return true if the key to open the radial is pressed.
     */
    public abstract boolean isKeyDown();

    /**
     * Should it draw the center text.
     *
     * @return true to draw the text.
     */
    public abstract boolean shouldDrawCenterText();

    /**
     * Should the mouse clip to the circle.
     *
     * @return true if it should.
     */
    public abstract boolean ShouldClipMouseToCircle();

    /**
     * Does the radial menu allow click outside the circle.
     *
     * @return true if it should.
     */
    public abstract boolean allowClickOutsideBounds();

    /**
     * Override to change the size of the radial.
     *
     * @return the inner radius modifier.
     */
    public float getRadiusInModifier() {
        return 1.0f;
    }

    /**
     * Override to change how many items there is per page.
     *
     * @return max items per page.
     */
    public int getMaxItemsPerPage() {
        return 7;
    }

    /**
     * Override to change the background color.
     *
     * @return The color in hex.
     */
    public int getBackgroundColor() {
        return 0x3F000000;
    }

    /**
     * Override to change the background color when hovered.
     *
     * @return The color in hex.
     */
    public int getBackgroundColorHover() {
        return 0x3FFFFFFF;
    }

    /**
     * Override to change the length of the open animation.
     *
     * @return the length of the open animation.
     */
    public float getOpenAnimationLength() {
        return 2.5f;
    }

    /**
     * Override to change the footer.
     *
     * @param width     the width.
     * @param y         the y point to draw from.
     * @param radiusOut the outer radius.
     */
    public void drawFooter(float width, float y, float radiusOut) {
        String pageString = InternalTranslations.translateToString(PAGE_FOOTER, currentPage, maxPages);
        font.drawStringWithShadow(pageString, (width - font.getStringWidth(pageString)) / 2.0f, y, 0xFFFFFFFF);
    }

    /**
     * Override to draw a header above the radial.
     *
     * @param width     the width.
     * @param y         the y point to draw from.
     * @param radiusOut the outer radius.
     */
    public void drawHeader(float width, float y, float radiusOut) {
    }

    /**
     * Override to draw extras on the screen.
     *
     * @param radiusOut the outer radius.
     */
    public void drawExtras(float radiusOut) {

    }

    /**
     * Returns whether the radial is closed or not.
     *
     * @return true if closed.
     */
    public boolean isClosed() {
        return state == State.CLOSED;
    }

    /**
     * Returns whether the radial is ready or not.
     *
     * @return true if ready.
     */
    public boolean isReady() {
        return state == State.NORMAL;
    }

    /**
     * Returns the hovered item.
     *
     * @return the hovered item.
     */
    public IRadialItem getHoveredItem() {
        for (IRadialItem item : visibleItems) {
            if (item.isHovered())
                return item;
        }
        return null;
    }

    private void setHovered(int which) {
        for (int i = 0; i < visibleItems.size(); i++) {
            visibleItems.get(i).setHovered(i == which);
        }
    }

    @SubscribeEvent
    public void overlayEvent(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            return;

        if (Minecraft.getInstance().currentScreen instanceof AbstractRadialMenu) {
            event.setCanceled(true);
        }
    }

    @Override
    public void removed() {
        super.removed();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        processClick(true, state);
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (state == State.INITIALIZING) {
            startAnimation = minecraft.world.getGameTime() + (double) minecraft.getRenderPartialTicks();
            state = State.OPENING;
            animationProgress = 0;
        }

        if (isClosed()) {
            Minecraft.getInstance().displayGuiScreen(null);
        }
        if (!isReady()) {
            return;
        }
        if (!isKeyDown()) {
            processClick(false, 0);
        }
    }

    private void processClick(boolean triggeredByMouse, int button) {
        if (state == State.NORMAL) {
            if (getHoveredItem() != null) {
                if (getHoveredItem() instanceof IRadialCategory) {
                    IRadialCategory category = (IRadialCategory) getHoveredItem();
                    if (button == 1) {
                        List<IRadialItem> items = category.getContextItems();
                        if (!items.isEmpty()) {
                            if (items.size() == 1 && category.skipMenuIfSingleContextItem()) {
                                items.get(0).click();
                                if (items.get(0).closeOnClick())
                                    close();
                            } else
                                changeItems(items);
                        }
                    } else {
                        List<IRadialItem> items = category.getItems();
                        if (items.isEmpty() && category.closeIfEmpty())
                            close();
                        else
                            changeItems(items);

                    }
                } else {
                    IRadialItem item = getHoveredItem();
                    if (button == 1) {
                        List<IRadialItem> items = item.getContextItems();
                        if (!items.isEmpty()) {
                            if (items.size() == 1 && item.skipMenuIfSingleContextItem()) {
                                items.get(0).click();
                                if (items.get(0).closeOnClick())
                                    close();
                            } else
                                changeItems(items);
                        }
                    } else {
                        item.click();
                        if (item.closeOnClick())
                            close();
                    }
                }
            } else {
                onClickOutside();
            }
        }
    }

    /**
     * Override to change what it does when clicked outside the radial.
     */
    public void onClickOutside() {
        close();
    }

    /**
     * Closes the radial.
     */
    public void close() {
        state = State.CLOSING;
        startAnimation = minecraft.world.getGameTime() + (double) minecraft.getRenderPartialTicks();
        animationProgress = 1.0f;
        setHovered(-1);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        draw(partialTicks, mouseX, mouseY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void draw(float partialTicks, int mouseX, int mouseY) {
        updateAnimationState(partialTicks);

        if (isClosed())
            return;

        if (isReady())
            processMouse(mouseX, mouseY);

        boolean animated = state == State.OPENING || state == State.CLOSING;
        radiusIn = animated ? Math.max(0.1f, 30 * animationProgress) : 30;
        radiusIn = radiusIn * getRadiusInModifier();
        radiusOut = radiusIn * 2f;
        itemRadius = (radiusIn + radiusOut) * 0.5f;
        animationTop = animated ? (1 - animationProgress) * height / 2.0f : 0;

        int x = width / 2;
        int y = height / 2;
        float z = 0;

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, animationTop, 0);

        drawBackground(x, y, z, radiusIn, radiusOut);

        RenderSystem.popMatrix();

        if (isReady()) {
            drawItems(x, y, z, width, height, font, itemRenderer);

            ITextComponent currentCenterText = null;
            for (IRadialItem item : visibleItems) {
                if (item.isHovered()) {
                    if (item.getCenterText() != null)
                        currentCenterText = item.getCenterText();
                    break;
                }
            }

            if (currentCenterText != null && shouldDrawCenterText()) {
                String text = currentCenterText.getFormattedText();
                font.drawStringWithShadow(text, (width - font.getStringWidth(text)) / 2.0f, (height - font.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
            }

            drawTooltips(mouseX, mouseY);
            drawFooter(width, height / 2.0f + radiusOut * 1.05f, radiusOut);
            drawHeader(width, height / 2.0f - radiusOut * 1.05f - font.FONT_HEIGHT, radiusOut);
            drawExtras(radiusOut);

        }
    }

    private void updateAnimationState(float partialTicks) {
        float openAnimation = 0;
        switch (state) {
            case OPENING:
                openAnimation = (float) ((minecraft.world.getGameTime() + partialTicks - startAnimation) / getOpenAnimationLength());
                if (openAnimation >= 1.0 || visibleItems.size() == 0) {
                    openAnimation = 1;
                    state = State.NORMAL;
                }
                break;
            case CLOSING:
                openAnimation = 1 - (float) ((minecraft.world.getGameTime() + partialTicks - startAnimation) / getOpenAnimationLength());
                if (openAnimation <= 0 || visibleItems.size() == 0) {
                    openAnimation = 0;
                    state = State.CLOSED;
                }
                break;
        }
        animationProgress = openAnimation;
    }

    private void drawTooltips(int mouseX, int mouseY) {
        for (int i = 0; i < visibleItems.size(); i++) {
            IRadialItem item = visibleItems.get(i);
            if (item.isHovered()) {
                DrawingContext context = new DrawingContext(width, height, mouseX, mouseY, 0, font, itemRenderer);
                item.drawTooltips(context);
            }
        }
    }

    private void drawItems(int x, int y, float z, int width, int height, FontRenderer font, ItemRenderer itemRenderer) {
        iterateVisible((item, s, e) -> {
            float middle = (s + e) * 0.5f;
            float posX = x + itemRadius * (float) Math.cos(middle);
            float posY = y + itemRadius * (float) Math.sin(middle);

            DrawingContext context = new DrawingContext(width, height, posX, posY, z, font, itemRenderer);
            item.draw(context);
        });
    }

    private void iterateVisible(TriConsumer<IRadialItem, Float, Float> consumer) {
        int numItems = visibleItems.size();
        for (int i = 0; i < numItems; i++) {
            float s = (float) getAngleFor(i - 0.5, numItems);
            float e = (float) getAngleFor(i + 0.5, numItems);

            IRadialItem item = visibleItems.get(i);
            consumer.accept(item, s, e);
        }
    }

    private void drawBackground(float x, float y, float z, float radiusIn, float radiusOut) {
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        if (!visibleItems.isEmpty()) {
            iterateVisible((item, s, e) -> {
                int color = item.isHovered() ? getBackgroundColorHover() : getBackgroundColor();
                drawPieArc(buffer, x, y, z, radiusIn, radiusOut, s, e, color);
            });
        } else {
            float s = (float) getAngleFor(0 - 0.5, 1);
            float e = (float) getAngleFor(0 + 0.5, 1);
            drawPieArc(buffer, x, y, z, radiusIn, radiusOut, s, e, getBackgroundColor());
        }

        tessellator.draw();

        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    private void drawPieArc(BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int color) {
        float angle = endAngle - startAngle;
        int sections = Math.max(1, MathHelper.ceil(angle / PRECISION));

        angle = endAngle - startAngle;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        int a = (color >> 24) & 0xFF;

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1);
            float pos1InY = y + radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x + radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2);
            float pos2InX = x + radiusIn * (float) Math.cos(angle2);
            float pos2InY = y + radiusIn * (float) Math.sin(angle2);

            buffer.pos(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
            buffer.pos(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
            buffer.pos(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
            buffer.pos(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
        }
    }

    @SubscribeEvent
    public void onMouseScroll(GuiScreenEvent.MouseScrollEvent.Pre event) {
        if (Minecraft.getInstance().currentScreen instanceof AbstractRadialMenu) {
            if (!isScrollInverted()) {
                if (event.getScrollDelta() < 0) {
                    nexPage();
                } else if (event.getScrollDelta() > 0) {
                    prevPage();
                }
            } else {
                if (event.getScrollDelta() > 0) {
                    nexPage();
                } else if (event.getScrollDelta() < 0) {
                    prevPage();
                }
            }
        }
    }

    public boolean isScrollInverted() {
        return false;
    }

    private void processMouse(int mouseX, int mouseY) {

        if (!isReady())
            return;

        int numItems = visibleItems.size();

        int x = width / 2;
        int y = height / 2;
        double a = Math.atan2(mouseY - y, mouseX - x);
        double d = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
        if (numItems > 0) {
            double s0 = getAngleFor(0 - 0.5, numItems);
            double s1 = getAngleFor(numItems - 0.5, numItems);
            while (a < s0) {
                a += TWO_PI;
            }
            while (a >= s1) {
                a -= TWO_PI;
            }
        }

        int hovered = -1;
        for (int i = 0; i < numItems; i++) {
            float s = (float) getAngleFor(i - 0.5, numItems);
            float e = (float) getAngleFor(i + 0.5, numItems);

            if (a >= s && a < e && d >= radiusIn && (d < radiusOut || ShouldClipMouseToCircle() || allowClickOutsideBounds())) {
                hovered = i;
                break;
            }
        }
        setHovered(hovered);


        if (ShouldClipMouseToCircle()) {
            MainWindow mainWindow = minecraft.getMainWindow();

            int windowWidth = mainWindow.getWidth();
            int windowHeight = mainWindow.getHeight();

            double[] xPos = new double[1];
            double[] yPos = new double[1];
            GLFW.glfwGetCursorPos(mainWindow.getHandle(), xPos, yPos);

            double scaledX = xPos[0] - (windowWidth / 2.0f);
            double scaledY = yPos[0] - (windowHeight / 2.0f);

            double distance = Math.sqrt(scaledX * scaledX + scaledY * scaledY);
            double radius = radiusOut * (windowWidth / (float) width) * 0.975;

            if (distance > radius) {
                double fixedX = scaledX * radius / distance;
                double fixedY = scaledY * radius / distance;

                GLFW.glfwSetCursorPos(mainWindow.getHandle(), (int) (windowWidth / 2 + fixedX), (int) (windowHeight / 2 + fixedY));
            }
        }
    }

    private double getAngleFor(double i, int numItems) {
        if (numItems == 0)
            return 0;
        double angle = ((i / numItems) + 0.25) * TWO_PI + Math.PI;
        return angle;
    }

    private List<IRadialItem> getItemForPage(int page) {
        return items.stream()
                .skip((page - 1) * getMaxItemsPerPage())
                .limit(getMaxItemsPerPage())
                .collect(Collectors.toList());
    }

    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            visibleItems = getItemForPage(currentPage);
        }
    }

    private void nexPage() {
        if (currentPage < maxPages) {
            currentPage++;
            visibleItems = getItemForPage(currentPage);
        }
    }

    private void changeItems(List<IRadialItem> items) {
        this.items = items;
        maxPages = (int) Math.ceil(items.size() / (double) getMaxItemsPerPage());
        currentPage = 1;
        this.visibleItems = getItemForPage(currentPage);
    }
}
