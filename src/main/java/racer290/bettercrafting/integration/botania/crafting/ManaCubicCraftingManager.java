package racer290.bettercrafting.integration.botania.crafting;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.integration.botania.crafting.BaseManaCubicCraftingRecipe.RealRuneConstellation;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;

public class ManaCubicCraftingManager {
	
	private Set<BaseManaCubicCraftingRecipe> untranslated;
	private Set<BaseManaCubicCraftingRecipe> recipes;
	
	public ManaCubicCraftingManager() {
		
		this.untranslated = Sets.newHashSet();
		this.recipes = Sets.newHashSet();
		
	}
	
	public void registerRecipe(BaseManaCubicCraftingRecipe recipe) {
		
		if (this.untranslated.stream().map(current -> current.getOutput().getItem()).anyMatch(item -> recipe.getOutput().getItem().equals(item))) return;
		
		this.untranslated.add(recipe);
		
		if (recipe instanceof ShapedManaCubicCraftingRecipe) {
			
			this.registerShapedRecipe((ShapedManaCubicCraftingRecipe) recipe);
			
		} else {
			
			this.registerShapelessRecipe((ShapelessManaCubicCraftingRecipe) recipe);
			
		}
		
	}
	
	public void registerShapedRecipe(ShapedManaCubicCraftingRecipe recipe) {
		
		for (ShapedManaCubicCraftingRecipe current : recipe.getAllTranslated()) {
			
			this.recipes.add(current);
			
		}
		
	}
	
	public void registerShapelessRecipe(ShapelessManaCubicCraftingRecipe recipe) {
		
		this.recipes.add(recipe);
		
	}
	
	public void registerAll(List<BaseManaCubicCraftingRecipe> recipes) {
		
		for (BaseManaCubicCraftingRecipe current : recipes) {
			
			this.registerRecipe(current);
			
		}
		
	}
	
	public @Nullable BaseManaCubicCraftingRecipe getRecipeForMatrix(CubicMatrix3x3<RealRuneConstellation> matrix) {
		
		for (BaseManaCubicCraftingRecipe current : this.recipes) {
			
			if (current.matches(matrix)) return current;
			
		}
		
		return null;
		
	}
	
	public void removeRecipe(@Nonnull ItemStack out) {
		
		if (out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("There can be no recipes with an empty output! Skipping ..");
			
			return;
			
		}
		
		Iterator<BaseManaCubicCraftingRecipe> it = this.recipes.iterator();
		
		while (it.hasNext()) {
			
			BaseManaCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.recipes.remove(current);
			}
			
		}
		
		it = this.untranslated.iterator();
		
		while (it.hasNext()) {
			
			BaseManaCubicCraftingRecipe current = it.next();
			
			if (current.getOutput().getItem() == out.getItem()) {
				this.untranslated.remove(current);
			}
			
		}
		
	}
	
	public ImmutableSet<BaseManaCubicCraftingRecipe> getRecipes() {
		
		return ImmutableSet.copyOf(this.recipes);
		
	}
	
	public ImmutableSet<BaseManaCubicCraftingRecipe> getRecipesUntranslated() {
		
		return ImmutableSet.copyOf(this.untranslated);
		
	}
	
}
