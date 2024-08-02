
package net.picoman.tutorialmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.picoman.tutorialmod.TutorialMod;
import net.picoman.tutorialmod.block.ModBlocks;
import net.picoman.tutorialmod.recipe.GemPolishingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class GemPolishingCategory implements IRecipeCategory<GemPolishingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(TutorialMod.MOD_ID, "gem_polishing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/gui/gem_polishing_station_gui.png");

    public static final RecipeType<GemPolishingRecipe> GEM_POLISHING_TYPE = //faut bien un recipe type de mezz
            new RecipeType<>(UID, GemPolishingRecipe.class);

    private final IDrawable background; //quand on définit une variable comme final, il faut un constructeur
    private final IDrawable icon;

    public GemPolishingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85); //draw la texture GUI mais en plus petit
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.GEM_POLISHING_STATION.get())); //on crée l'icon qui sera utilisée dans la tab JEI
    }

    @Override
    public RecipeType<GemPolishingRecipe> getRecipeType() {
        return GEM_POLISHING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.tutorialmod.gem_polishing_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GemPolishingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addIngredients(recipe.getIngredients().get(0)); //x et y sont les coordonnées de l'input slot

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(recipe.getResultItem(null)); //x et y sont les coordonées de l'output slot
    }
}
