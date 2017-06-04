package racer290.bettercrafting.tile;

import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.block.BlockHelper;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;
import racer290.bettercrafting.crafting.ShapedCubicCraftingRecipe;

public class TileCraftingResult extends TileModInventory implements ITickable {
	
	private static ImmutableMap<BlockPos, Block> MULTIBLOCK_MAPPINGS;
	
	private int ticksCrafting;
	private BaseCubicCraftingRecipe currentRecipe;
	
	@Override
	public void update() {
		
		if (this.world.isRemote) return;
		
		if (!this.getInventory().getStackInSlot(0).isEmpty()) return;
		
		for (Entry<BlockPos, Block> current : TileCraftingResult.MULTIBLOCK_MAPPINGS.entrySet()) {
			
			BlockPos offsetPos = this.pos.add(current.getKey());
			
			if (current.getValue() == Blocks.IRON_BLOCK) {
				
				if (!this.getWorld().getBlockState(offsetPos).getBlock().isBeaconBase(this.getWorld(), offsetPos, null)) return;
				
			} else {
				
				if (this.getWorld().getBlockState(offsetPos).getBlock() != current.getValue()) return;
				
			}
			
		}
		
		this.currentRecipe = BetterCrafting.craftingManager.getRecipeForMatrix(this.getMatrix());
		
		if (this.currentRecipe == null) return;
		
		if (++this.ticksCrafting < this.currentRecipe.getTicks()) return;
		
		for (BlockPos current : TileCraftingResult.MULTIBLOCK_MAPPINGS.keySet()) {
			
			if (this.world.getBlockState(this.pos.add(current)).getBlock() == BlockHelper.blockCraftingSlot) {
				
				((TileCraftingSlot) this.world.getTileEntity(this.pos.add(current))).clear();
				
			}
			
		}
		
		this.getInventory().setStackInSlot(0, this.currentRecipe.getOutput());
		
		// BetterCrafting.LOGGER.info("recipe finished");
		
		this.ticksCrafting = 0;
		this.currentRecipe = null;
		
	}
	
	public int getTicksCrafting() {
		
		return this.ticksCrafting;
		
	}
	
	public void rightClick(EntityPlayer player, EnumHand hand) {
		
		ItemHandlerHelper.giveItemToPlayer(player, this.getInventory().getStackInSlot(0), player.inventory.currentItem);
		
		this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
		
	}
	
	private ItemStack[][][] getMatrix() {
		
		ItemStack[][][] matrix = new ItemStack[ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH][ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH][ShapedCubicCraftingRecipe.DEFAULT_MATRIX_LENGTH];
		
		for (Entry<BlockPos, Block> current : TileCraftingResult.MULTIBLOCK_MAPPINGS.entrySet()) {
			
			if (current.getValue() == BlockHelper.blockCraftingSlot) {
				
				if (!(this.world.getTileEntity(this.pos.add(current.getKey())) instanceof TileCraftingSlot)) {
					
					BetterCrafting.LOGGER.info(this.world.getBlockState(this.pos.add(current.getKey())).getBlock().getLocalizedName());
					
					return null;
					
				}
				
				int x = current.getKey().getX() + 1;
				int y = current.getKey().getY() + 5;
				int z = current.getKey().getZ() + 1;
				
				matrix[y][z][x] = ((TileCraftingSlot) this.world.getTileEntity(this.pos.add(current.getKey()))).getInventory().getStackInSlot(0);
				
			}
			
		}
		
		return matrix;
		
	}
	
	public ItemStack getStoredItem() {
		
		return this.getInventory().getStackInSlot(0);
		
	}
	
	public TileCraftingResult() {
		
		super();
		
		this.getInventory().setSize(1);
		this.getInventory().setSlotLimit(0, 1);
		
		this.ticksCrafting = 0;
		this.currentRecipe = null;
		
	}
	
	static {
		
		// 0, -4, 0 is the center of the structure
		
		TileCraftingResult.MULTIBLOCK_MAPPINGS = ImmutableMap.<BlockPos, Block> builder().put(new BlockPos(-2, -6, -2), Blocks.IRON_BLOCK).put(new BlockPos(-2, -6, 2), Blocks.IRON_BLOCK).put(new BlockPos(2, -6, -2), Blocks.IRON_BLOCK).put(new BlockPos(2, -6, 2), Blocks.IRON_BLOCK).put(new BlockPos(-2, -2, -2), Blocks.IRON_BLOCK).put(new BlockPos(-2, -2, 2), Blocks.IRON_BLOCK).put(new BlockPos(2, -2, -2), Blocks.IRON_BLOCK).put(new BlockPos(2, -2, 2), Blocks.IRON_BLOCK)
				
				.put(new BlockPos(-1, -6, -2), Blocks.IRON_BARS).put(new BlockPos(0, -6, -2), Blocks.IRON_BARS).put(new BlockPos(1, -6, -2), Blocks.IRON_BARS)
				
				.put(new BlockPos(-1, -6, 2), Blocks.IRON_BARS).put(new BlockPos(0, -6, 2), Blocks.IRON_BARS).put(new BlockPos(1, -6, 2), Blocks.IRON_BARS)
				
				.put(new BlockPos(-2, -6, -1), Blocks.IRON_BARS).put(new BlockPos(-2, -6, 0), Blocks.IRON_BARS).put(new BlockPos(-2, -6, 1), Blocks.IRON_BARS)
				
				.put(new BlockPos(2, -6, -1), Blocks.IRON_BARS).put(new BlockPos(2, -6, 0), Blocks.IRON_BARS).put(new BlockPos(2, -6, 1), Blocks.IRON_BARS)
				
				.put(new BlockPos(-1, -2, -2), Blocks.IRON_BARS).put(new BlockPos(0, -2, -2), Blocks.IRON_BARS).put(new BlockPos(1, -2, -2), Blocks.IRON_BARS)
				
				.put(new BlockPos(-1, -2, 2), Blocks.IRON_BARS).put(new BlockPos(0, -2, 2), Blocks.IRON_BARS).put(new BlockPos(1, -2, 2), Blocks.IRON_BARS)
				
				.put(new BlockPos(-2, -2, -1), Blocks.IRON_BARS).put(new BlockPos(-2, -2, 0), Blocks.IRON_BARS).put(new BlockPos(-2, -2, 1), Blocks.IRON_BARS)
				
				.put(new BlockPos(2, -2, -1), Blocks.IRON_BARS).put(new BlockPos(2, -2, 0), Blocks.IRON_BARS).put(new BlockPos(2, -2, 1), Blocks.IRON_BARS)
				
				.put(new BlockPos(-1, -5, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -5, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -5, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -4, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -4, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -4, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -3, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -3, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(-1, -3, 1), BlockHelper.blockCraftingSlot)
				
				.put(new BlockPos(0, -5, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -5, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -5, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -4, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -4, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -4, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -3, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -3, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(0, -3, 1), BlockHelper.blockCraftingSlot)
				
				.put(new BlockPos(1, -5, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -5, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -5, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -4, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -4, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -4, 1), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -3, -1), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -3, 0), BlockHelper.blockCraftingSlot).put(new BlockPos(1, -3, 1), BlockHelper.blockCraftingSlot)
				
				.build();
	}
	
}