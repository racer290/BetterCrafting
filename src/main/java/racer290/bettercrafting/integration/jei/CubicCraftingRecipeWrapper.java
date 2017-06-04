package racer290.bettercrafting.integration.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;
import racer290.bettercrafting.crafting.ShapelessCubicCraftingRecipe;

public class CubicCraftingRecipeWrapper extends BlankRecipeWrapper {
	
	private int ticks;
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
	private boolean shapeless;
	
	public CubicCraftingRecipeWrapper(ShapedCubicCraftingRecipe wrapped) {
		
		this.ticks = wrapped.getTicks();
		
		ArrayList<List<ItemStack>> in = Lists.newArrayList();
		
		for (Ingredient[][] a : wrapped.getInputMatrix()) {
			
			for (Ingredient[] b : a) {
				
				for (Ingredient current : b) {
					
					in.add(current.getAll());
					
				}
				
			}
			
		}
		
		this.input = in;
		
		this.output = wrapped.getOutput().copy();
		
	}
	
	public CubicCraftingRecipeWrapper(ShapelessCubicCraftingRecipe recipe) {
		
		this.input = new ArrayList<>();
		
		for (Ingredient in : recipe.getInput()) {
			
			this.input.add(in.getAll());
			
		}
		
		this.output = recipe.getOutput();
		this.ticks = recipe.getTicks();
		this.shapeless = true;
		
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInputLists(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		FontRenderer font = minecraft.fontRendererObj;
		
		font.drawString(this.ticks + " ticks", 0, 10, Color.GRAY.getRGB());
		if (this.shapeless) font.drawString("Shapeless", 10, 10, Color.GRAY.getRGB());
		
	}
	
}
