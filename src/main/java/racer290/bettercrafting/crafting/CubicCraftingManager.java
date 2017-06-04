package racer290.bettercrafting.crafting;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;

public class CubicCraftingManager {
	
	private Set<BaseCubicCraftingRecipe> untranslated;
	private Set<BaseCubicCraftingRecipe> recipes;
	
	public CubicCraftingManager() {
		
		this.recipes = Sets.newConcurrentHashSet();
		this.untranslated = Sets.newConcurrentHashSet();
		
	}
	
	public void registerRecipe(BaseCubicCraftingRecipe recipe) {
		
		if (this.untranslated.stream().map(current -> current.getOutput().getItem()).anyMatch(item -> recipe.getOutput().getItem().equals(item))) return;
		
		this.untranslated.add(recipe);
		
		if (recipe instanceof ShapedCubicCraftingRecipe) {
			
			this.registerShapedRecipe((ShapedCubicCraftingRecipe) recipe);
			
		} else {
			
			this.registerShapelessRecipe((ShapelessCubicCraftingRecipe) recipe);
			
		}
		
	}
	
	public void registerShapedRecipe(ShapedCubicCraftingRecipe recipe) {
		
		for (ShapedCubicCraftingRecipe current : recipe.getAllTranslated()) {
			
			this.recipes.add(current);
			
		}
		
	}
	
	public void registerShapelessRecipe(ShapelessCubicCraftingRecipe recipe) {
		
		this.recipes.add(recipe);
		
	}
	
	public void registerAll(List<BaseCubicCraftingRecipe> recipes) {
		
		for (BaseCubicCraftingRecipe current : recipes) {
			
			this.registerRecipe(current);
			
		}
		
	}
	
	public @Nullable BaseCubicCraftingRecipe getRecipeForMatrix(ItemStack[][][] matrix) {
		
		for (BaseCubicCraftingRecipe current : this.recipes) {
			
			if (current.matches(matrix)) return current;
			
		}
		
		return null;
		
	}
	
	public void removeRecipe(@Nonnull ItemStack out) {
		
		if (out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("There can be no recipes with an empty output! Skipping ..");
			
			return;
			
		}
		
		Iterator<BaseCubicCraftingRecipe> it = this.recipes.iterator();
		
		while (it.hasNext()) {
			
			BaseCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.recipes.remove(current);
			}
			
		}
		
		it = this.untranslated.iterator();
		
		while (it.hasNext()) {
			
			BaseCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.untranslated.remove(current);
			}
			
		}
		
	}
	
	public ImmutableSet<BaseCubicCraftingRecipe> getRecipes() {
		
		return ImmutableSet.copyOf(this.recipes);
		
	}
	
	public ImmutableSet<BaseCubicCraftingRecipe> getRecipesUntranslated() {
		
		return ImmutableSet.copyOf(this.untranslated);
		
	}
	
}