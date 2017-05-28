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
			
			boolean alive = true;
			
			for (int z = 0; z < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
				
				for (int y = 0; y < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
					
					for (int x = 0; x < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
						
						if (!this.stacksMatch(current.getInputMatrix()[x][y][z], matrix[x][y][z])) {
							alive = false;
						}
						
					}
					
				}
				
			}
			
			if (alive) return current;
			
		}
		
		return null;
		
	}
	
	public void removeRecipe(@Nonnull CubicCraftingRecipe recipe) {
		
		this.recipes.remove(recipe);
		
	}
	
	public boolean stacksMatch(ItemStack stack, ItemStack anotherStack) {
		
		if (stack.isEmpty() && anotherStack.isEmpty()) return true;
		
		return stack.getItem() == anotherStack.getItem() && stack.getItemDamage() == anotherStack.getItemDamage();
		
	}
	
	public ImmutableSet<CubicCraftingRecipe> getRecipes() {
		
		return ImmutableSet.copyOf(this.recipes);
		
	}
	
}