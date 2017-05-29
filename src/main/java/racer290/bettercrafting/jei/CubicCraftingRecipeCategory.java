package racer290.bettercrafting.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.block.BlockHelper;


public class CubicCraftingRecipeCategory extends BlankRecipeCategory<CubicCraftingRecipeWrapper> {
	
	private IDrawable background;
	
	public static final String UID = BetterCrafting.MODID;
	private String title;
	
	public CubicCraftingRecipeCategory(IGuiHelper guiHelper) {
		
		this.background = guiHelper.createBlankDrawable(168, 64);
		
		this.title = I18n.format(CubicCraftingRecipeCategory.UID + ".jei.title");
		
	}
	
	@Override
	public String getUid() {
		
		return CubicCraftingRecipeCategory.UID;
		
	}
	
	@Override
	public String getTitle() {
		
		return this.title;
		
	}
	
	@Override
	public IDrawable getBackground() {
		
		return this.background;
		
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(BlockHelper.blockCraftingResult), 100, 50);
		
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CubicCraftingRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
	}
	
}
