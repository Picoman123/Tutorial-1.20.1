package net.picoman.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import net.picoman.tutorialmod.TutorialMod;
import net.picoman.tutorialmod.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, TutorialMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, TutorialMod.MOD_ID);

    public static final RegistryObject<PoiType> SOUND_POI = POI_TYPES.register("sound_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SOUND_BLOCK.get().getStateDefinition().getPossibleStates()), //getPossibleStates fait en sorte que le job peut être acquis peut importe l'état du bloc de métier
                    1, 1)); //maxtickets = nombre de villageois qui peuvent prendre la profession, validrange la distance max pour prendre le job
//attention on peut pas créer des nouveaux poitypes pour des blocs qui sont déjà utilisés pour des villageois. Check dans poitype pour voir ce qui est utilisé. Sinon on peut faire du mixin mais pas utilisé dans le tuto
    public static final RegistryObject<VillagerProfession> SOUND_MASTER =
            VILLAGER_PROFESSIONS.register("soundmaster", () -> new VillagerProfession("soundmaster",
                    holder -> holder.get() == SOUND_POI.get(), holder -> holder.get() == SOUND_POI.get(), //le double holder sert à dire qu'il acquiert son job au SOUND_POI et y travaille aussi. Le SOUND_POI correspond au soundblock
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}