package racer290.bettercrafting.crafting;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;

public abstract class BaseCubicCraftingRecipe {
	
	protected ItemStack output;
	protected int ticks;
	
	public static final int DEFAULT_MATRIX_LENGTH = 3;
	public static int DEFAULT_TICKS_PER_OPERATION = 200;
	
	public abstract boolean matches(CubicMatrix3x3<ItemStack> matrix);
	
	public ItemStack getOutput() {
		
		return this.output;
		
	}
	
	public int getTicks() {
		
		return this.ticks;
		
	}
	
	public static CubicMatrix3x3<Ingredient> nullMatrix() {
		
		return new CubicMatrix3x3<>(Ingredient.EMPTY);
		
	}
	
	/**
	 * A class for handling different types of input, including oredict.
	 * Designed to work with JEI & Crafttweaker.
	 */
	public static class Ingredient {
		
		private final Object ingredientRaw;
		private final NonNullList<ItemStack> stacks;
		
		public static Ingredient EMPTY = new Ingredient(ItemStack.EMPTY);
		
		public Ingredient(@Nonnull Object ingredient) {
			
			this.ingredientRaw = ingredient;
			
			if (this.ingredientRaw instanceof String) {
				
				this.stacks = OreDictionary.getOres((String) ingredient);
				
			} else if (this.ingredientRaw instanceof ItemStack) {
				
				this.stacks = ((ItemStack) this.ingredientRaw).isEmpty() ? null : NonNullList.withSize(1, (ItemStack) ingredient);
				
			} else if (this.ingredientRaw instanceof Item) {
				
				this.stacks = NonNullList.withSize(1, new ItemStack((Item) this.ingredientRaw));
				
			} else if (this.ingredientRaw instanceof Block) {
				
				this.stacks = NonNullList.withSize(1, new ItemStack((Block) this.ingredientRaw));
				
			} else
				throw new IllegalArgumentException("Type " + ingredient.getClass() + " is not permitted as ingredient!");
			
		}
		
		public boolean matches(ItemStack stack) {
			
			if (this.stacks == null) return stack.isEmpty();
			else if (this.stacks.size() > 1) {
				
				for (ItemStack current : this.stacks) {
					
					if (current.getItem() == stack.getItem() && current.getMetadata() == stack.getMetadata() && stack.getItemDamage() <= current.getItemDamage()) return true;
					
				}
				
			} else if (this.stacks.size() == 1) return this.stacks.get(0).getItem() == stack.getItem() && this.stacks.get(0).getMetadata() == stack.getMetadata() && stack.getItemDamage() <= this.stacks.get(0).getItemDamage();
			
			return false;
			
		}
		
		public boolean isEmpty() {
			
			return this.stacks == null;
			
		}
		
		public List<ItemStack> getAll() {
			
			return this.stacks;
			
		}
		
		public Object getRaw() {
			
			return this.ingredientRaw;
			
		}
		
	}
	
}
