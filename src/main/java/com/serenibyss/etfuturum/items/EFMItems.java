package com.serenibyss.etfuturum.items;

import com.serenibyss.etfuturum.EFMTags;
import com.serenibyss.etfuturum.items.base.EFMItem;
import com.serenibyss.etfuturum.items.base.EFMItem.Settings;
import com.serenibyss.etfuturum.load.feature.Feature;
import com.serenibyss.etfuturum.util.IModelRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

import static com.serenibyss.etfuturum.load.feature.Features.*;

public enum EFMItems {

    PHANTOM_MEMBRANE(MC13.phantom, "phantom_membrane", new EFMItem(new Settings().creativeTab(CreativeTabs.BREWING).translationKey("phantom_membrane"))),
    TRIDENT(MC13.trident, "trident", new ItemTrident()),

    ;

    private final Feature feature;
    private final String myName;
    private final Item myItem;

    EFMItems(Feature feature, String myName, Item myItem) {
        this.feature = feature;
        this.myName = myName;
        this.myItem = myItem;
    }

    public boolean isEnabled() {
        return feature.isEnabled();
    }

    @Nullable
    public Item getItem() {
        if (isEnabled()) {
            return myItem;
        }
        return null;
    }

    public ItemStack getItemStack() {
        return getItemStack(1, 0);
    }

    public ItemStack getItemStack(int count) {
        return getItemStack(count, 0);
    }

    public ItemStack getItemStack(int count, int meta) {
        if (isEnabled()) {
            return new ItemStack(myItem, count, meta);
        }
        return ItemStack.EMPTY;
    }

    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();
        for (EFMItems value : values()) {
            if (value.isEnabled()) {
                value.myItem.setRegistryName(new ResourceLocation(EFMTags.MODID, value.myName));
                r.register(value.myItem);
            }
        }
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        for (EFMItems value : values()) {
            if (value.isEnabled() && value.myItem instanceof IModelRegister item) {
                item.registerModel();
            }
        }
    }
}
