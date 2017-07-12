package racer290.bettercrafting.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;


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
	public boolean matches(CubicMatrix3x3<ItemStack> matrix) {
		
		ArrayList<Ingredient> missing = Lists.newArrayList(this.input);
		
		for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
					
					ItemStack stack = matrix.get(x, y, z);
					Iterator<Ingredient> it = missing.iterator();
					
					while (it.hasNext()) {
						
						if (it.next().matches(stack)) {
							it.remove();
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
