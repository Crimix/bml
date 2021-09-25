package com.black_dog20.bml.internal.utils;

public class DevEnvironmentChecker {

    private static Boolean isDev = null;

    /**
     * Returns true if an only if <code>bml.isDevEnv</code> property is present and is true.
     * Is only evaluated once after that the cached result is returned.
     *
     * @return true if dev environment.
     */
    public static boolean isDev() {
        if (isDev == null) {
            try {
                isDev = Boolean.valueOf(System.getProperty("bml.isDevEnv"));
            } catch (Exception e) {
                isDev = false;
            }
        }

        return isDev;
    }
}
