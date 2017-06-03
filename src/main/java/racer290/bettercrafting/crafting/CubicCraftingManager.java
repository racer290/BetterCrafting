package racer290.bettercrafting.crafting;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;

public class CubicCraftingManager {
	
	private Set<BaseCubicCraftingRecipe> recipes;
	private Set<BaseCubicCraftingRecipe> rawRecipes;
	
	public CubicCraftingManager() {
		
		this.rawRecipes = Sets.newConcurrentHashSet();
		this.recipes = Sets.newConcurrentHashSet();
		
	}
	
	public void registerRecipe(BaseCubicCraftingRecipe recipe) {
		
		if (this.recipes.stream().map(current -> current.getOutput().getItem()).anyMatch(item -> recipe.getOutput().getItem() == item)) return;
		
		this.recipes.add(recipe);
		
		if (recipe instanceof ShapedCubicCraftingRecipe) {
			
			this.registerShapedRecipe((ShapedCubicCraftingRecipe) recipe);
			
		} else {
			
		}
		
	}
	
	public void registerShapedRecipe(ShapedCubicCraftingRecipe recipe) {
		
		for (ShapedCubicCraftingRecipe current : recipe.getAllTranslated()) {
			
			this.rawRecipes.add(current);
			
		}
		
	}
	
	public void registerAll(List<BaseCubicCraftingRecipe> recipes) {
		
		for (BaseCubicCraftingRecipe current : recipes) {
			
			this.registerRecipe(current);
			
		}
		
	}
	
	public @Nullable BaseCubicCraftingRecipe getRecipeForMatrix(ItemStack[][][] matrix) {
		
		for (BaseCubicCraftingRecipe current : this.rawRecipes) {
			
			if (current.matches(matrix)) return current;
			
		}
		
		return null;
		
	}
	
	public void removeRecipe(@Nonnull ItemStack out) {
		
		Iterator<BaseCubicCraftingRecipe> it = this.rawRecipes.iterator();
		
		while (it.hasNext()) {
			
			BaseCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.rawRecipes.remove(current);
			}
			
		}
		
		it = this.recipes.iterator();
		
		while (it.hasNext()) {
			
			BaseCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.recipes.remove(current);
			}
			
		}
		
	}
	
	public ImmutableSet<BaseCubicCraftingRecipe> getRecipesRaw() {
		
		return ImmutableSet.copyOf(this.rawRecipes);
		
	}
	
	public ImmutableSet<BaseCubicCraftingRecipe> getRecipes() {
		
		return ImmutableSet.copyOf(this.recipes);
		
	}
	
}