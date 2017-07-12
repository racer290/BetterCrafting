package racer290.bettercrafting.integration.botania.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;

public class ShapelessManaCubicCraftingRecipe extends BaseManaCubicCraftingRecipe {
	
	private List<RecipeRuneConstellation> input;
	
	public ShapelessManaCubicCraftingRecipe(RecipeRuneConstellation[] in, ItemStack out, int mana) {
		
		this(in, out, mana, BaseCubicCraftingRecipe.DEFAULT_TICKS_PER_OPERATION);
		
	}
	
	public ShapelessManaCubicCraftingRecipe(RecipeRuneConstellation[] in, ItemStack out, int mana, int ticks) {
		
		if (ticks < 1) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid duration, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (mana < 1) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid amount of mana, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (out == null || out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with empty output, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		RecipeRuneConstellation[] inno = new RecipeRuneConstellation[27];
		Arrays.fill(inno, RecipeRuneConstellation.EMPTY);
		
		int i = 0;
		
		for (int inpointer = 0; inpointer < in.length; inpointer++) {
			
			if (in[inpointer] == RecipeRuneConstellation.EMPTY) {
				
				BetterCrafting.LOGGER.warn("Encountered empty element during shapeless recipe construction! Skipping ..");
				
			} else {
				
				inno[i] = in[inpointer];
				
				i++;
				
			}
			
		}
		
		this.input = Arrays.asList(inno);
		this.output = out;
		this.ticks = ticks;
		this.mana = mana;
		
	}
	
	@Override
	public boolean matches(CubicMatrix3x3<RealRuneConstellation> matrix) {
		
		ArrayList<RecipeRuneConstellation> missing = Lists.newArrayList(this.input);
		
		for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					RealRuneConstellation rrc = matrix.get(x, y, z);
					Iterator<RecipeRuneConstellation> it = missing.iterator();
					
					while (it.hasNext()) {
						
						if (it.next().matches(rrc)) {
							
							it.remove();
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public ArrayList<RecipeRuneConstellation> getInput() {
		
		return new ArrayList<>(this.input);
		
	}
	
}
