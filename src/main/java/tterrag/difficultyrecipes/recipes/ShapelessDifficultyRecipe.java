package tterrag.difficultyrecipes.recipes;

import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tterrag.difficultyrecipes.DifficultyRecipes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapelessDifficultyRecipe extends DifficultyRecipe<ShapelessOreRecipe> {

    static {
        RecipeSorter.register(
            DifficultyRecipes.MODID + ":shapeless",
            ShapelessDifficultyRecipe.class,
            Category.SHAPED,
            "after:forge:shapelessore");
    }

    @Override
    public Class<ShapelessOreRecipe> getType() {
        return null;
    }

    public static Builder<ShapelessOreRecipe> builder() {
        return new Builder<ShapelessOreRecipe>(new ShapelessDifficultyRecipe(), ShapelessOreRecipe.class);
    }
}
