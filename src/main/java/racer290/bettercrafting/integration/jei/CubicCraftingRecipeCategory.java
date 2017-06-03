package racer290.bettercrafting.integration.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.crafting.CubicCraftingRecipe;

public class CubicCraftingRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {
	
	private IDrawable background;
	
	public static final String UID = BetterCrafting.MODID;
	private String title;
	
	public CubicCraftingRecipeCategory(IGuiHelper guiHelper) {
		
		this.background = guiHelper.createDrawable(new ResourceLocation(BetterCrafting.MODID, "textures/gui/jei_background.png"), 0, 0, 114, 232, 114, 232);
		
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
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
		if (!(recipeWrapper instanceof CubicCraftingRecipeWrapper)) return;
		
		List<List<ItemStack>> recipeIngredients = ingredients.getInputs(ItemStack.class);
		
		for (int layer = 0; layer < CubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; layer++) {
			
			int baseSlot = layer * 9;
			int baseY = 30 + (2 - layer) * 68;
			
			recipeLayout.getItemStacks().init(0 + baseSlot, true, 48, baseY);
			recipeLayout.getItemStacks().set(0 + baseSlot, recipeIngredients.get(0 + baseSlot));
			recipeLayout.getItemStacks().init(1 + baseSlot, true, 24, baseY + 12);
			recipeLayout.getItemStacks().set(1 + baseSlot, recipeIngredients.get(1 + baseSlot));
			recipeLayout.getItemStacks().init(2 + baseSlot, true, 0, baseY + 24);
			recipeLayout.getItemStacks().set(2 + baseSlot, recipeIngredients.get(2 + baseSlot));
			recipeLayout.getItemStacks().init(3 + baseSlot, true, 72, baseY + 12);
			recipeLayout.getItemStacks().set(3 + baseSlot, recipeIngredients.get(3 + baseSlot));
			recipeLayout.getItemStacks().init(4 + baseSlot, true, 48, baseY + 24);
			recipeLayout.getItemStacks().set(4 + baseSlot, recipeIngredients.get(4 + baseSlot));
			recipeLayout.getItemStacks().init(5 + baseSlot, true, 24, baseY + 36);
			recipeLayout.getItemStacks().set(5 + baseSlot, recipeIngredients.get(5 + baseSlot));
			recipeLayout.getItemStacks().init(6 + baseSlot, true, 96, baseY + 24);
			recipeLayout.getItemStacks().set(6 + baseSlot, recipeIngredients.get(6 + baseSlot));
			recipeLayout.getItemStacks().init(7 + baseSlot, true, 72, baseY + 36);
			recipeLayout.getItemStacks().set(7 + baseSlot, recipeIngredients.get(7 + baseSlot));
			recipeLayout.getItemStacks().init(8 + baseSlot, true, 48, baseY + 48);
			recipeLayout.getItemStacks().set(8 + baseSlot, recipeIngredients.get(7 + baseSlot));
			
		}
		
		recipeLayout.getItemStacks().init(27, false, 48, 0);
		recipeLayout.getItemStacks().set(27, ingredients.getOutputs(ItemStack.class).get(0));
		
	}
	
}
