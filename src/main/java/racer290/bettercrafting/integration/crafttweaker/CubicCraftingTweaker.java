package racer290.bettercrafting.integration.crafttweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods." + BetterCrafting.MODID + "CubicCrafting")
public class CubicCraftingTweaker {
	
	@ZenMethod
	public static void addShaped(IItemStack output, IIngredient[][][] input) {
		
		CubicCraftingTweaker.addShaped(output, input, ShapedCubicCraftingRecipe.DEFAULT_TICKS_PER_OPERATION);
		
	}
	
	@ZenMethod
	public static void addShaped(IItemStack output, IIngredient[][][] input, int ticks) {
		
		MineTweakerAPI.apply(new Add(output, input, ticks));
		
	}
	
	public static void register() {
		
		MineTweakerAPI.registerClass(CubicCraftingTweaker.class);
		
	}
	
	private static class Add implements IUndoableAction {
		
		private ShapedCubicCraftingRecipe recipe;
		
		public Add(IItemStack ioutput, IIngredient[][][] iinput, int ticks) {
			
			Ingredient[][][] input = new Ingredient[ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH][ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH][ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH];
			
			for (int z = 0; z < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
				
				for (int y = 0; y < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
					
					for (int x = 0; x < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
						
						if (iinput[x][y][z] == null) {
							
							input[x][y][z] = Ingredient.EMPTY;
							
						} else if (iinput[x][y][z] instanceof IOreDictEntry) {
							
							input[x][y][z] = new Ingredient(((IOreDictEntry) iinput[x][y][z]).getName());
							
						} else if (iinput[x][y][z] instanceof IItemStack) {
							
							input[x][y][z] = new Ingredient(CubicCraftingTweaker.getStack((IItemStack) iinput[x][y][z]));
							
						} else throw new IllegalArgumentException(iinput[x][y][z].getClass() + " is not a permitted type for cubic crafting recipes!");
						
					}
					
				}
				
			}
			
			this.recipe = new ShapedCubicCraftingRecipe(input, CubicCraftingTweaker.getStack(ioutput), ticks, true);
			
		}
		
		@Override
		public void apply() {
			
			BetterCrafting.craftingManager.registerRecipe(this.recipe);
			MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(this.recipe);
			
		}
		
		@Override
		public boolean canUndo() {
			
			return true;
			
		}
		
		@Override
		public String describe() {
			
			return "Adding shaped cubic crafting recipe for output " + this.recipe.getOutput().getUnlocalizedName();
			
		}
		
		@Override
		public String describeUndo() {
			
			return "Undoing addition of the shaped cubic crafting recipe for output " + this.recipe.getOutput().getUnlocalizedName();
			
		}
		
		@Override
		public Object getOverrideKey() {
			
			return null;
			
		}
		
		@Override
		public void undo() {
			
			BetterCrafting.craftingManager.removeRecipe(this.recipe.getOutput());
			MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(this.recipe);
			
		}
		
	}
	
	public static ItemStack getStack(IItemStack istack) {
		
		if (istack == null) return ItemStack.EMPTY;
		
		Object stack = istack.getInternal();
		
		if (!(stack instanceof ItemStack)) {
			MineTweakerAPI.getLogger().logError(istack.getDisplayName() + " is not a valid ItemStack!");
		}
		
		return (ItemStack) stack;
		
	}
	
}