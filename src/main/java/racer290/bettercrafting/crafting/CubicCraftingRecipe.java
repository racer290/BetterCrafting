package racer290.bettercrafting.crafting;

import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import racer290.bettercrafting.BetterCrafting;

public class CubicCraftingRecipe {
	
	public static final int DEFAULT_MATRIX_LENGTH = 3;
	public static final int DEFAULT_TICKS_PER_OPERATION = 200; // aka 10 secs
	
	private Object[][][] input;
	private ItemStack output;
	
	private final int ticks;
	
	private final int sizeX;
	private final int sizeY;
	private final int sizeZ;
	
	public CubicCraftingRecipe(Object[][][] in, ItemStack out) {
		
		this(in, out, DEFAULT_TICKS_PER_OPERATION, true);
		
	}
	
	public CubicCraftingRecipe(Object[][][] in, @Nonnull ItemStack out, int ticks, boolean translate) {
		
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
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					if (in[x][y][z] instanceof String) {
						
						if (minX > x) minX = x;
						if (minY > y) minY = y;
						if (minZ > z) minZ = z;
						
						if (maxX < x) maxX = x;
						if (maxY < y) maxY = y;
						if (maxZ < z) maxZ = z;
						
					} else if (in[x][y][z] instanceof ItemStack) {

						if (minX > x) minX = x;
						if (minY > y) minY = y;
						if (minZ > z) minZ = z;
						
						if (maxX < x) maxX = x;
						if (maxY < y) maxY = y;
						if (maxZ < z) maxZ = z;
						
					} else {
						
						throw new IllegalArgumentException("Cubic recipes cannot contain elements of the type " + (in[x][y][z] == null ? "null" : in[x][y][z].getClass().getName()) + "!");
						
					}
					
				}
				
			}
			
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
	
	public Object[][][] getInputMatrix() {
		
		return this.input;
		
	}
	
	public ItemStack getOutput() {
		
		return this.output;
		
	}
	
	public int getTicks() {
		
		return this.ticks;
		
	}
	
	private Object[][][] offsetInputMatrix(int offsetX, int offsetY, int offsetZ) {
		
		Object[][][] offset = nullMatrix();
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					try {
						
						offset[x + offsetX][y + offsetY][z + offsetZ] = this.getInputMatrix()[x][y][z];
						
					} catch (ArrayIndexOutOfBoundsException ex) {
						
						if (this.getInputMatrix()[x][y][z] instanceof ItemStack && !((ItemStack) this.getInputMatrix()[x][y][z]).isEmpty()) throw ex;
						
					}
					
				}
				
			}
			
		}
		
		return offset;
		
	}
	
	private static ItemStack[][][] nullMatrix() {
		
		ItemStack[][][] matrix = new ItemStack[DEFAULT_MATRIX_LENGTH][DEFAULT_MATRIX_LENGTH][DEFAULT_MATRIX_LENGTH];
		
		for (int z = 0; z < DEFAULT_MATRIX_LENGTH; z++) {
			
			for (int y = 0; y < DEFAULT_MATRIX_LENGTH; y++) {
				
				for (int x = 0; x < DEFAULT_MATRIX_LENGTH; x++) {
					
					matrix[x][y][z] = ItemStack.EMPTY;
					
				}
				
			}
			
		}
		
		return matrix;
		
	}
	
}