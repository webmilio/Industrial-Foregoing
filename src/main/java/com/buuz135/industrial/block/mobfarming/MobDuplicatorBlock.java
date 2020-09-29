package com.buuz135.industrial.block.mobfarming;

import com.buuz135.industrial.block.IndustrialBlock;
import com.buuz135.industrial.block.mobfarming.tile.MobDuplicatorTile;
import com.buuz135.industrial.module.ModuleAgricultureHusbandry;
import com.buuz135.industrial.module.ModuleMobFarming;
import com.buuz135.industrial.utils.IndustrialTags;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MobDuplicatorBlock extends IndustrialBlock<MobDuplicatorTile> {

    public MobDuplicatorBlock() {
        super("mob_duplicator", Properties.from(Blocks.IRON_BLOCK), MobDuplicatorTile.class, ModuleMobFarming.TAB_MOB_FARMING);
    }

    @Override
    public IFactory<MobDuplicatorTile> getTileEntityFactory() {
        return MobDuplicatorTile::new;
    }

    @Nonnull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public void registerRecipe(Consumer<IFinishedRecipe> consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe(this)
                .patternLine("PNP").patternLine("MFM").patternLine("ERE")
                .key('P', IndustrialTags.Items.PLASTIC)
                .key('N', Items.NETHER_WART_BLOCK)
                .key('M', Items.MAGMA_CREAM)
                .key('F', IndustrialTags.Items.MACHINE_FRAME_ADVANCED)
                .key('E', Items.EMERALD)
                .key('R', Items.REDSTONE)
                .build(consumer);
    }
}
