package racer290.bettercrafting.crafting;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;

public class ShapedCubicCraftingRecipe extends BaseCubicCraftingRecipe {
	
	private CubicMatrix3x3<Ingredient> input;
	
	private final int sizeX;
	private final int sizeY;
	private final int sizeZ;
	
	public ShapedCubicCraftingRecipe(CubicMatrix3x3<Ingredient> in, ItemStack out) {
		
		this(in, out, DEFAULT_TICKS_PER_OPERATION, true);
		
	}
	
	public ShapedCubicCraftingRecipe(CubicMatrix3x3<Ingredient> in, ItemStack out, int ticks, boolean translate) {
		
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
						
						if (!in.get(x, y, z).isEmpty()) {
							
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
						
						try {
							
							// Translating recipe starting point to (0, 0, 0) with that x - minX stuff
							this.input.set(x - minX, y - minY, z - minZ, in.get(x, y, z));
							
						} catch (IndexOutOfBoundsException ex) {}
						
					}
					
				}
				
			}
			
		} else {
			
			this.input = in;
			
		}
		
		this.output = out;
		
		this.ticks = ticks;
		
	}
	
	public Set<ShapedCubicCraftingRecipe> getAllTranslated() {
		
		Set<ShapedCubicCraftingRecipe> translated = Sets.newHashSet();
		
		if (this.sizeX == DEFAULT_MATRIX_LENGTH && this.sizeY == DEFAULT_MATRIX_LENGTH && this.sizeX == DEFAULT_MATRIX_LENGTH) {
			
			translated.add(this);
			
			return translated;
			
		}
		
		for (int dz = 0; dz <= DEFAULT_MATRIX_LENGTH - this.sizeZ; dz++) {
			
			for (int dy = 0; dy <= DEFAULT_MATRIX_LENGTH - this.sizeY; dy++) {
				
				for (int dx = 0; dx <= DEFAULT_MATRIX_LENGTH - this.sizeX; dx++) {
					
					translated.add(new ShapedCubicCraftingRecipe(this.offsetInputMatrix(dx, dy, dz), this.getOutput(), this.getTicks(), false));
					
				}
				
			}
			
		}
		
		return translated;
		
	}
	
	@Override
	public boolean matches(CubicMatrix3x3<ItemStack> matrix) {
		
		for (int z = 0; z < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH; x++) {
					
					if (!this.getInputMatrix().get(x, y, z).matches(matrix.get(x, y, z))) return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public CubicMatrix3x3<Ingredient> getInputMatrix() {
		
		return this.input;
		
	}
	
	@Override
	public ItemStack getOutput() {
		
		return this.output;
		
	}
	
	@Override
	public int getTicks() {
		
		return this.ticks;
		
	}
	
	private CubicMatrix3x3<Ingredient> offsetInputMatrix(int offsetX, int offsetY, int offsetZ) {
		
		CubicMatrix3x3<Ingredient> offset = nullMatrix();
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					try {
						
						offset.set(x + offsetX, y + offsetY, z + offsetZ, this.getInputMatrix().get(x, y, z));
						
					} catch (ArrayIndexOutOfBoundsException ex) {
						
						if (!this.getInputMatrix().get(x, y, z).isEmpty()) throw ex;
						
					}
					
				}
				
			}
			
		}
		
		return offset;
		
	}
	
}