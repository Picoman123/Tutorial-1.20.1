package net.picoman.tutorialmod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.picoman.tutorialmod.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.picoman.tutorialmod.entity.ai.RhinoAttackGoal;
import org.jetbrains.annotations.Nullable;

public class RhinoEntity extends Animal {
    private static final EntityDataAccessor<Boolean> ATTACKING = //boolean qui synchronise entre client et serveur quand on passe de true à false
            SynchedEntityData.defineId(RhinoEntity.class, EntityDataSerializers.BOOLEAN);

    public RhinoEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0; //pas oublier de bien l'égaler à 0, je sais pas pourquoi par contre

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) { //si on attaque et que le timeout est inférieur ou égal à 0
            attackAnimationTimeout = 80; //longueur de l'animation de l'attaque en tics
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) { //si on n'est plus en train d'attaquer
            attackAnimationState.stop(); //on arrête l'animation directement, on la laisse même pas finir
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false); //la valeur par défaut de notre boolean est false
    }

    @Override
    protected void registerGoals() { //représente l'intelligence artificielle de l'entité, peut devenir très complexe
        this.goalSelector.addGoal(0, new FloatGoal(this)); //plus le chiffre de priorité est bas plus c'est important
        //toujours ajouter un floatGoal sinon si l'entité se retrouve dans l'eau elle coule et se noie

        this.goalSelector.addGoal(1, new RhinoAttackGoal(this, 1.0D, true));

        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_BEEF), false)); //on peut faire en sorte que le dino suive si on a du boeuf cuit dans la main

        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D)); //le bébé suit le parent

        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //regarder les autres entités vanilla pour voir les différents goals qui existent

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this)); //définis ce que le rhino attaque quand il attaque. Ici, celui qui l'a blessé
    }

    public static AttributeSupplier.Builder createAttributes() { //si on oublie cette fonction l'entity ne peut pas spawn
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.FOLLOW_RANGE, 24D) //peut-être nécessaire de déclarer l'attribut, pour que l'enfant puisse suivre le parent
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 2f);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.RHINO.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.COOKED_BEEF); //il faut donner du boeuf cuit pour breed des dinos
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() { //il y a plein de sons à override, à voir ce qu'on veut faire
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }
}