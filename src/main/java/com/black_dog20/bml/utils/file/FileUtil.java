package com.black_dog20.bml.utils.file;

import com.black_dog20.bml.Bml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.function.Supplier;

public class FileUtil {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    /**
     * Saves the data as a file to the specific dir.
     *
     * @param dir      the dir.
     * @param fileName the name of the file.
     * @param data     the data.
     * @param type     the type of the data.
     * @return true if the data was saved, otherwise false.
     */
    public static <T> boolean save(File dir, String fileName, T data, Type type) {
        try {
            dir.mkdirs();

            if (!fileName.startsWith("/"))
                fileName = "/" + fileName;

            File dataFile = new File(dir.getPath() + fileName);
            FileWriter writer = new FileWriter(dataFile);
            GSON.toJson(data, type, writer);
            writer.close();

            return true;
        } catch (Exception e) {
            Bml.getLogger().error(e.getMessage());
            return false;
        }
    }

    /**
     * Loads tha data from a file in a dir, or if the file does not exists returns the result of the supplier.
     *
     * @param dir      the dir.
     * @param fileName the name of the file.
     * @param type     the type of the data to return.
     * @param supplier the supplier which creates the fallback result.
     * @return returns the data or the fallback result.
     */
    public static <T> T load(File dir, String fileName, Type type, Supplier<T> supplier) {
        try {
            dir.mkdirs();

            if (!fileName.startsWith("/"))
                fileName = "/" + fileName;

            File dataFile = new File(dir.getPath() + fileName);
            if (dataFile.exists()) {
                JsonReader reader = new JsonReader(new FileReader(dataFile));
                T data = GSON.fromJson(reader, type);
                if (data == null)
                    return supplier.get();
                else
                    return data;
            } else {
                return supplier.get();
            }
        } catch (Exception e) {
            Bml.getLogger().error(e.getMessage());
            return supplier.get();
        }
    }

    /**
     * Returns a file pointing to a folder inside the world folder.
     *
     * @param world   the server world.
     * @param dirName the name of the dir.
     * @return a file pointing to the folder.
     */
    public static File getDirRelativeToWorldFolder(ServerWorld world, String dirName) {
        Path path = world.getServer().func_240776_a_(FolderName.field_237245_a_);
        File dir = path.toFile();
        if (!dirName.startsWith("/"))
            dirName = "/" + dirName;
        File dirFile = new File(dir.getPath() + dirName);
        dirFile.mkdirs();
        return dirFile;
    }
}
