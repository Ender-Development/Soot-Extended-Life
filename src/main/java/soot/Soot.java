package soot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import soot.config.ConfigSoot;
import soot.handler.*;
import soot.network.PacketHandler;
import soot.projectiles.ProjectileFireBlast;
import soot.recipe.CraftingRegistry;
import soot.util.Attributes;
import teamroots.embers.Embers;
import teamroots.embers.register.ItemRegister;

@Mod(modid = Soot.MODID, name = Soot.NAME, acceptedMinecraftVersions = Soot.VERSIONS, dependencies = Soot.DEPENDENCIES)
@Mod.EventBusSubscriber
public class Soot {
    @Mod.Instance(Soot.MODID)
    public static Soot instance;

    public static final String MODID = "soot";
    public static final String NAME = "Soot";
    public static final String DEPENDENCIES = "required-after:embers@[1.23.0,);after:mixinbooter@[8.0,);";
    public static final String VERSIONS = Embers.VERSIONS;

    public static Logger log;

    @SidedProxy(clientSide = "soot.ClientProxy", serverSide = "soot.ServerProxy")
    public static IProxy proxy;

    public static CreativeTabs creativeTab;

    @EventHandler
    public void construct(FMLConstructionEvent event) {
        proxy.registerResourcePack();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        creativeTab = new CreativeTabs("soot") {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ItemRegister.DUST_ASH);
            }
        };
        CraftingRegistry.preInit();
        Registry.preInit();
        proxy.preInit();
        MinecraftForge.EVENT_BUS.register(Attributes.class);
        MinecraftForge.EVENT_BUS.register(FluidStitchHandler.class);
        MinecraftForge.EVENT_BUS.register(AnvilHandler.class);
        MinecraftForge.EVENT_BUS.register(FuelHandler.class);
        MinecraftForge.EVENT_BUS.register(WitchburnHandler.class);
        MinecraftForge.EVENT_BUS.register(EitrHandler.class);
        if (ConfigSoot.TRADING_ANTIMONY)
            MinecraftForge.EVENT_BUS.register(VillagerAntimonyHandler.class);
        if (ConfigSoot.GOLEMS_POISON_IMMUNE || ConfigSoot.GOLEMS_TYRFING_WEAK)
            MinecraftForge.EVENT_BUS.register(GolemHandler.class);
        MinecraftForge.ORE_GEN_BUS.register(GenerationHandler.class);
        MinecraftForge.EVENT_BUS.register(new MigrationHandler());
        MinecraftForge.EVENT_BUS.register(ProjectileFireBlast.class);
        PacketHandler.registerMessages();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Registry.init();
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Registry.postInit();
        proxy.postInit();
    }
}
