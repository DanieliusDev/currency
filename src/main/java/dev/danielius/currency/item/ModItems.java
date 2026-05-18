package dev.danielius.currency.item;

import dev.danielius.currency.Currency;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final Item COPPER_COIN = register("copper_coin", Item::new, new Item.Properties());
    public static final Item IRON_COIN = register("iron_coin", Item::new, new Item.Properties());
    public static final Item GOLD_COIN = register("gold_coin", Item::new, new Item.Properties());

    public static <T extends Item> T register(
            String name,
            Function<Item.Properties, T> itemFactory,
            Item.Properties properties
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(Currency.MOD_ID, name);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        T item = itemFactory.apply(properties.setId(itemKey));
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(creativeTab -> {
                    creativeTab.accept(COPPER_COIN);
                    creativeTab.accept(IRON_COIN);
                    creativeTab.accept(GOLD_COIN);
                });
    }
}
