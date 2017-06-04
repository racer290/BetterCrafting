package racer290.bettercrafting;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import racer290.bettercrafting.block.BlockHelper;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;
import racer290.bettercrafting.crafting.ShapelessCubicCraftingRecipe;

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
		
		this.registerRecipes();
		
	}
	
	public void postInit(FMLPostInitializationEvent evt) {
		
	}
	
	public void registerRecipes() {
		
		ArrayList<BaseCubicCraftingRecipe> recipes = new ArrayList<>();
		
		Ingredient glass = new Ingredient(Blocks.GLASS);
		Ingredient obby = new Ingredient(Blocks.OBSIDIAN);
		Ingredient star = new Ingredient(Items.NETHER_STAR);
		
		Ingredient[][][] matrix = new Ingredient[][][] {
			
			{ { obby, obby, obby }, { obby, obby, obby }, { obby, obby, obby } },
			
			{ { glass, glass, glass }, { glass, star, glass }, { glass, glass, glass } },
			
			{ { glass, glass, glass }, { glass, glass, glass }, { glass, glass, glass } }
			
		};
		
		recipes.add(new ShapedCubicCraftingRecipe(matrix, new ItemStack(Blocks.BEACON)));
		
		Ingredient[] list = new Ingredient[] {
				
				new Ingredient("gemEmerald"), new Ingredient(Items.GOLDEN_APPLE), new Ingredient(Blocks.GOLD_BLOCK)
				
		};
		
		recipes.add(new ShapelessCubicCraftingRecipe(list, new ItemStack(Items.TOTEM)));
		
		BetterCrafting.craftingManager.registerAll(recipes);
		
		Iterator<IRecipe> it = CraftingManager.getInstance().getRecipeList().iterator();
		
		while (it.hasNext()) {
			
			IRecipe next = it.next();
			
			if (next.getRecipeOutput().getItem() == Item.getItemFromBlock(Blocks.BEACON)) {
				
				it.remove();
				break;
				
			}
			
		}
		
		BetterCrafting.LOGGER.info("Recipes registered, the beacon recipe has been tweaked!");
		
	}
	
}
