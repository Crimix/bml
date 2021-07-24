package com.black_dog20.bml.client.rows.columns;

import com.black_dog20.bml.client.rows.RowDrawingContext;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;

/**
 * Head column which displays the players head from their skin.
 */
public class HeadColumn extends Column {

    private final PlayerInfo info;
    private final boolean display;

    protected HeadColumn(String id, PlayerInfo info, Alignment alignment) {
        super(id, alignment);
        this.info = info;
        this.display = Minecraft.getInstance().isLocalServer() || Minecraft.getInstance().getConnection().getConnection().isEncrypted();
    }

    /**
     * Creates a head column from the NetworkPlayerInfo.
     *
     * @param id   the id of the column.
     * @param info the player to display the head from.
     * @return a head column
     */
    public static HeadColumn of(String id, PlayerInfo info) {
        return new HeadColumn(id, info, Alignment.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        if (display)
            return 10; //Heads are 10 wide
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(RowDrawingContext context) {
        GameProfile gameprofile = info.getProfile();
        Player entityplayer = Minecraft.getInstance().level.getPlayerByUUID(gameprofile.getId());

        if (display) {
            boolean flag1 = entityplayer != null && entityplayer.isModelPartShown(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
            Minecraft.getInstance().getTextureManager().bindForSetup(info.getSkinLocation());
            int l2 = 8 + (flag1 ? 8 : 0);
            int i3 = 8 * (flag1 ? -1 : 1);
            GuiComponent.blit(context.poseStack, (int) context.x, (int) context.y, 8, 8, 8.0F, (float) l2, 8, i3, 64, 64);

            if (entityplayer != null && entityplayer.isModelPartShown(PlayerModelPart.HAT)) {
                int j3 = 8 + (flag1 ? 8 : 0);
                int k3 = 8 * (flag1 ? -1 : 1);
                GuiComponent.blit(context.poseStack, (int) context.x, (int) context.y, 8, 8, 40.0F, (float) j3, 8, k3, 64, 64);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        if (display)
            return Minecraft.getInstance().font.lineHeight;
        return 0;
    }
}
