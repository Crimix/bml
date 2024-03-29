package com.black_dog20.bml.datagen.patchouli;

import com.black_dog20.bml.datagen.patchouli.objects.Book;
import com.black_dog20.bml.datagen.patchouli.objects.Category;
import com.black_dog20.bml.datagen.patchouli.objects.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to generate a Patchouli book
 *
 * @author black_dog20
 */
public abstract class PatchouliProvider implements DataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator gen;
    private final String modid;
    private final String bookname;
    private final String locale;
    private Book book;
    private List<Entry> entries = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private JsonObject languageFile;

    /**
     * The constructor for the provider.
     *
     * @param gen      the data generator.
     * @param modid    the mod id.
     * @param bookname the name of the book.
     * @param locale   the locale.
     */
    public PatchouliProvider(DataGenerator gen, String modid, String bookname, String locale) {
        this.gen = gen;
        this.modid = modid;
        this.bookname = bookname;
        this.locale = locale;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        languageFile = getLangFile(cache, locale);
        add();
        Path bookPath = this.gen.getOutputFolder().resolve("data/" + modid + "/patchouli_books/" + bookname);
        Path localePath = this.gen.getOutputFolder().resolve(bookPath + "/" + locale);
        Path entriesPath = this.gen.getOutputFolder().resolve(localePath + "/" + "entries");
        Path categoriesPath = this.gen.getOutputFolder().resolve(localePath + "/" + "categories");
        new File(bookPath.toString()).mkdirs();
        new File(localePath.toString()).mkdirs();
        new File(entriesPath.toString()).mkdirs();
        new File(categoriesPath.toString()).mkdirs();
        if (book != null)
            save(cache, book, this.gen.getOutputFolder().resolve(bookPath.toString() + "/book.json"));
        if (!categories.isEmpty()) {
            for (Category category : categories) {
                new File(this.gen.getOutputFolder().resolve(entriesPath.toString() + "/" + sanitize(category.getId()) + "/").toString()).mkdirs();
                save(cache, category, this.gen.getOutputFolder().resolve(categoriesPath.toString() + "/" + sanitize(category.getId()) + ".json"));
            }
        }

        if (!entries.isEmpty()) {
            for (Entry entry : entries) {
                save(cache, entry, this.gen.getOutputFolder().resolve(entriesPath.toString() + "/" + sanitize(entry.getCategory()) + "/" + sanitize(entry.getName()) + ".json"));
            }
        }
    }

    /**
     * The add method in which all objects should be created.
     */
    protected abstract void add();

    /**
     * Sets the book that should be generated.
     *
     * @param book the book to generate json for.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Adds an entry to the book.
     *
     * @param entry a entry to generate json for.
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Adds a category to the book.
     *
     * @param category an category to generate json for.
     */
    public void addCategory(Category category) {
        categories.add(category);
    }

    private void save(HashCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object);
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
        String hash = DataProvider.SHA1.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(data);
            }
        }

        cache.putNew(target, hash);
    }

    private static String sanitize(String s) {
        return s.toLowerCase().replace(' ', '_');
    }

    private static JsonObject getLangFile(HashCache cache, String locale) {
        Gson gson = new Gson();
        try {
            Field field = ObfuscationReflectionHelper.findField(HashCache.class, "newCache");
            field.setAccessible(true);
            Map<Path, String> createdFiles = (Map<Path, String>) field.get(cache);
            for (Path path : createdFiles.keySet()) {
                if (path.endsWith(locale + ".json")) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                    return gson.fromJson(bufferedReader, JsonObject.class);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    protected String getBlockName(Block block) {
        if (languageFile.has(block.getDescriptionId())) {
            return languageFile.get(block.getDescriptionId()).getAsString();
        }
        throw new IllegalStateException(block.getDescriptionId() + " was not found in lang file");
    }

    protected String getItemName(Item item) {
        if (languageFile.has(item.getDescriptionId())) {
            return languageFile.get(item.getDescriptionId()).getAsString();
        }
        throw new IllegalStateException(item.getDescriptionId() + " was not found in lang file");
    }
}
