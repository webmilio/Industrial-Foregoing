package com.buuz135.industrial.module;

import com.buuz135.industrial.block.mobfarming.MobCrusherBlock;
import com.buuz135.industrial.block.mobfarming.MobDuplicatorBlock;
import com.buuz135.industrial.block.mobfarming.SlaughterFactoryBlock;
import com.buuz135.industrial.registry.IFRegistries;
import com.buuz135.industrial.utils.Reference;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ModuleMobFarming implements IModule {

    public static final AdvancedTitaniumTab TAB_MOB_FARMING = new AdvancedTitaniumTab(Reference.MOD_ID + "_mob_farming", true);

    public static final MobCrusherBlock MOB_CRUSHER = new MobCrusherBlock();
    public static final SlaughterFactoryBlock SLAUGHTER_FACTORY = new SlaughterFactoryBlock();
    public static final MobDuplicatorBlock MOB_DUPLICATOR = new MobDuplicatorBlock();

    @Override
    public List<Feature.Builder> generateFeatures() {
        List<Feature.Builder> builders = new ArrayList<>();

        builders.add(Feature.builder("mob_farming")
                .event(EventManager.forge(RegistryEvent.NewRegistry.class).process(registry -> IFRegistries.poke())));

        builders.add(createFeature(MOB_CRUSHER));
        builders.add(createFeature(SLAUGHTER_FACTORY));
        builders.add(createFeature(MOB_DUPLICATOR));

        TAB_MOB_FARMING.addIconStack(new ItemStack(MOB_CRUSHER));
        return builders;
    }
}
