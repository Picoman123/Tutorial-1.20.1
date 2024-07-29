package net.picoman.tutorialmod.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.picoman.tutorialmod.TutorialMod;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    SAPPHIRE("sapphire", 26, new int[]{5, 7, 5, 4}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 1f,
            0f, () -> Ingredient.of(ModItems.SAPPHIRE.get())); //le nom "sapphire" dicte la manière d'écrire le nom du png dans le dossier models armor
    //pour créer nouvelle armure, il suffit de copier ce qu'il y a au-dessus
    //SAPPHIRE("sapphire", 26, new int[]{5, 7, 5, 4}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 1f,
    //            0f, () -> Ingredient.of(ModItems.SAPPHIRE.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float thougness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = thougness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getDurabilityForType(ArmorItem.Type type) { //type précise si c'est plastron, casque, etc
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier; //0 casque, 1 plastron, 2 ...
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return TutorialMod.MOD_ID + ":" + this.name; //si on met pas le TutorialMod.MOD_ID, la texture de l'armure va pas se stocker dans notre mod
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
