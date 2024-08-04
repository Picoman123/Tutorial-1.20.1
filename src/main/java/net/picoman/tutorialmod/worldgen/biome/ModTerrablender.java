package net.picoman.tutorialmod.worldgen.biome;

import net.picoman.tutorialmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(TutorialMod.MOD_ID, "overworld"), 5)); //plus le weight est grand plus le biome a de chances de remplacer le biome forêt
    }
}
