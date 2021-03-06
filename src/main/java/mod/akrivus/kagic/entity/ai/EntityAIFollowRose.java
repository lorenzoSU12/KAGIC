package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityPepo;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowRose extends EntityAIBase {
	private final EntityPepo pepo;
	private EntityRoseQuartz gem;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowRose(EntityPepo pepoIn, double followSpeedIn) {
        this.pepo = pepoIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
    	this.gem = this.pepo.getMaster();
        return this.gem != null;
    }
    public boolean continueExecuting() {
        return this.gem != null && !this.pepo.getNavigator().noPath();
    }
    public void startExecuting() {
        this.oldWaterCost = this.pepo.getPathPriority(PathNodeType.WATER);
        this.pepo.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    public void resetTask() {
        this.gem = null;
        this.pepo.getNavigator().clearPathEntity();
        this.pepo.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    public void updateTask() {
        if (this.pepo.getDistanceSqToEntity(this.gem) > (this.gem.width * 3) + 3) {
        	this.pepo.getNavigator().tryMoveToEntityLiving(this.gem, this.followSpeed);
        }
    }
}