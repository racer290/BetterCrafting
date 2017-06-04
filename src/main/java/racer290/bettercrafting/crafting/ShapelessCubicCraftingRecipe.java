package racer290.bettercrafting.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;


public class ShapelessCubicCraftingRecipe extends BaseCubicCraftingRecipe {
	
	private List<Ingredient> input;
	
	public ShapelessCubicCraftingRecipe(Ingredient[] in, ItemStack out) {
		
		this(in, out, DEFAULT_TICKS_PER_OPERATION);
		
	}
	
	public ShapelessCubicCraftingRecipe(Ingredient[] in, ItemStack out, int ticksPerOperation) {
		
		if (ticksPerOperation < 1) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid duration, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (out == null || out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with empty output, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		Ingredient[] inno = new Ingredient[27];
		Arrays.fill(inno, Ingredient.EMPTY);
		
		int i = 0;
		
		for (int inpointer = 0; inpointer < in.length; inpointer++) {
			
			if (in[inpointer] == Ingredient.EMPTY) {
				
				BetterCrafting.LOGGER.warn("Encountered empty element during shapeless recipe construction! Skipping ..");
				
			} else {
				
				inno[i] = in[inpointer];
				
				i++;
				
			}
			
		}
		
		this.input = Arrays.asList(inno);
		this.output = out;
		this.ticks = ticksPerOperation;
		
	}
	
	@Override
	public boolean matches(ItemStack[][][] matrix) {
		
		ArrayList<Ingredient> missing = Lists.newArrayList(this.input);
		
		for (ItemStack[][] z : matrix) {
			
			for (ItemStack[] y : z) {
				
				for (ItemStack x : y) {
					
					Iterator<Ingredient> it = missing.iterator();
					
					while (it.hasNext()) {
						
						Ingredient next = it.next();
						
						if (next.matches(x)) {
							
							it.remove();
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return missing.size() > 0 ? false : true;
		
	}
	
	public List<Ingredient> getInput() {
		
		return new ArrayList<>(this.input);
		
	}
	
}
