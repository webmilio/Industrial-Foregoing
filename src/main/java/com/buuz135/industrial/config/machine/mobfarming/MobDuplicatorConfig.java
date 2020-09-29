package com.buuz135.industrial.config.machine.mobfarming;

import com.buuz135.industrial.config.MachineMobFarmingConfig;
import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile.Child(MachineMobFarmingConfig.class)
public class MobDuplicatorConfig {
    @ConfigVal(comment = "Cooldown Time in Ticks [20 Ticks per Second] - Default: [50 (2.5s)]")
    public static int maxProgress = 100;

    @ConfigVal(comment = "Amount of Power Consumed per Tick - Default: [5000 FE]")
    public static int powerPerOperation = 5000;

    @ConfigVal(comment = "Max Stored Power [FE] - Default: [55000 FE]")
    public static int maxStoredPower = 55000;

    @ConfigVal(comment = "Max Essence [mb] - Default: [8000 mb]")
    public static int tankSize = 8000;

    @ConfigVal(comment = "Amount of mobs in the working area before the duplicator stops spawning entities - Default: 20")
    public static int maxMobsInWA = 20;
}
