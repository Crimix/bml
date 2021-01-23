package com.black_dog20.bml.utils.keybinds;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
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
        return InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), keycode);
    }

    /**
     * Returns if the key is down by its keyBinding.
     *
     * @param keyBinding the keybinding.
     * @return true if the key is down.
     */
    public static boolean isKeyDown(KeyBinding keyBinding) {
        Minecraft minecraft = Minecraft.getInstance();
        int keycode = keyBinding.getKey().getKeyCode();
        if (keyBinding.isInvalid())
            return false;

        boolean keyIsDown;
        if (keyBinding.getKey().getType() == InputMappings.Type.MOUSE) {
            keyIsDown = GLFW.glfwGetMouseButton(minecraft.getMainWindow().getHandle(), keycode) == 1;
        } else {
            keyIsDown = InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), keycode);
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
    public static boolean isKeyDownIgnoreConflicts(KeyBinding keyBinding) {
        Minecraft minecraft = Minecraft.getInstance();
        int keycode = keyBinding.getKey().getKeyCode();
        if (keyBinding.isInvalid())
            return false;

        if (keyBinding.getKey().getType() == InputMappings.Type.MOUSE) {
            return GLFW.glfwGetMouseButton(minecraft.getMainWindow().getHandle(), keycode) == 1;
        } else {
            return InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), keycode);
        }
    }

    /**
     * Gets the localized formatted name of the keybinding.
     *
     * @param keyBinding the keybinding.
     * @return the localized and formatted name.
     */
    public static String getKeyBindText(KeyBinding keyBinding) {
        return TextUtil.capitaliseFirstLetterFully(keyBinding.func_238171_j_().getString().toLowerCase());
    }

    /**
     * Gets the localized formatted name of the keybinding.
     *
     * @param keycode the keycode.
     * @return the localized and formatted name.
     */
    public static String getKeyBindText(int keycode) {
        return TextUtil.capitaliseFirstLetterFully(I18n.format(InputMappings.getInputByCode(keycode, -1).getTranslationKey()).toLowerCase());
    }
}
