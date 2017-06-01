package racer290.bettercrafting.integration.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;

public class CubicCraftingRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {
	
	private IDrawable background;
	
	public static final String UID = BetterCrafting.MODID;
	private String title;
	
	public CubicCraftingRecipeCategory(IGuiHelper guiHelper) {
		
		this.background = guiHelper.createBlankDrawable(112, 230);
		
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
		
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
		if (!(recipeWrapper instanceof CubicCraftingRecipeWrapper)) return;
		
		List<List<ItemStack>> recipeIngredients = ingredients.getInputs(ItemStack.class);
		
		for (int i = 0; i < recipeIngredients.size(); i++) {
			
			recipeLayout.getItemStacks().init(i, true, 20 + 10 * i, 10 + 5 * i);
			recipeLayout.getItemStacks().set(i, recipeIngredients.get(i));
			
		}
		
		recipeLayout.getItemStacks().init(27, false, 30, 48);
		recipeLayout.getItemStacks().set(27, ingredients.getOutputs(ItemStack.class).get(0));
		
	}
	
}
