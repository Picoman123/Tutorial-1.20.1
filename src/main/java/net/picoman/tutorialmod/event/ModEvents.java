/*package net.picoman.tutorialmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.picoman.tutorialmod.TutorialMod;
import net.picoman.tutorialmod.item.ModItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID) //pour faire en sorte que forge reconnaisse la classe comme une eventclass
public class ModEvents {
//attention les trades avec les villageois n'apparaissent pas avec une proba de 100% comme dans minecraft de base
    @SubscribeEvent //il faut toujours ça pour un event
    public static void addCustomTrades(VillagerTradesEvent event) { //il faut toujours ça pour un event, le nom de la fonction et de l'event changent bien sûr
        if(event.getType() == VillagerProfession.FARMER) { //si on a affaire à un farmer
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades(); //toujours mettre ça

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer( //le int correspond au niveau du trader, va de 1 à 5
                    new ItemStack(Items.EMERALD, 2),//items qu'il faut donner au villageois
                    new ItemStack(ModItems.STRAWBERRY.get(), 12), //ce qu'on reçoit
                    10, 8, 0.02f)); //max uses, xp, price multiplier

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.CORN.get(), 6),
                    5, 9, 0.035f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 8),
                    new ItemStack(ModItems.CORN_SEEDS.get(), 2),
                    2, 12, 0.075f));
        }

        if(event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.THORNS, 2)); //createForEnchantment permet de créer des livres enchantés facilement

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    enchantedBook,
                    2, 8, 0.02f));
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) { //pour ajouter des trades au wandering trader, les wandering trader n'ont pas de niveaux
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 12),
                new ItemStack(ModItems.SAPPHIRE_BOOTS.get(), 1),
                3, 2, 0.2f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(ModItems.METAL_DETECTOR.get(), 1),
                2, 12, 0.15f));
    }
}*/