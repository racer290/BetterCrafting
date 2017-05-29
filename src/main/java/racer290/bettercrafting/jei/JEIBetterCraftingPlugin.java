package racer290.bettercrafting.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.block.BlockHelper;

@JEIPlugin
public class JEIBetterCraftingPlugin extends BlankModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHelper.blockCraftingResult), CubicCraftingRecipeCategory.UID);
		
	}
	
}
