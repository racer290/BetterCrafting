package racer290.bettercrafting.integration.botania.crafting;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;

public class ShapedManaCubicCraftingRecipe extends BaseManaCubicCraftingRecipe {
	
	private CubicMatrix3x3<RecipeRuneConstellation> input;
	
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	
	public ShapedManaCubicCraftingRecipe(CubicMatrix3x3<RecipeRuneConstellation> in, ItemStack out, int ticks, int mana, boolean translate) {
		
		if (ticks < 1) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid duration, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (mana < 0) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with invalid amount of mana, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (out == null || out.isEmpty()) {
			
			BetterCrafting.LOGGER.warn("Tried to construct recipe with empty output, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		// Some twisted size calculation logic following ..
		int minX = BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH, minY = BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH, minZ = BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH;
		int maxX = -1, maxY = -1, maxZ = -1;
		
		try {
			
			for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
				
				for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
					
					for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
						
						if (!(in.get(x, y, z).getIngredient().matches(ItemStack.EMPTY))) {
							
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
			
			BetterCrafting.LOGGER.info("Tried to construct a recipe with insufficient size!");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		if (maxX == -1 || maxY == -1 || maxZ == -1 || minX == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH || minY == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH || minZ == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH) {
			
			BetterCrafting.LOGGER.warn("Tried to construct empty recipe, this is not permitted! Skipping recipe ..");
			
			throw new IllegalArgumentException("Tried to construct invalid recipe");
			
		}
		
		this.sizeX = maxX - minX + 1;
		this.sizeY = maxY - minY + 1;
		this.sizeZ = maxZ - minZ + 1;
		
		this.input = nullMatrix();
		
		if (translate) {
			
			for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
				
				for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
					
					for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
						
						if (!in.get(x, y, z).isEmpty()) {
							
							try {
								
								this.getInputMatrix().set(x - minX, y - minY, z - minZ, in.get(x, y, z));
								
							} catch (IndexOutOfBoundsException ex) {}
							
						}
						
					}
					
				}
				
			}
			
		} else {
			
			this.input = in;
			
		}
		
		this.output = out;
		
		this.ticks = ticks;
		
	}
	
	@Override
	public boolean matches(CubicMatrix3x3<RealRuneConstellation> matrix) {
		
		for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					if (!this.getInputMatrix().get(x, y, z).matches(matrix.get(x, y, z))) return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public Set<ShapedManaCubicCraftingRecipe> getAllTranslated() {
		
		if (this.sizeX == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH && this.sizeY == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH && this.sizeZ == BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH) return Sets.newHashSet(this);
		
		Set<ShapedManaCubicCraftingRecipe> translated = Sets.newHashSet();
		
		for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					translated.add(new ShapedManaCubicCraftingRecipe(this.offsetInputMatrix(x, y, z), this.getOutput(), this.getTicks(), this.getRequiredMana(), false));
					
				}
				
			}
			
		}
		
		return translated;
		
	}
	
	public static CubicMatrix3x3<RecipeRuneConstellation> nullMatrix() {
		
		return new CubicMatrix3x3<>(RecipeRuneConstellation.EMPTY);
		
	}
	
	private CubicMatrix3x3<RecipeRuneConstellation> offsetInputMatrix(int offsetX, int offsetY, int offsetZ) {
		
		CubicMatrix3x3<RecipeRuneConstellation> offset = nullMatrix();
		
		for (int z = 0; z < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < BaseCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					try {
						
						offset.set(x + offsetX, y + offsetY, z + offsetZ, this.getInputMatrix().get(x, y, z));
						
					} catch (IndexOutOfBoundsException ex) {
						
						if (!this.getInputMatrix().get(x, y, z).isEmpty()) throw ex;
						
					}
					
				}
				
			}
			
		}
		
		return offset;
		
	}
	
	public CubicMatrix3x3<RecipeRuneConstellation> getInputMatrix() {
		
		return this.input;
		
	}
	
}
