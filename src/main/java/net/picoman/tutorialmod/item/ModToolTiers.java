package net.picoman.tutorialmod.item;

import net.picoman.tutorialmod.TutorialMod;
import net.picoman.tutorialmod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier SAPPHIRE = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25, //le premier paramètres de Forge tier caractérise le tier d'outil (netherite c'est 4 donc 5 c'est mieux)
                    ModTags.Blocks.NEEDS_SAPPHIRE_TOOL, () -> Ingredient.of(ModItems.SAPPHIRE.get())),
            new ResourceLocation(TutorialMod.MOD_ID, "sapphire"), List.of(Tiers.NETHERITE), List.of()); //list of (tiers.NETHERITE) sert à préciser le tier juste en-dessous et le deuxième list.of() à préciser le tier au-dessus mais ici y'a rien de mieux

}