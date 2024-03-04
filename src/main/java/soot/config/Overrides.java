package soot.config;

import net.minecraftforge.common.config.Config;

public class Overrides {
    @Config.RequiresMcRestart
    @Config.Name("Ember Bore")
    @Config.Comment("Overrides the Ember Bore")
    public boolean bore = false;

    @Config.RequiresMcRestart
    @Config.Name("Dawnstone Anvil")
    @Config.Comment("Overrides the Dawnstone Anvil")
    public boolean dawnstoneAnvil = false;

    @Config.RequiresMcRestart
    @Config.Name("Hearth Coil")
    @Config.Comment("Overrides the Hearth Coil")
    public boolean hearthCoil = false;

    @Config.RequiresMcRestart
    @Config.Name("Mixer")
    @Config.Comment("Overrides the Mixer Centrifuge")
    public boolean mixer = false;

    @Config.RequiresMcRestart
    @Config.Name("Stamper")
    @Config.Comment("Overrides the Stamper")
    public boolean stamper = false;

    @Config.RequiresMcRestart
    @Config.Name("Beam Cannon")
    @Config.Comment("Overrides the Beam Cannon")
    public boolean beamCannon = false;

    @Config.RequiresMcRestart
    @Config.Name("Alchemy Tablet")
    @Config.Comment("Overrides the Exchange Tablet")
    public boolean alchemyTablet = false;

    @Config.RequiresMcRestart
    @Config.Name("Mech Accessor")
    @Config.Comment("Overrides the Mech Accessor")
    public boolean mechAccessor = false;

    @Config.RequiresMcRestart
    @Config.Name("Alchemy Pedestal")
    @Config.Comment("Overrides the Alchemy Pedestal")
    public boolean alchemyPedestal = false;

    @Config.RequiresMcRestart
    @Config.Name("Crystal Cell")
    @Config.Comment("Overrides the Crystal Cell")
    public boolean crystalCell = false;
}
