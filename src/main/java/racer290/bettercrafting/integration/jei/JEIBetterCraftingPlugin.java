package racer290.bettercrafting.integration.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.block.BlockHelper;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;

@JEIPlugin
public class JEIBetterCraftingPlugin extends BlankModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		registry.addRecipeCategories(new CubicCraftingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		
		registry.handleRecipes(ShapedCubicCraftingRecipe.class, new IRecipeWrapperFactory<ShapedCubicCraftingRecipe>() {
			
			@Override
			public IRecipeWrapper getRecipeWrapper(ShapedCubicCraftingRecipe recipe) {
				
				return new CubicCraftingRecipeWrapper(recipe);
				
			}
			
		}, CubicCraftingRecipeCategory.UID);
		
		registry.addRecipes(BetterCrafting.craftingManager.getRecipes(), CubicCraftingRecipeCategory.UID);
		
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHelper.blockCraftingResult), CubicCraftingRecipeCategory.UID);
		
	}
	
}
