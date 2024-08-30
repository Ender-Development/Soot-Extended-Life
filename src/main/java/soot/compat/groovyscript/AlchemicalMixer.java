package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import net.minecraftforge.fluids.FluidStack;
import soot.Soot;
import soot.recipe.CraftingRegistry;
import soot.recipe.RecipeAlchemicalMixer;
import teamroots.embers.api.alchemy.AspectList;

import java.util.Arrays;

@RegistryDescription(linkGenerator = Soot.MODID)
public class AlchemicalMixer extends VirtualizedRegistry<RecipeAlchemicalMixer> {
    @RecipeBuilderDescription(example = {
            @Example(".fluidInput(fluid('water') * 10, fluid('lava') * 10, fluid('iron') * 16, fluid('gold') * 16).fluidOutput(fluid('dawnstone') * 16).setAspect('dawnstone', 1, 2).setAspect('copper', 2, 3)"),
    })
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        CraftingRegistry.alchemicalMixingRecipes.removeAll(removeScripted());
        CraftingRegistry.alchemicalMixingRecipes.addAll(restoreFromBackup());
    }

    public void add(RecipeAlchemicalMixer recipe) {
        if (recipe != null) {
            addScripted(recipe);
            CraftingRegistry.alchemicalMixingRecipes.add(recipe);
        }
    }

    public boolean remove(RecipeAlchemicalMixer recipe) {
        if (CraftingRegistry.alchemicalMixingRecipes.removeIf(r -> r == recipe)) {
            addBackup(recipe);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = {@Example("fluid('antimony')")})
    public boolean removeByOutput(IIngredient output) {
        return CraftingRegistry.alchemicalMixingRecipes.removeIf(r -> {
            if (output.test(r.getResult(r.getInput()))) {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<RecipeAlchemicalMixer> streamRecipes() {
        return new SimpleObjectStream<>(CraftingRegistry.alchemicalMixingRecipes).setRemover(this::remove);
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, priority = 2000, example = @Example(commented = true))
    public void removeAll() {
        CraftingRegistry.alchemicalMixingRecipes.forEach(this::addBackup);
        CraftingRegistry.alchemicalMixingRecipes.clear();
    }

    @Property(property = "fluidInput", valid = {@Comp(value = "2", type = Comp.Type.GTE), @Comp(value = "3", type = Comp.Type.LTE)})
    @Property(property = "fluidOutput", valid = {@Comp(value = "1")})
    public static class RecipeBuilder extends AbstractRecipeBuilder<RecipeAlchemicalMixer> {
        @Property(valid = @Comp(value = "empty", type = Comp.Type.NOT))
        private final AspectList.AspectRangeList aspects = new AspectList.AspectRangeList();

        @Override
        public String getErrorMsg() {
            return "Error adding Soot Alchemical Mixing recipe";
        }

        @RecipeBuilderMethodDescription(field = "aspects")
        public RecipeBuilder setAspect(String aspect, int min, int max) {
            this.aspects.setRange(aspect, min, max);
            return this;
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg);
            validateFluids(msg, 2, 4, 1, 1);
            msg.add(aspects.isEmpty(), "Aspects are required!");
            msg.add(fluidOutput.get(0).amount > 16, "Output fluid must be less than or equal to 16mb, but was {}mb.", fluidOutput.get(0).amount);
            msg.add(fluidInput.stream().anyMatch(fluid -> fluid.amount > 16), "Input fluids must be less than or equal to 16mb, but was {}mb.", Arrays.toString(fluidInput.stream().mapToInt(fluid -> fluid.amount).toArray()));
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable RecipeAlchemicalMixer register() {
            if (!validate()) return null;
            RecipeAlchemicalMixer recipe = new RecipeAlchemicalMixer(fluidInput.toArray(new FluidStack[0]), fluidOutput.get(0), aspects.fixMathematicalError().setSeedOffset(CraftingRegistry.alchemicalMixingRecipes.size()));
            GSPlugin.instance.alchemicalMixer.add(recipe);
            return recipe;
        }
    }
}
