package soot.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import soot.Soot;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.stream.Collectors;

public class LateMixin implements ILateMixinLoader {
    public static final List<String> modMixins = ImmutableList.of(
    );

    @Override
    public List<String> getMixinConfigs() {
        return modMixins.stream().filter(Loader::isModLoaded).map(mod -> "mixin."+ Soot.MODID +"." + mod + ".json").collect(Collectors.toList());
    }
}
