
package net.picoman.tutorialmod.entity.ai;

import net.picoman.tutorialmod.entity.custom.RhinoEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class RhinoAttackGoal extends MeleeAttackGoal {
    private final RhinoEntity entity;
    private int attackDelay = 40; //délai entre le début de l'animation et l'attaque réelle (voir dans Blockbench)
    private int ticksUntilNextAttack = 40; //ce qui reste entre l'attaque et la fin de l'animation
    private boolean shouldCountTillNextAttack = false;

    public RhinoAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((RhinoEntity) pMob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 40;
        ticksUntilNextAttack = 40;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true; //on commence le count si l'ennemi est proche

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true); //lance l'animation
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay * 2); //parce qu'il faut 40 tics jusqu'à l'attaque suivante, que le cycle se fasse (on attaque à deux secondes de l'animation)
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0); //countdown qui compte jusque 0 et reste à 0 quand on est à 0
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
