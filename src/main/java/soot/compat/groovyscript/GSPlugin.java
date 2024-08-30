package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import soot.Soot;

public class GSPlugin implements GroovyPlugin {
    @GroovyBlacklist
    public static GSContainer instance;

    @Override
    public @Nullable GroovyPropertyContainer createGroovyPropertyContainer() {
        GSPlugin.instance = new GSContainer();
        return GSPlugin.instance;
    }

    @Override
    public String getModId() {
        return Soot.MODID;
    }

    @Override
    public String getContainerName() {
        return Soot.NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container) {
        LinkGeneratorHooks.registerLinkGenerator(new SootLinkGenerator());
    }
}
