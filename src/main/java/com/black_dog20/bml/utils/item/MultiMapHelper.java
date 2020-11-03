package com.black_dog20.bml.utils.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MultiMapHelper {

    /**
     * Modifies the multimap, by removing the specific attribute by category and uuid.
     *
     * @param multimap the multimap to be modified.
     * @param key      the key (category).
     * @param uuid     the uuid of the attribute to be removed.
     */
    public static void removeValues(Multimap<Attribute, AttributeModifier> multimap, Attribute key, UUID uuid) {
        List<AttributeModifier> list = multimap.get(key).stream()
                .filter(a -> a.getID().equals(uuid))
                .collect(Collectors.toList());
        list.forEach(a -> multimap.remove(key, a));
    }
}
