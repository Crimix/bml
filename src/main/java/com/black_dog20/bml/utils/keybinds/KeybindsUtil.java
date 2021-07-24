package com.black_dog20.bml.utils.keybinds;

import com.black_dog20.bml.utils.text.TextUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import org.lwjgl.glfw.GLFW;

public class KeybindsUtil {

    /**
     * Returns if the key is down by its keycode.
     * Does not support mouse keys.
     *
     * @param keycode the keycode.
     * @return true if the key is down.
     */
    public static boolean isKeyDown(int keycode) {
        Minecraft minecraft = Minecraft.getInstance();
        return InputConstants.isKeyDown(minecraft.getWindow().getWindow(), keycode);
    }

    /**
     * Returns if the key is down by its keyBinding.
     *
     * @param keyBinding the keybinding.
     * @return true if the key is down.
     */
    public static boolean isKeyDown(KeyMapping keyBinding) {
        Minecraft minecraft = Minecraft.getInstance();
        int keycode = keyBinding.getKey().getValue();
        if (keyBinding.isUnbound())
            return false;

        boolean keyIsDown;
        if (keyBinding.getKey().getType() == InputConstants.Type.MOUSE) {
            keyIsDown = GLFW.glfwGetMouseButton(minecraft.getWindow().getWindow(), keycode) == 1;
        } else {
            keyIsDown = InputConstants.isKeyDown(minecraft.getWindow().getWindow(), keycode);
        }

        boolean conflictContextActive = keyBinding.getKeyConflictContext().isActive();
        boolean keyModifierActivate = keyBinding.getKeyModifier().isActive(keyBinding.getKeyConflictContext());

        return keyIsDown && conflictContextActive && keyModifierActivate;
    }

    /**
     * Returns if the key is down by its keyBinding while ignore conflict resolution.
     *
     * @param keyBinding the keybinding.
     * @return true if the key is down.
     */
    public static boolean isKeyDownIgnoreConflicts(KeyMapping keyBinding) {
        Minecraft minecraft = Minecraft.getInstance();
        int keycode = keyBinding.getKey().getValue();
        if (keyBinding.isUnbound())
            return false;

        if (keyBinding.getKey().getType() == InputConstants.Type.MOUSE) {
            return GLFW.glfwGetMouseButton(minecraft.getWindow().getWindow(), keycode) == 1;
        } else {
            return InputConstants.isKeyDown(minecraft.getWindow().getWindow(), keycode);
        }
    }

    /**
     * Gets the localized formatted name of the keybinding.
     *
     * @param keyBinding the keybinding.
     * @return the localized and formatted name.
     */
    public static String getKeyBindText(KeyMapping keyBinding) {
        return TextUtil.capitaliseFirstLetterFully(keyBinding.getTranslatedKeyMessage().getString().toLowerCase());
    }

    /**
     * Gets the localized formatted name of the keybinding.
     *
     * @param keycode the keycode.
     * @return the localized and formatted name.
     */
    public static String getKeyBindText(int keycode) {
        return TextUtil.capitaliseFirstLetterFully(I18n.get(InputConstants.getKey(keycode, -1).getName()).toLowerCase());
    }
}
