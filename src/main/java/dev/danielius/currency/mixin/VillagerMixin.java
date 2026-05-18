package dev.danielius.currency.mixin;

import dev.danielius.currency.item.ModItems;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Villager.class)
public class VillagerMixin {
	@Inject(at = @At("TAIL"), method = "updateTrades")
	private void replaceTradeCurrency(CallbackInfo info) {
		Villager villager = (Villager)(Object)this;
		MerchantOffers offers = villager.getOffers();

		for (int i = 0; i < offers.size(); i++) {
			MerchantOffer offer = offers.get(i);

			ItemCost costA = offer.getItemCostA();
			Optional<ItemCost> costB = offer.getItemCostB();
			ItemStack result = offer.getResult();

			if (costA.itemStack().is(Items.EMERALD)) {
				int emeralds = costA.itemStack().getCount();

				costA = convertCurrency(emeralds);
			}

			if (costB.isPresent() && costB.get().itemStack().is(Items.EMERALD)) {
				int emeralds = costB.get().itemStack().getCount();

				costB = Optional.ofNullable(convertCurrency(emeralds));
			}

			if (result.is(Items.EMERALD)) {
				int emeralds = result.getCount();

				result = convertCurrency(emeralds).itemStack();
			}

			offer = new MerchantOffer(
					costA,
					costB,
					result,
					offer.getUses(),
					offer.getMaxUses(),
					offer.getXp(),
					offer.getPriceMultiplier()
			);

			offers.set(i, offer);
		}
	}

	@Unique
	private ItemCost convertCurrency(int emeralds) {
		Item coin;
		int minMultiplier;
		int maxMultiplier;

		if (emeralds <= 8) {
			coin = ModItems.COPPER_COIN;
			minMultiplier = 4;
			maxMultiplier = 8;
		} else if (emeralds <= 16) {
			coin = ModItems.IRON_COIN;
			minMultiplier = 1;
			maxMultiplier = 4;
		} else if (emeralds <= 32) {
			coin = ModItems.GOLD_COIN;
			minMultiplier = 1;
			maxMultiplier = 2;
		} else {
			coin = ModItems.GOLD_COIN;
			minMultiplier = 1;
			maxMultiplier = 1;
		}

		int multiplier = (int)(Math.random() * (minMultiplier + 1)) + (maxMultiplier - minMultiplier);
		return new ItemCost(coin, emeralds * multiplier);
	}
}
