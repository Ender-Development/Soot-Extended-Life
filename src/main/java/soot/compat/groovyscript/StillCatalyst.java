package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.Example;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import soot.Soot;
import soot.recipe.CatalystInfo;
import soot.recipe.CraftingRegistry;

import java.util.Arrays;

@RegistryDescription(linkGenerator = Soot.MODID)
public class StillCatalyst extends VirtualizedRegistry<CatalystInfo> {
    @Override
    @GroovyBlacklist
    public void onReload() {
        CraftingRegistry.stillCatalysts.removeAll(removeScripted());
        CraftingRegistry.stillCatalysts.addAll(restoreFromBackup());
    }

    public void add(CatalystInfo recipe) {
        if (recipe != null) {
            addScripted(recipe);
            CraftingRegistry.stillCatalysts.add(recipe);
        }
    }

    public boolean remove(CatalystInfo recipe) {
        if (CraftingRegistry.stillCatalysts.removeIf(r -> r == recipe)) {
            addBackup(recipe);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, description = "groovyscript.wiki.soot.still_catalyst.removecatalyst", example = @Example("ore('logWood')"))
    public boolean removeCatalyst(IIngredient input) {
        return CraftingRegistry.stillCatalysts.removeIf(r -> {
            if (Arrays.stream(input.getMatchingStacks()).anyMatch(r::matches)) {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.ADDITION, description = "groovyscript.wiki.soot.still_catalyst.addcatalyst", example = {@Example("item('minecraft:iron_ingot'), 600"), @Example("item('minecraft:glass'), 50")})
    public boolean addCatalyst(IIngredient item, int amount) {
        if (CraftingRegistry.stillCatalysts.stream().anyMatch(r -> r.matches(item.getMatchingStacks()[0])))
            return false;
        add(new CatalystInfo(item.toMcIngredient(), amount));
        return true;
    }

    @MethodDescription(type = MethodDescription.Type.VALUE, description = "groovyscript.wiki.soot.still_catalyst.getamount", example = @Example("item('minecraft:snowball')"))
    public int getAmount(IIngredient item) {
        return CraftingRegistry.getStillCatalyst(item.toMcIngredient().getMatchingStacks()[0]);
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<CatalystInfo> streamCatalysts() {
        return new SimpleObjectStream<>(CraftingRegistry.stillCatalysts).setRemover(this::remove);
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, priority = 2000, example = @Example(commented = true))
    public void removeAll() {
        CraftingRegistry.stillCatalysts.forEach(this::addBackup);
        CraftingRegistry.stillCatalysts.clear();
    }
}
