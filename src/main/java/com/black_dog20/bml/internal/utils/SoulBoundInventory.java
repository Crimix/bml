package com.black_dog20.bml.internal.utils;

import com.black_dog20.bml.api.ISoulbindable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SoulBoundInventory {

    private static String SOULBOUND_INVENTORY_TAG = "bml-soulbound-inventory";

    public final NonNullList<ItemStack> mainInventory;
    public final NonNullList<ItemStack> armorInventory;
    public final NonNullList<ItemStack> offHandInventory;
    public final Player player;

    private SoulBoundInventory(Player player, boolean load) {
        this.player = player;
        this.mainInventory = NonNullList.<ItemStack>withSize(player.getInventory().items.size(), ItemStack.EMPTY);
        this.armorInventory = NonNullList.<ItemStack>withSize(player.getInventory().armor.size(), ItemStack.EMPTY);
        this.offHandInventory = NonNullList.<ItemStack>withSize(player.getInventory().offhand.size(), ItemStack.EMPTY);

        if (load) {
            readFromNBT();
        } else {
            copyMain();
            copyArmor();
            copyOffHand();
        }
    }

    public SoulBoundInventory(Player player) {
        this(player, false);
    }

    public static SoulBoundInventory GetForPlayer(Player player) {
        return new SoulBoundInventory(player, true);
    }

    private boolean isSoulbound(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem() instanceof ISoulbindable && ((ISoulbindable) itemStack.getItem()).isSoulbound(itemStack);
    }

    private void copyMain() {
        NonNullList<ItemStack> old = player.getInventory().items;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                this.mainInventory.set(i, old.get(i).copy());
                old.get(i).setCount(0);
            }
        }
    }

    private void copyArmor() {
        NonNullList<ItemStack> old = player.getInventory().armor;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                this.armorInventory.set(i, old.get(i).copy());
                old.get(i).setCount(0);
            }
        }
    }

    private void copyOffHand() {
        NonNullList<ItemStack> old = player.getInventory().offhand;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                this.offHandInventory.set(i, old.get(i));
                old.get(i).setCount(0);
            }
        }
    }

    public void restoreMain(Player player) {
        NonNullList<ItemStack> old = this.mainInventory;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                player.getInventory().items.set(i, old.get(i).copy());
                old.get(i).setCount(0);
            }
        }
    }


    public void restoreArmor(Player player) {
        NonNullList<ItemStack> old = this.armorInventory;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                player.getInventory().armor.set(i, old.get(i).copy());
                old.get(i).setCount(0);
            }
        }
    }

    public void restoreHand(Player player) {
        NonNullList<ItemStack> old = this.offHandInventory;
        for (int i = 0; i < old.size(); i++) {
            ItemStack itemStack = old.get(i);
            if (isSoulbound(itemStack)) {
                player.getInventory().offhand.set(i, old.get(i));
                old.get(i).setCount(0);
            }
        }
    }

    public void writeToNBT() {
        ListTag nbtTagListIn = new ListTag();
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (!((ItemStack) this.mainInventory.get(i)).isEmpty()) {
                CompoundTag nbttagcompound = new CompoundTag();
                nbttagcompound.putByte("Slot", (byte) i);
                ((ItemStack) this.mainInventory.get(i)).save(nbttagcompound);
                nbtTagListIn.add(nbttagcompound);
            }
        }

        for (int j = 0; j < this.armorInventory.size(); ++j) {
            if (!((ItemStack) this.armorInventory.get(j)).isEmpty()) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte) (j + 100));
                ((ItemStack) this.armorInventory.get(j)).save(nbttagcompound1);
                nbtTagListIn.add(nbttagcompound1);
            }
        }

        for (int k = 0; k < this.offHandInventory.size(); ++k) {
            if (!((ItemStack) this.offHandInventory.get(k)).isEmpty()) {
                CompoundTag nbttagcompound2 = new CompoundTag();
                nbttagcompound2.putByte("Slot", (byte) (k + 150));
                ((ItemStack) this.offHandInventory.get(k)).save(nbttagcompound2);
                nbtTagListIn.add(nbttagcompound2);
            }
        }
        player.getPersistentData().put(SOULBOUND_INVENTORY_TAG, nbtTagListIn);
    }

    public void readFromNBT() {
        this.mainInventory.clear();
        this.armorInventory.clear();
        this.offHandInventory.clear();

        ListTag nbtTagListIn = (ListTag) player.getPersistentData().get(SOULBOUND_INVENTORY_TAG);
        if (nbtTagListIn != null) {

            for (int i = 0; i < nbtTagListIn.size(); ++i) {
                CompoundTag nbttagcompound = nbtTagListIn.getCompound(i);
                int j = nbttagcompound.getByte("Slot") & 255;
                ItemStack itemstack = ItemStack.of(nbttagcompound);

                if (!itemstack.isEmpty()) {
                    if (j >= 0 && j < this.mainInventory.size()) {
                        this.mainInventory.set(j, itemstack);
                    } else if (j >= 100 && j < this.armorInventory.size() + 100) {
                        this.armorInventory.set(j - 100, itemstack);
                    } else if (j >= 150 && j < this.offHandInventory.size() + 150) {
                        this.offHandInventory.set(j - 150, itemstack);
                    }
                }
            }
        }

    }


    public void clear() {
        player.getPersistentData().remove(SOULBOUND_INVENTORY_TAG);
    }

    public boolean addItemStackToInventory(ItemStack stack) {
        int i = getFirstStackWhichCanMerge(stack);
        if (i == -1) {
            i = getFirstEmptyStack();
            if (i == -1)
                return false;
            else {
                mainInventory.set(i, stack);
                return true;
            }
        } else {
            mainInventory.set(i, stack);
            return true;
        }
    }

    public int getFirstEmptyStack() {
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (((ItemStack) this.mainInventory.get(i)).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    public int getFirstStackWhichCanMerge(ItemStack stack) {
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (this.mainInventory.get(i).getItem() == stack.getItem() && this.mainInventory.get(i).getMaxStackSize() >= (this.mainInventory.get(i).getCount() + stack.getCount())) {
                return i;
            }
        }

        return -1;
    }

}
