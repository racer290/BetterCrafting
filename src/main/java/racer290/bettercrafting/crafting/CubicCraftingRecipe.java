package racer290.bettercrafting.crafting;

import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import racer290.bettercrafting.BetterCrafting;

public class CubicCraftingRecipe {
	
	public static final int DEFAULT_MATRIX_LENGTH = 3;
	public static final int DEFAULT_TICKS_PER_OPERATION = 200; // aka 10 secs
	
	private Ingredient[][][] input;
	private ItemStack output;
	
	private final int ticks;
	
	private final int sizeX;
	private final int sizeY;
	private final int sizeZ;
	
	public CubicCraftingRecipe(Ingredient[][][] in, ItemStack out) {
		
		this(in, out, DEFAULT_TICKS_PER_OPERATION, true);
		
	}
	
	public CubicCraftingRecipe(Ingredient[][][] in, @Nonnull ItemStack out, int ticks, boolean translate) {
		
		if (ticks < 1) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid duration, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (out == null || out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with empty output, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		// Some twisted size calculation logic following ..
		int minX = DEFAULT_MATRIX_LENGTH, minY = DEFAULT_MATRIX_LENGTH, minZ = DEFAULT_MATRIX_LENGTH;
		int maxX = -1, maxY = -1, maxZ = -1;
		
		try {
			
			for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
				
				for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
					
					for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
						
						if (!in[x][y][z].matches(ItemStack.EMPTY)) {
							
							if (minX > x) {
								minX = x;
							}
							if (minY > y) {
								minY = y;
							}
							if (minZ > z) {
								minZ = z;
							}
							
							if (maxX < x) {
								maxX = x;
							}
							if (maxY < y) {
								maxY = y;
							}
							if (maxZ < z) {
								maxZ = z;
							}
							
						}
						
					}
					
				}
				
			}
			
		} catch (ArrayIndexOutOfBoundsException ex) {
			
			throw new IllegalArgumentException("Tried to construct a recipe with insufficient size! Hint: elements with [x][y][z] such that x, y or z > 2 will be ignored!");
			
		}
		
		if (maxX == -1 || maxY == -1 || maxZ == -1 || minX == DEFAULT_MATRIX_LENGTH || minY == DEFAULT_MATRIX_LENGTH || minZ == DEFAULT_MATRIX_LENGTH) {
			
			BetterCrafting.LOGGER.warn("Tried to construct empty recipe, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		this.sizeX = maxX - minX + 1;
		this.sizeY = maxY - minY + 1;
		this.sizeZ = maxZ - minZ + 1;
		
		this.input = nullMatrix();
		
		if (translate) {
			
			for (int z = 0; z < this.sizeZ; z++) {
				
				for (int y = 0; y < this.sizeY; y++) {
					
					for (int x = 0; x < this.sizeX; x++) {
						
						// Translating recipe starting point to (0, 0, 0) with that x - minX stuff
						this.input[x - minX][y - minY][z - minZ] = in[x][y][z];
						
					}
					
				}
				
			}
			
		} else {
			
			this.input = in;
			
		}
		
		this.output = out;
		
		this.ticks = ticks;
		
	}
	
	public Set<CubicCraftingRecipe> getAllTranslated() {
		
		Set<CubicCraftingRecipe> translated = Sets.newHashSet();
		
		if (this.sizeX == DEFAULT_MATRIX_LENGTH && this.sizeY == DEFAULT_MATRIX_LENGTH && this.sizeX == DEFAULT_MATRIX_LENGTH) return translated;
		
		for (int dz = 0; dz <= DEFAULT_MATRIX_LENGTH - this.sizeZ; dz++) {
			
			for (int dy = 0; dy <= DEFAULT_MATRIX_LENGTH - this.sizeY; dy++) {
				
				for (int dx = 0; dx <= DEFAULT_MATRIX_LENGTH - this.sizeX; dx++) {
					
					translated.add(new CubicCraftingRecipe(this.offsetInputMatrix(dx, dy, dz), this.getOutput(), this.getTicks(), false));
					
				}
				
			}
			
		}
		
		return translated;
		
	}
	
	public Ingredient[][][] getInputMatrix() {
		
		return this.input;
		
	}
	
	public ItemStack getOutput() {
		
		return this.output;
		
	}
	
	public int getTicks() {
		
		return this.ticks;
		
	}
	
	private Ingredient[][][] offsetInputMatrix(int offsetX, int offsetY, int offsetZ) {
		
		Ingredient[][][] offset = nullMatrix();
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					try {
						
						offset[x + offsetX][y + offsetY][z + offsetZ] = this.getInputMatrix()[x][y][z];
						
					} catch (ArrayIndexOutOfBoundsException ex) {
						
						if (!this.getInputMatrix()[x][y][z].matches(ItemStack.EMPTY)) throw ex;
						
					}
					
				}
				
			}
			
		}
		
		return offset;
		
	}
	
	public static Ingredient[][][] nullMatrix() {
		
		Ingredient[][][] matrix = new Ingredient[DEFAULT_MATRIX_LENGTH][DEFAULT_MATRIX_LENGTH][DEFAULT_MATRIX_LENGTH];
		
		Ingredient empty = new Ingredient(ItemStack.EMPTY);
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					matrix[x][y][z] = empty;
					
				}
				
			}
			
		}
		
		return matrix;
		
	}
	
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
				throw new IllegalArgumentException("Type " + ingredient.getClass() + " is not permitted as ingredient");
			
		}
		
		public boolean matches(ItemStack stack) {
			
			if (this.stacks == null)
				return stack.isEmpty();
			else if (this.stacks.size() > 1) {
				
				for (ItemStack current : this.stacks) {
					
					if (current.getItem() == stack.getItem() && current.getMetadata() == stack.getMetadata() && stack.getItemDamage() <= current.getItemDamage()) return true;
					
				}
				
			} else if (this.stacks.size() == 1) return this.stacks.get(0).getItem() == stack.getItem() && this.stacks.get(0).getMetadata() == stack.getMetadata() && stack.getItemDamage() <= this.stacks.get(0).getItemDamage();
			
			return false;
			
		}
		
	}
	
}