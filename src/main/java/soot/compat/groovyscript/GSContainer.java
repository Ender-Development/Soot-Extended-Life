package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;

public class GSContainer extends GroovyPropertyContainer {
    public final AlchemicalMixer alchemicalMixer = new AlchemicalMixer();

    public GSContainer() {
        addProperty(alchemicalMixer);
    }
}
