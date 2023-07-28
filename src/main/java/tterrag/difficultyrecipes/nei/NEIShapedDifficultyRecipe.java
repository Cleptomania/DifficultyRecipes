package tterrag.difficultyrecipes.nei;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.google.common.collect.Lists;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.ShapedRecipeHandler;
import tterrag.difficultyrecipes.DifficultyRecipes;
import tterrag.difficultyrecipes.recipes.DifficultyRecipe;
import tterrag.difficultyrecipes.util.Difficulty;

public class NEIShapedDifficultyRecipe extends ShapedRecipeHandler {

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("difficultyrecipes.shaped.name");
    }

    private List<DifficultyRecipe<ShapedOreRecipe>> cached = Lists.newArrayList();

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting")) {
            for (DifficultyRecipe<?> rec : DifficultyRecipe.allRecipes) {
                CachedShapedRecipe cached = null;
                if (rec.getType() == ShapedOreRecipe.class) {
                    DifficultyRecipe<ShapedOreRecipe> recipe = (DifficultyRecipe<ShapedOreRecipe>) rec;

                    cached = forgeShapedRecipe(
                        ((DifficultyRecipe<ShapedOreRecipe>) rec)
                            .getRecipe(rec.getDifficulty(Minecraft.getMinecraft().theWorld)));

                    if (cached == null) continue;

                    cached.computeVisuals();
                    arecipes.add(cached);
                    this.cached.add(recipe);
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (DifficultyRecipe<?> rec : DifficultyRecipe.allRecipes) {
            if (rec.getType() == ShapedOreRecipe.class) {
                DifficultyRecipe<ShapedOreRecipe> recipe = (DifficultyRecipe<ShapedOreRecipe>) rec;
                Difficulty diff = recipe.getDifficulty(Minecraft.getMinecraft().theWorld);
                ShapedOreRecipe irecipe = recipe.getRecipe(diff);
                if (irecipe != null && NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                    CachedShapedRecipe cached = null;
                    cached = forgeShapedRecipe(recipe.getRecipe(diff));
                    if (cached == null) continue;
                    cached.computeVisuals();
                    arecipes.add(cached);
                    this.cached.add(recipe);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (DifficultyRecipe<?> rec : DifficultyRecipe.allRecipes) {
            if (rec.getType() == ShapedOreRecipe.class) {
                DifficultyRecipe<ShapedOreRecipe> recipe = (DifficultyRecipe<ShapedOreRecipe>) rec;
                CachedShapedRecipe cached = null;
                cached = forgeShapedRecipe(recipe.getRecipe(recipe.getDifficulty(Minecraft.getMinecraft().theWorld)));

                if (cached == null || !cached.contains(cached.ingredients, ingredient.getItem())) continue;

                cached.computeVisuals();
                if (cached.contains(cached.ingredients, ingredient)) {
                    cached.setIngredientPermutation(cached.ingredients, ingredient);
                    arecipes.add(cached);
                    this.cached.add(recipe);
                }
            }
        }
    }

    @Override
    public CachedShapedRecipe forgeShapedRecipe(ShapedOreRecipe recipe) {
        return recipe == null ? null : super.forgeShapedRecipe(recipe);
    }

    @Override
    public void drawExtras(int recipeIndex) {
        super.drawExtras(recipeIndex);
        DifficultyRecipe<?> recipe = cached.get(recipeIndex);
        Difficulty diff = recipe.getDifficulty(Minecraft.getMinecraft().theWorld);
        String s = diff.getLocName();
        StringBuilder sb = new StringBuilder();
        Collection<Difficulty> dupes = DifficultyRecipe.getDuplicatedRecipes(recipe, diff);
        if (!dupes.isEmpty()) {
            for (Difficulty e : dupes) {
                sb.append(e.getLocName());
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            String s2 = I18n.format(DifficultyRecipes.MODID + ".gui.also", sb.toString());
            GuiDraw.drawStringC(s2, 83, 60, 0x666666, false);
        }
        GuiDraw.drawStringC(s, 127, 10, DifficultyRecipe.getColorFor(diff), false);
    }
}
