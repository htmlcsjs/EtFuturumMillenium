package com.serenibyss.etfuturum.recipes;

import com.serenibyss.etfuturum.EFMTags;
import com.serenibyss.etfuturum.api.StrippingRegistry;
import com.serenibyss.etfuturum.blocks.EFMBlocks;
import com.serenibyss.etfuturum.load.feature.Features;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.RecipeSorter;

import static com.serenibyss.etfuturum.recipes.RecipeHelpers.addShapedRecipe;

public class EFMRecipes {

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onRegisterRecipe(RegistryEvent.Register<IRecipe> event) {
        RecipeSorter.register(EFMTags.MODID + ":shaped", EFMShapedRecipe.class, RecipeSorter.Category.SHAPED, "before:minecraft:shaped");
        RecipeSorter.register(EFMTags.MODID + ":shapeless", EFMShapelessRecipe.class, RecipeSorter.Category.SHAPELESS, "before:minecraft:shapeless");

        additions();
        if (Features.MC14.stonecutter.isEnabled()) {
            StonecutterRecipes.init();
        }
        if (Features.MC13.stripping.isEnabled()) {
            strippingRecipes();
        }
    }

    private static void additions() {
        addShapedRecipe("barrel", EFMBlocks.BARREL.getItemStack(), "XSX", "X X", "XSX", 'X', "plankWood", 'S', "slabWood");
        addShapedRecipe("stonecutter", EFMBlocks.STONECUTTER.getItemStack(), " X ", "SSS", 'X', "ingotIron", 'S', new ItemStack(Blocks.STONE));

        addStairsRecipe("stone_stairs", EFMBlocks.STONE_STAIRS.getItemStack(), new ItemStack(Blocks.STONE));

        // todo fix 1.12 vanilla stone slab recipe
        addSlabRecipe("stone_slab", EFMBlocks.STONE_SLAB.getItemStack(), new ItemStack(Blocks.STONE));

        // todo: Uncomment when Nautilus Shell and Heart of the Sea are added
        //addShapedRecipe("conduit", EFMBlocks.CONDUIT.getItemStack(), "XXX", "XSX", "XXX", 'X', EFMItems.NAUTILUS_SHELL.getItemStack(), 'S', EFMItems.HEART_OF_THE_SEA.getItemStack());
    }

    @SuppressWarnings("deprecation")
    private static void strippingRecipes() {
        StrippingRegistry r = StrippingRegistry.instance();
        Block stripped_log_1 = EFMBlocks.STRIPPED_LOG_1.getBlock();
        Block stripped_log_2 = EFMBlocks.STRIPPED_LOG_2.getBlock();

        if (stripped_log_1 == null || stripped_log_2 == null) {
            return;
        }

        // states from logs directly map to stripped_log
        for (int i = 0; i <= 11; i++) {
            r.registerConversion(Blocks.LOG.getStateFromMeta(i), stripped_log_1.getStateFromMeta(i));
        }

        r.registerConversion(Blocks.LOG2.getStateFromMeta(0), stripped_log_2.getStateFromMeta(0));
        r.registerConversion(Blocks.LOG2.getStateFromMeta(1), stripped_log_2.getStateFromMeta(1));
        r.registerConversion(Blocks.LOG2.getStateFromMeta(4), stripped_log_2.getStateFromMeta(4));
        r.registerConversion(Blocks.LOG2.getStateFromMeta(5), stripped_log_2.getStateFromMeta(5));
        r.registerConversion(Blocks.LOG2.getStateFromMeta(8), stripped_log_2.getStateFromMeta(8));
        r.registerConversion(Blocks.LOG2.getStateFromMeta(9), stripped_log_2.getStateFromMeta(9));
    }
}
