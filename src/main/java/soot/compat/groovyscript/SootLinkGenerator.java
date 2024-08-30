package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import soot.Soot;

public class SootLinkGenerator extends BasicLinkGenerator {
    @Override
    public String id() {
        return Soot.MODID;
    }

    @Override
    public String domain() {
        return "https://github.com/Ender-Development/Soot-Extended-Life/";
    }
}
