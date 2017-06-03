package racer290.bettercrafting;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import racer290.bettercrafting.block.BlockHelper;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent evt) {
		
		BlockHelper.registerBlocks();
		
		try {
			
			BlockHelper.registerItemBlocks();
			
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			
			BetterCrafting.LOGGER.error("Something went wrong during initialization of the inventory block variants. Some blocks may not have a corresponding item.");
			BetterCrafting.LOGGER.catching(ex);
			
		}
		
	}
	
	public void init(FMLInitializationEvent evt) {
		
		this.registerCubicRecipes();
		
	}
	
	public void postInit(FMLPostInitializationEvent evt) {
		
	}
	
	public void registerCubicRecipes() {
		
		ArrayList<BaseCubicCraftingRecipe> recipes = new ArrayList<>();
		
		Ingredient dirt = new Ingredient(Blocks.DIRT);
		// Ingredient empty = new Ingredient(ItemStack.EMPTY);
		
		Ingredient[][][] matrix = new Ingredient[][][] {
			
			{ { dirt, dirt, dirt }, { dirt, dirt, dirt }, { dirt, dirt, dirt } },
			
			{ { dirt, dirt, dirt }, { dirt, dirt, dirt }, { dirt, dirt, dirt } },
			
			{ { dirt, dirt, dirt }, { dirt, dirt, dirt }, { dirt, dirt, dirt } }
			
		};
		
		recipes.add(new ShapedCubicCraftingRecipe(matrix, new ItemStack(Items.DIAMOND)));
		
		BetterCrafting.craftingManager.registerAll(recipes);
		
	}
	
}
