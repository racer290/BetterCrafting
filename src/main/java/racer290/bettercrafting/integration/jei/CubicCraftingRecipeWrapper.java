package racer290.bettercrafting.integration.jei;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.crafting.CubicCraftingRecipe;
import racer290.bettercrafting.crafting.CubicCraftingRecipe.Ingredient;


public class CubicCraftingRecipeWrapper extends BlankRecipeWrapper {
	
	private int ticks;
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	public CubicCraftingRecipeWrapper(CubicCraftingRecipe wrapped) {
		
		this.ticks = wrapped.getTicks();
		
		Builder<List<ItemStack>> builder = ImmutableList.builder();
		
		for (Ingredient[][] a : wrapped.getInputMatrix()) {
			
			for (Ingredient[] b : a) {
				
				for (Ingredient current : b) {
					
					builder.add(current.getAll());
					
				}
				
			}
			
		}
		
		this.input = builder.build();
		
		this.output = wrapped.getOutput().copy();
		
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInputLists(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		// TODO Draw recipe duration!
		
	}
	
}
