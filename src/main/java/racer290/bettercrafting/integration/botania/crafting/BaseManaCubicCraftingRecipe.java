package racer290.bettercrafting.integration.botania.crafting;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;
import vazkii.botania.common.item.ModItems;

public abstract class BaseManaCubicCraftingRecipe {
	
	protected int ticks;
	protected int mana;
	protected ItemStack output;
	
	public abstract boolean matches(CubicMatrix3x3<RealRuneConstellation> matrix);
	
	public ItemStack getOutput() {
		
		return this.output.copy();
		
	}
	
	public int getTicks() {
		
		return this.ticks;
		
	}
	
	public int getRequiredMana() {
		
		return this.mana;
		
	}
	
	public static class RecipeRuneConstellation {
		
		private Ingredient ing;
		private Integer runeMeta;
		
		public static final RecipeRuneConstellation EMPTY = new RecipeRuneConstellation(Ingredient.EMPTY, -1);
		
		public RecipeRuneConstellation(Ingredient ing, int rune) {
			
			if (ing == Ingredient.EMPTY && rune != -1) throw new IllegalArgumentException("Can't assign a non-empty rune to an empty ItemStack! Rune meta: " + rune);
			
			this.ing = ing;
			
			if (rune < -1 || rune > 15) throw new IllegalArgumentException("Invalid rune metadata: " + rune);
			
			this.runeMeta = rune;
			
		}
		
		public Ingredient getIngredient() {
			
			return this.ing;
			
		}
		
		public Integer getRune() {
			
			return this.runeMeta;
			
		}
		
		public boolean isEmpty() {
			
			return this.getIngredient().matches(ItemStack.EMPTY);
			
		}
		
		public boolean matches(RealRuneConstellation rc) {
			
			return this.isEmpty() ? rc.isEmpty() : this.ing.matches(rc.getStack()) && rc.getRuneMeta() == this.runeMeta;
			
		}
		
	}
	
	public static class RealRuneConstellation {
		
		private ItemStack stack;
		private ItemStack rune;
		
		public RealRuneConstellation(ItemStack stack, ItemStack rune) {
			
			if (!rune.isEmpty() && rune.getItem() != ModItems.rune) throw new IllegalArgumentException("The rune stack must be a rune (surprise, surprise)! Item found: " + rune.getItem().getRegistryName());
			
			this.stack = stack;
			this.rune = rune;
			
		}
		
		public ItemStack getStack() {
			
			return this.stack;
			
		}
		
		public Integer getRuneMeta() {
			
			return this.rune.isEmpty() ? -1 : this.rune.getMetadata();
			
		}
		
		public boolean isEmpty() {
			
			return this.stack.isEmpty() && this.rune.isEmpty();
			
		}
		
	}
	
}
