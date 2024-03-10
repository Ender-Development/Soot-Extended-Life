package soot.handler;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import soot.config.ConfigSoot;
import soot.config.Sulfur_Ore;
import soot.world.SulfurGenerator;

import java.util.Random;

public class GenerationHandler {
    static SulfurGenerator sulfurGenerator = new SulfurGenerator();

    @SubscribeEvent
    public static void onGenerateOres(OreGenEvent.Post event) {
        Random random = event.getRand();
        World world = event.getWorld();
        if(ConfigSoot.SULFUR_ORE.isSulfurEnabled(world.provider.getDimension())) {
            BlockPos chunkPos = event.getPos();
            int x = random.nextInt(16) + 8;
            int y = ConfigSoot.SULFUR_ORE.sulfurMinY + random.nextInt(ConfigSoot.SULFUR_ORE.sulfurMaxY - ConfigSoot.SULFUR_ORE.sulfurMinY + 1);
            int z = random.nextInt(16) + 8;
            BlockPos blockpos2 = chunkPos.add(x, y, z);
            sulfurGenerator.generate(world, random, blockpos2);
        }
    }
}
