package com.black_dog20.bml.utils.keybinds;

import com.black_dog20.bml.utils.text.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class KeybindsUtil {

    /**
     * Returns if the key is down by its keycode.
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
        return InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), keycode);
    }

    /**
     * Gets the localized formatted name of the keybinding.
     *
     * @param keyBinding the keybinding.
     * @return the localized and formatted name.
     */
    public static String getKeyBindText(KeyBinding keyBinding) {
        return TextUtil.capitaliseFirstLetterFully(keyBinding.getLocalizedName().toLowerCase());
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
