package me.bubbles.lives.items.manager;

import me.bubbles.lives.Lives;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Item {

    public Lives plugin;
    private String nbtIdentifier;
    private net.minecraft.world.item.ItemStack nmsStack;
    private ShapedRecipe recipe;
    private ItemStack itemStack;

    public Item(Lives plugin, Material material, String nbtIdentifier) {
        this.plugin=plugin;
        this.itemStack=new ItemStack(material);
        itemStack.setAmount(1);
        this.nbtIdentifier=nbtIdentifier;
        this.nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsStack.v();
        nbtTagCompound.a("livesItem",nbtIdentifier);
        nmsStack.c(nbtTagCompound);
    }

    public void onRegister() {

    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(ShapedRecipe recipe) {
        this.recipe = recipe;
    }

    public net.minecraft.world.item.ItemStack getNMSStack() {
        return nmsStack;
    }

    public void setNMSStack(net.minecraft.world.item.ItemStack nmsStack) {
        this.nmsStack = nmsStack;
    }

    public void setNMSStack(ItemStack itemStack) {
        this.nmsStack=CraftItemStack.asNMSCopy(itemStack);
    }

    public ItemStack nmsAsItemStack() {
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public String getNBTIdentifier() {
        return nbtIdentifier;
    }

    public boolean onEvent(Event event) {
        return false;
    }

    public boolean equals(ItemStack itemStack) {
        NBTTagCompound nbtTagCompound = CraftItemStack.asNMSCopy(itemStack).v();
        String name = nbtTagCompound.l("livesItem");
        return name.equals(nbtIdentifier);
    }

}