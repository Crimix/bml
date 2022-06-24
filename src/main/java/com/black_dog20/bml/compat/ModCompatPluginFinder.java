package com.black_dog20.bml.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ModCompatPluginFinder {

    public static void registerModcompatToEventBus(String sourceModId) {
        Type annotationType = Type.getType(ModCompatPlugin.class);
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        for (ModFileScanData scanData : allScanData) {
            Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();
            for (ModFileScanData.AnnotationData a : annotations) {
                if (Objects.equals(a.annotationType(), annotationType)) {
                    boolean hasSourceModId = hasCorrectSourceModId(a, sourceModId);
                    boolean targetModIdLoaded = isTargetModLoaded(a);

                    if (hasSourceModId && targetModIdLoaded) {
                        String className = a.memberName();
                        try {
                            Class<?> asmClass = Class.forName(className);
                            Constructor<?> constructor = asmClass.getDeclaredConstructor();
                            MinecraftForge.EVENT_BUS.register(constructor.newInstance());
                        } catch (ReflectiveOperationException | LinkageError e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static boolean hasCorrectSourceModId(ModFileScanData.AnnotationData data, String sourceModId) {
        return Optional.ofNullable(data.annotationData().get("sourceMod"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(sourceModId::equals)
                .orElse(false);
    }

    private static boolean isTargetModLoaded(ModFileScanData.AnnotationData data) {
        return Optional.ofNullable(data.annotationData().get("targetMods"))
                .filter(String[].class::isInstance)
                .map(String[].class::cast)
                .map(Arrays::asList)
                .stream()
                .flatMap(Collection::stream)
                .allMatch(ModList.get()::isLoaded);
    }
}
