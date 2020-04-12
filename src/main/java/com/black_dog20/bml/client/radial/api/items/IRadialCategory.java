package com.black_dog20.bml.client.radial.api.items;

import java.util.List;

/**
 * Interface for radial categories.
 *
 * @author black_dog20
 */
public interface IRadialCategory extends IRadialItem {

    /**
     * Gets the radial items for this category.
     *
     * @return a list of radial items.
     */
    List<IRadialItem> getItems();

    /**
     * Adds an item to this category;
     *
     * @param item the radial item to add.
     */
    void addItem(IRadialItem item);

    /**
     * If this category is empty should the radial menu close.
     *
     * @return true if the radial menu should close.
     */
    boolean closeIfEmpty();

    /**
     * Default empty override.
     */
    @Override
    default void click() {

    }

    /**
     * Default override.
     */
    @Override
    default boolean closeOnClick() {
        return false;
    }
}
