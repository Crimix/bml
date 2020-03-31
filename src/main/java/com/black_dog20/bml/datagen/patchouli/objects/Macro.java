package com.black_dog20.bml.datagen.patchouli.objects;

public class Macro {
    private String key;
    private String value;

    public Macro(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key;
    }
}
