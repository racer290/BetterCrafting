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

public class CubicCraftingRecipeWrapper extends BlankRecipeWrapper {
	
	private int ticks;
	
	private List<List<ItemStack>> input;
	private ItemStack output;
	
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
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInputLists(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		FontRenderer font = minecraft.fontRendererObj;
		
		font.drawString(this.ticks + " ticks", 0, 10, Color.GRAY.getRGB());
		
	}
	
}
