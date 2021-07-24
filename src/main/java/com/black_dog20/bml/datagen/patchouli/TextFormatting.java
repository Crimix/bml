package com.black_dog20.bml.datagen.patchouli;

import com.black_dog20.bml.datagen.patchouli.objects.Macro;
import net.minecraft.client.KeyMapping;

/**
 * Utility class to help in formatting texts in books.
 *
 * @author black_dog20
 */
public class TextFormatting {

    public static String format(String s, Format... formats) {
        return String.format(s, formats);
    }

    public static Format clear() {
        return new Format("$()");
    }

    public static Format lineBreak() {
        return new Format("$(br)");
    }

    public static Format doubleLineBreak() {
        return new Format("$(br2)");
    }

    public static Format list(String text) {
        return new Format("$(li)", text);
    }

    public static Format sublist(String text) {
        return new Format("$(li2)", text);
    }

    public static Format subsublist(String text) {
        return new Format("$(li3)", text);
    }

    public static Format color(String color, String text) {
        return new Format("$(#" + color + ")", text, "$(0)");
    }

    public static Format obfuscate(String text) {
        return new Format("$(k)", text, "$()");
    }

    public static Format bold(String text) {
        return new Format("$(l)", text, "$()");
    }

    public static Format strike(String text) {
        return new Format("$(m)", text, "$()");
    }

    public static Format italic(String text) {
        return new Format("$(o)", text, "$()");
    }

    public static Format entryLink(String entryId, String text) {
        return new Format("$(l:" + sanitize(entryId) + ")", text, "$(/l)");
    }

    public static Format entryLink(String entryId, Format text) {
        return new Format("$(l:" + sanitize(entryId) + ")", text.toString(), "$(/l)");
    }

    public static Format entryLinkWithAnchor(String entryId, String anchor, String text) {
        return new Format("$(l:" + sanitize(entryId) + "#" + anchor + ")", text, "$(/l)");
    }

    public static Format entryLinkWithAnchor(String entryId, String anchor, Format text) {
        return new Format("$(l:" + sanitize(entryId) + "#" + anchor + ")", text.toString(), "$(/l)");
    }

    public static Format webLink(String url, String text) {
        return new Format("$(l:" + url + ")", text, "$(/l)");
    }

    public static Format playerName() {
        return new Format("$(playername)");
    }

    public static Format keybind(KeyMapping keybind) {
        String desc = keybind.getName();
        if (desc.startsWith("key."))
            desc = desc.replaceFirst("key.", "");
        return new Format("$(k:", desc, ")");
    }

    public static Format tooltip(String tooltip, String text) {
        return new Format("$(t:" + tooltip + ")", text, "$(/t)");
    }

    public static Format command(String command, String text) {
        return new Format("$(c:" + command + ")", text, "$(/c)");
    }

    public static Format thing(String text) {
        return new Format("$(thing)", text, "$(0)");
    }

    public static Format item(String text) {
        return new Format("$(item)", text, "$(0)");
    }

    public static class Format {
        private final String start;
        private final String value;
        private final String end;

        public Format(String start, String value, String end) {
            this.start = start;
            this.value = value;
            this.end = end;
        }

        public Format(String start, String value) {
            this.start = start;
            this.value = value;
            this.end = "";
        }

        public Format(String start) {
            this.start = start;
            this.value = "";
            this.end = "";
        }

        public Format(String start, String value, Macro end) {
            this.start = start;
            this.value = value;
            this.end = end.getKey();
        }

        public Format(Macro start, String value, String end) {
            this.start = start.getKey();
            this.value = value;
            this.end = end;
        }

        public Format(Macro start, String value, Macro end) {
            this.start = start.getKey();
            this.value = value;
            this.end = end.getKey();
        }

        public Format(Macro start, String value) {
            this.start = start.getKey();
            this.value = value;
            this.end = "";
        }

        public Format(Macro start) {
            this.start = start.getKey();
            this.value = "";
            this.end = "";
        }

        @Override
        public String toString() {
            return start + value + end;
        }
    }

    private static String sanitize(String s) {
        return s.toLowerCase().replace(' ', '_');
    }
}
