package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;

public class GSContainer extends GroovyPropertyContainer {
    public final AlchemicalMixer alchemicalMixer = new AlchemicalMixer();
    public final Still still = new Still();
    public final StillCatalyst stillCatalyst = new StillCatalyst();

    public GSContainer() {
        addProperty(alchemicalMixer);
        addProperty(still);
        addProperty(stillCatalyst);
    }
}
