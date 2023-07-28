package tterrag.difficultyrecipes.recipes;

import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tterrag.difficultyrecipes.DifficultyRecipes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapedDifficultyRecipe extends DifficultyRecipe<ShapedOreRecipe> {

    static {
        RecipeSorter.register(
            DifficultyRecipes.MODID + ":shaped",
            ShapedDifficultyRecipe.class,
            Category.SHAPED,
            "after:forge:shapedore");
    }

    @Override
    public Class<ShapedOreRecipe> getType() {
        return null;
    }

    public static Builder<ShapedOreRecipe> builder() {
        return new Builder<ShapedOreRecipe>(new ShapedDifficultyRecipe(), ShapedOreRecipe.class);
    }
}
