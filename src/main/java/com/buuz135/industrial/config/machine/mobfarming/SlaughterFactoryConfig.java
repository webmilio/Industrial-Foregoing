package com.buuz135.industrial.config.machine.mobfarming;

import com.buuz135.industrial.config.MachineAgricultureHusbandryConfig;
import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(MachineAgricultureHusbandryConfig.class)
public class SlaughterFactoryConfig {

    @ConfigVal(comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [100 (5s)]")
    public static int maxProgress = 100;

    @ConfigVal(comment = "Amount of Power Consumed per Operation - Default: [400FE]")
    public static int powerPerOperation = 400;

    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;

    @ConfigVal(comment = "Max Amount of Stored Fluid - Default: [8000mB]")
    public static int maxMeatTankSize = 8000;

    @ConfigVal(comment = "Max Amount of Stored Fluid - Default: [8000mB]")
    public static int maxPinkSlimeTankSize = 8000;

}
