package soot.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import soot.Soot;
import soot.recipe.CraftingRegistry;
import soot.recipe.RecipeStill;

import java.util.Arrays;

@RegistryDescription(linkGenerator = Soot.MODID)
public class Still extends VirtualizedRegistry<RecipeStill> {
    @RecipeBuilderDescription(example = {
            @Example(".fluidInput(fluid('water') * 10).input(item('minecraft:iron_ingot')).fluidOutput(fluid('lava') * 10).catalystConsumed(10)"),
            @Example(".fluidInput(fluid('iron') * 10).fluidOutput(fluid('copper') * 2)")
    })
    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        CraftingRegistry.stillRecipes.removeAll(removeScripted());
        CraftingRegistry.stillRecipes.addAll(restoreFromBackup());
    }

    public void add(RecipeStill recipe) {
        if (recipe != null) {
            addScripted(recipe);
            CraftingRegistry.stillRecipes.add(recipe);
        }
    }

    public boolean remove(RecipeStill recipe) {
        if (CraftingRegistry.stillRecipes.removeIf(r -> r == recipe)) {
            addBackup(recipe);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("fluid('boiling_wort')"))
    public boolean removeByInput(IIngredient input) {
        return CraftingRegistry.stillRecipes.removeIf(r -> {
            if (input.test(r.getInputs().get(0))) {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("fluid('inner_fire')"))
    public boolean removeByOutput(IIngredient output) {
        return CraftingRegistry.stillRecipes.removeIf(r -> {
            if (output.test(r.getOutputs().get(0))) {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<RecipeStill> streamRecipes() {
        return new SimpleObjectStream<>(CraftingRegistry.stillRecipes).setRemover(this::remove);
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, priority = 2000, example = @Example(commented = true))
    public void removeAll() {
        CraftingRegistry.stillRecipes.forEach(this::addBackup);
        CraftingRegistry.stillRecipes.clear();
    }

    @Property(property = "input", valid = {@Comp(value = "0", type = Comp.Type.GTE), @Comp(value = "1", type = Comp.Type.LTE)})
    @Property(property = "fluidInput", valid = @Comp("1"))
    @Property(property = "fluidOutput", valid = @Comp("1"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<RecipeStill> {
        @Override
        public String getErrorMsg() {
            return "Error adding Soot Still recipe";
        }

        @Property(defaultValue = "1", valid = {@Comp(value = "1", type = Comp.Type.GTE), @Comp(value = "1000", type = Comp.Type.LTE)}, value = "groovyscript.wiki.soot.still.catalyst_consumed.value")
        private int catalystConsumed = 1;

        @RecipeBuilderMethodDescription(field = "catalystConsumed")
        public RecipeBuilder catalystConsumed(int catalystConsumed) {
            this.catalystConsumed = catalystConsumed;
            return this;
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 0, 1, 0, 0);
            validateFluids(msg, 1, 1, 1, 1);
            msg.add(!input.isEmpty() && Arrays.stream(input.get(0).getMatchingStacks()).anyMatch(itemStack -> itemStack.getCount() > 1), "Input must be a single item");
            msg.add(catalystConsumed < 1 || catalystConsumed > 1000, "Catalyst consumed must be between 1 and 1000");
        }

        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable RecipeStill register() {
            if (!validate()) return null;
            RecipeStill recipe = new RecipeStill(new ResourceLocation(Soot.MODID, "brew_" + CraftingRegistry.stillRecipes.size()), fluidInput.get(0), !input.isEmpty() ? input.get(0).toMcIngredient() : Ingredient.EMPTY, !input.isEmpty() ? catalystConsumed : 0, fluidOutput.get(0));
            GSPlugin.instance.still.add(recipe);
            return recipe;
        }
    }
}
