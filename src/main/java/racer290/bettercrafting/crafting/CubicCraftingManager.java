package racer290.bettercrafting.crafting;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;

public class CubicCraftingManager {
	
	private Set<CubicCraftingRecipe> recipes;
	
	public CubicCraftingManager() {
		
		this.recipes = Sets.newConcurrentHashSet();
		
	}
	
	public void registerRecipe(@Nonnull CubicCraftingRecipe recipe) {
		
		Set<CubicCraftingRecipe> translated = recipe.getAllTranslated();
		
		for (CubicCraftingRecipe current : translated) {
			
			this.recipes.add(current);
			
		}
		
	}
	
	public void registerAll(List<CubicCraftingRecipe> recipes) {
		
		for (CubicCraftingRecipe current : recipes) {
			
			this.registerRecipe(current);
			
		}
		
	}
	
	public @Nullable CubicCraftingRecipe getRecipeForMatrix(ItemStack[][][] matrix) {
		
		if (matrix == null) return null;
		
		for (CubicCraftingRecipe current : this.recipes) {
			
			if (CubicCraftingManager.recipeMatches(current, matrix)) return current;
			
		}
		
		return null;
		
	}
	
	public void removeRecipe(@Nonnull CubicCraftingRecipe recipe) {
		
		this.recipes.remove(recipe);
		
	}
	
	public static boolean recipeMatches(CubicCraftingRecipe recipe, ItemStack[][][] matrix) {
		
		for (int z = 0; z < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					if (!recipe.getInputMatrix()[x][y][z].matches(matrix[x][y][z])) return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public ImmutableSet<CubicCraftingRecipe> getRecipes() {
		
		return ImmutableSet.copyOf(this.recipes);
		
	}
	
}