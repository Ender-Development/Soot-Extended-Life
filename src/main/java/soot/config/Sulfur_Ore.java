package soot.config;

import net.minecraftforge.common.config.Config;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sulfur_Ore {
    @Config.RequiresMcRestart
    @Config.Name("Sulfur Blacklist")
    @Config.Comment("A list of all dimension IDs in which sulfur orespawn is prohibited. Sulfur ores will spawn in any dimension not on this list, but only in vanilla stone.")
    public int[] sulfurBlacklist = new int[]{-1, 1};

    @Config.RequiresMcRestart
    @Config.Name("Sulfur Blacklist Is Whitelist")
    @Config.Comment("Whether the sulfur blacklist is a whitelist.")
    public boolean sulfurBlacklistIsWhitelist = false;

    @Config.RequiresMcRestart
    @Config.Name("Sulfur Min Y")
    @Config.Comment("Minimum height over which sulfur ore will spawn.")
    @Config.RangeInt(min = 0, max = 255)
    public int sulfurMinY = 0;

    @Config.RequiresMcRestart
    @Config.Name("Sulfur Max Y")
    @Config.Comment("Maximum height under which sulfur ore will spawn.")
    @Config.RangeInt(min = 0, max = 255)
    public int sulfurMaxY = 32;

    @Config.RequiresMcRestart
    @Config.Name("Sulfur Veins Per Chunk")
    @Config.Comment("Number of attempts to spawn copper ore the world generator will make for each chunk.")
    public int sulfurVeinsPerChunk = 3;

    public boolean isSulfurEnabled(int dimension) {
        boolean checkDimension = IntStream.of(this.sulfurBlacklist).boxed().collect(Collectors.toList()).contains(dimension);
        return !(checkDimension != this.sulfurBlacklistIsWhitelist || checkDimension);
    }
}
