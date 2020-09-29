package com.buuz135.industrial.block.mobfarming.tile;

import com.buuz135.industrial.block.tile.IndustrialAreaWorkingTile;
import com.buuz135.industrial.block.tile.RangeManager;
import com.buuz135.industrial.config.machine.mobfarming.MobDuplicatorConfig;
import com.buuz135.industrial.item.MobImprisonmentToolItem;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.module.ModuleMobFarming;
import com.buuz135.industrial.utils.BlockUtils;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.stream.Collectors;

public class MobDuplicatorTile extends IndustrialAreaWorkingTile<MobDuplicatorTile> {

    private final Method GET_EXPERIENCE_POINTS = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "func_70693_a", PlayerEntity.class);
    private final Method DROP_SPECIAL_ITEMS = ObfuscationReflectionHelper.findMethod(MobEntity.class, "func_213333_a", DamageSource.class, int.class, boolean.class);

    @Save
    private SidedInventoryComponent<MobDuplicatorTile> _input;
    @Save
    private SidedFluidTankComponent<MobDuplicatorTile> _tank;

    private final static int FIND_BLOCK_TRY_COUNT = 19;

    @Save
    private boolean _dropXP;


    public MobDuplicatorTile() {
        super(ModuleMobFarming.MOB_DUPLICATOR, RangeManager.RangeType.TOP_UP, true);

        if (!GET_EXPERIENCE_POINTS.isAccessible()) GET_EXPERIENCE_POINTS.setAccessible(true);
        if (!DROP_SPECIAL_ITEMS.isAccessible()) DROP_SPECIAL_ITEMS.setAccessible(true);

        this._dropXP = true;


        this.addTank(_tank = (SidedFluidTankComponent<MobDuplicatorTile>) new SidedFluidTankComponent<MobDuplicatorTile>("essence", MobDuplicatorConfig.tankSize, 43, 20, 0)
                .setColor(DyeColor.LIME)
                .setTankAction(FluidTankComponent.Action.FILL)
                .setComponentHarness(this)
                .setValidator(fluidStack -> fluidStack.getFluid().isEquivalentTo(ModuleCore.ESSENCE.getSourceFluid())));

        this.addInventory(_input = (SidedInventoryComponent<MobDuplicatorTile>) new SidedInventoryComponent<MobDuplicatorTile>("input", 64, 22, 1, 1)
                .setColor(DyeColor.ORANGE)
                .setRange(1, 1)
                .setInputFilter((stack, size) -> stack.getItem() instanceof MobImprisonmentToolItem)
                .setComponentHarness(this));


    }

    @Override
    public WorkAction work() {
        if (_input.getStackInSlot(0).isEmpty() || _tank.getFluid() == null || !hasEnergy(MobDuplicatorConfig.powerPerOperation))
            return new WorkAction(1, 0);

        VoxelShape workArea = getWorkingArea();
        List<MobEntity> mobs = this.world.getEntitiesWithinAABB(MobEntity.class, workArea.getBoundingBox()).stream().filter(mob -> !mob.isInvulnerable() && mob.isNonBoss()).collect(Collectors.toList());

        if (mobs.size() > MobDuplicatorConfig.maxMobsInWA)
            return new WorkAction(1, 0);

        ItemStack mobToolStack = _input.getStackInSlot(0);
        LivingEntity toSpawn = getEntityToSpawn(mobToolStack);

        int requiredEssence = (int) Math.ceil(toSpawn.getHealth() * 2);
        int canSpawn = ((_tank.getFluid().getAmount()) / requiredEssence);

        if (canSpawn <= 0)
            return new WorkAction(1, 0);

        int spawnAmount = 1 + this.world.rand.nextInt(Math.min(canSpawn, 4));
        List<BlockPos> blockPositions = BlockUtils.getBlockPosInAABB(workArea.getBoundingBox());

        for (int i = spawnAmount; i >= 0; i--) {
            FluidStack essence = _tank.getFluid();

            if (essence != null && essence.getAmount() > requiredEssence) {
                BlockPos spawnOn = null;

                for (int tries = FIND_BLOCK_TRY_COUNT; spawnOn == null || tries >= 0 && !this.world.isAirBlock(spawnOn); tries--)
                    spawnOn = blockPositions.get(this.world.rand.nextInt(blockPositions.size()));

                if (spawnOn == null)
                    continue; // RIP That spawn.

                // We call it again in order to change the reference to the entity ?
                toSpawn = getEntityToSpawn(mobToolStack);
                toSpawn.setUniqueId(UUID.randomUUID());
                toSpawn.onAddedToWorld();

                toSpawn.setPosition(spawnOn.getX() + 0.5, spawnOn.getY(), spawnOn.getZ() + 0.5);
                this.world.addEntity(toSpawn);

                _tank.drain(requiredEssence, IFluidHandler.FluidAction.EXECUTE);
            }
        }

        return new WorkAction(1, MobDuplicatorConfig.powerPerOperation);
    }

    private LivingEntity getEntityToSpawn(ItemStack stack) {
        return (LivingEntity) ((MobImprisonmentToolItem) _input.getStackInSlot(0).getItem()).getEntityFromStack(stack, world, false);
    }

    @Nonnull
    @Override
    public MobDuplicatorTile getSelf() {
        return this;
    }

    @Nonnull
    @Override
    protected EnergyStorageComponent<MobDuplicatorTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(MobDuplicatorConfig.maxStoredPower, 10, 20);
    }

    @Override
    public int getMaxProgress() {
        return MobDuplicatorConfig.maxProgress;
    }
}
