package dev.danielius.currency.datagen;

import dev.danielius.currency.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(
            HolderLookup.@NonNull Provider registries,
            @NonNull RecipeOutput output
    ) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.MISC, ModItems.COPPER_COIN, 4)
                        .pattern("nn")
                        .pattern("nn")
                        .define('n', Items.COPPER_NUGGET)
                        .unlockedBy(getHasName(Items.COPPER_NUGGET), has(Items.COPPER_NUGGET))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.IRON_COIN, 4)
                        .pattern("nn")
                        .pattern("nn")
                        .define('n', Items.IRON_NUGGET)
                        .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.GOLD_COIN, 4)
                        .pattern("nn")
                        .pattern("nn")
                        .define('n', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(Items.GOLD_NUGGET), has(Items.GOLD_NUGGET))
                        .save(output);
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return "ModRecipeProvider";
    }
}
