package com.buuz135.industrial.config.machine.mobfarming;

import com.buuz135.industrial.config.MachineAgricultureHusbandryConfig;
import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(MachineAgricultureHusbandryConfig.class)
public class MobCrusherConfig {

    @ConfigVal(comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [50 (2.5s)]")
    public static int maxProgress = 100;

    @ConfigVal(comment = "Amount of Power Consumed per Tick - Default: [40FE]")
    public static int powerPerOperation = 50;

    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;

    @ConfigVal(comment = "Max Essence [mb] - Default: [32000 mb]")
    public static int tankSize = 32000;

}
