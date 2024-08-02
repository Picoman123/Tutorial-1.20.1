package net.picoman.tutorialmod.event;

import net.picoman.tutorialmod.TutorialMod;
import net.picoman.tutorialmod.block.entity.ModBlockEntities;
import net.picoman.tutorialmod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import net.picoman.tutorialmod.entity.client.ModModelLayers;
import net.picoman.tutorialmod.entity.client.RhinoModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


//cette classe concerne les évènements moddés

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)  //on utilise le modbus et pas le bus de forge //on met Dist.client parce que ça s'affiche que du côté client pour afficher les textures
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.GEM_POLISHING_BE.get(), GemPolishingBlockEntityRenderer::new);
    }
}